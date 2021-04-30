package com.br.oferta.api.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.br.oferta.api.util.event.RecursoCriadoEvent;
import com.br.oferta.api.model.Loja;
import com.br.oferta.api.service.LojaService;
import com.br.oferta.api.util.upload.FileStorageService;
import com.br.oferta.api.util.upload.payload.UploadFileResponse;
import io.swagger.annotations.Api;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/lojas")
@Api(value = "API REST E-COMMERCE")
@CrossOrigin(origins = "*")
public class LojaController {

    private final Logger logger = LoggerFactory.getLogger(LojaController.class);

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private LojaService lojaService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @CrossOrigin(maxAge = 10, allowCredentials = "false") //origins = "http://localhost:8080/categorias")
    @GetMapping
//    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope('read')")
    public List<Loja> findAll() {
        return lojaService.findBySort();
    }

    @GetMapping("/{id}")
    //@PreAuthorize("hasAuthority('ROLE_PESQUISAR_PESSOA') and #oauth2.hasScope('read')")
    public ResponseEntity<Loja> findById(@PathVariable Long id) {
        Optional<Loja> pessoa = lojaService.findById(id);
        return pessoa.isPresent() ? ResponseEntity.ok(pessoa.get()) : ResponseEntity.notFound().build();
    }

    @CrossOrigin(maxAge = 10, allowCredentials = "false") //origins = "http://localhost:8080/categorias")
    @GetMapping("/nome/{nome}")
//    @PreAuthorize("hasAuthority('ROLE_PESQUISAR') and #oauth2.hasScope('read')")
    public List<Loja> findAllByNome(@PathVariable String nome) {
        return lojaService.findByNome(nome);
    }

    @CrossOrigin(maxAge = 10, allowCredentials = "false")
    @PostMapping("/create")//, consumes = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasAuthority('ROLE_CADASTRAR_PESSOA') and #oauth2.hasScope('write')")
    public ResponseEntity<Loja> create(@Validated @RequestBody Loja pessoa, HttpServletResponse response) {
        Loja pessoaSalva = lojaService.create(pessoa);
        publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoaSalva.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(pessoaSalva);
    }

    @PutMapping("/update/{id}")
//    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA')")
    public ResponseEntity<Loja> update(@PathVariable Long id, @Validated @RequestBody Loja loja) {
        try {
            Loja salvar = lojaService.findById(id).get();
            if (salvar == null) {
                throw new EmptyResultDataAccessException(1);
            }
            BeanUtils.copyProperties(loja, salvar, "id");
            lojaService.create(salvar);
            return ResponseEntity.ok(salvar);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    //@PreAuthorize("hasAuthority('ROLE_REMOVER_PESSOA') and #oauth2.hasScope('write')")
    public void delete(@PathVariable Long id) {
        lojaService.delete(id);
    }

    @DeleteMapping("/delete/foto/{foto}")
    public void excluirFoto(@PathVariable String foto) {
        lojaService.excluirFoto(foto);
    }

    @PostMapping("/upload")
    public UploadFileResponse uploadFile(@RequestParam MultipartFile foto) {
        String fileName = fileStorageService.storeFile(foto);
        System.out.println("FileName: " + fileName);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/lojas/download/").path(fileName).toUriString();

        System.out.println("Caminho upload: " + fileDownloadUri);

        return new UploadFileResponse(fileName, fileDownloadUri, foto.getContentType(), foto.getSize());
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
