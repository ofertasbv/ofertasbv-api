package com.br.oferta.api.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.oferta.api.util.event.RecursoCriadoEvent;
import com.br.oferta.api.model.Arquivo;
import com.br.oferta.api.service.ArquivoService;
import com.br.oferta.api.util.upload.FileStorageService;
import com.br.oferta.api.util.upload.payload.UploadFileResponse;
import io.swagger.annotations.Api;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.core.io.Resource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/arquivos")
@Api(value = "API REST E-COMMERCE")
@CrossOrigin(origins = "*")
public class ArquivoController {

    private final Logger logger = LoggerFactory.getLogger(ArquivoController.class);

    @Autowired
    private ArquivoService arquivoService;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @CrossOrigin(maxAge = 10, allowCredentials = "false") //origins = "http://localhost:8080/categorias")
    @GetMapping
//    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope('read')")
    public List<Arquivo> findAll() {
        return arquivoService.findBySort();
    }

    @GetMapping("/{id}")
//    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope('read')")
    public ResponseEntity<Arquivo> findById(@PathVariable Long id) {
        Optional<Arquivo> arquivo = arquivoService.findById(id);
        return (ResponseEntity<Arquivo>) (arquivo.isPresent() ? ResponseEntity.ok(arquivo.get()) : ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
//    @PreAuthorize("hasAuthority('ROLE_CADASTRAR_CATEGORIA') and #oauth2.hasScope('write')")
    public ResponseEntity<Arquivo> create(@Validated @RequestBody Arquivo arquivo, HttpServletResponse response) {
        Arquivo arquivoSalva = arquivoService.create(arquivo);
        publisher.publishEvent(new RecursoCriadoEvent(this, response, arquivoSalva.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(arquivoSalva);
    }

    @PutMapping("/update/{id}")
//    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA')")
    public ResponseEntity<Arquivo> update(@PathVariable Long id, @Validated @RequestBody Arquivo arquivo) {
        try {
            Arquivo arquivoSalva = arquivoService.findById(id).get();
            if (arquivoSalva == null) {
                throw new EmptyResultDataAccessException(1);
            }
            BeanUtils.copyProperties(arquivo, arquivoSalva, "id");
            arquivoService.create(arquivoSalva);
            return ResponseEntity.ok(arquivoSalva);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        return arquivoService.delete(id);
    }

    @DeleteMapping("/delete/foto/{foto}")
    public void excluirFoto(@PathVariable String foto) {
        arquivoService.excluirFoto(foto);
    }

    @PostMapping(value = "/upload")
    public UploadFileResponse uploadFile(@RequestParam(value = "foto") MultipartFile file) {

        System.out.println("File: " + file.getOriginalFilename());

        String fileName = fileStorageService.storeFile(file);

        String nomeArquivo = fileStorageService.nomeArquivoDownload();

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/arquivos/download/")
                .path(fileName)
                .toUriString();

        System.out.println(" ======================= FILE ============================ ");

        System.out.println("FILENAME: " + fileName);
        System.out.println("FILE-NAME-URL: " + fileDownloadUri);

        String url = fileDownloadUri.substring(0, fileDownloadUri.lastIndexOf('/') + 1);

        System.out.println("Nome: " + nomeArquivo);
        System.out.println("URL: " + url);

        String nomeCompleto = nomeArquivo;
        String urlFile = url + nomeArquivo;
       

        return new UploadFileResponse(nomeCompleto, urlFile,
                file.getContentType(), file.getSize());
    }

    @PostMapping("/uploadMultipleFiles")
    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        return Arrays.asList(files)
                .stream().map(file -> uploadFile(file))
                .collect(Collectors.toList());
    }

    @GetMapping("/download/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);
        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }
        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

}
