package com.br.oferta.api.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

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
import com.br.oferta.api.model.Produto;
import com.br.oferta.api.service.ProdutoService;
import com.br.oferta.api.util.filter.ProdutoFilter;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/produtos")
@Api(value = "API REST E-COMMERCE")
@CrossOrigin(origins = "*")
public class ProdutoController {

    private final Logger logger = LoggerFactory.getLogger(ProdutoController.class);

    private final ProdutoService produtoService;

    private final FileStorageService fileStorageService;

    private final ApplicationEventPublisher publisher;

    @Autowired
    public ProdutoController(ProdutoService produtoService, FileStorageService fileStorageService, ApplicationEventPublisher publisher) {
        this.produtoService = produtoService;
        this.fileStorageService = fileStorageService;
        this.publisher = publisher;
    }

    //@CrossOrigin(maxAge = 10, allowCredentials = "false") //origins = "http://localhost:8080/categorias")
    @GetMapping
    //@PreAuthorize("hasAuthority('ROLE_PESQUISAR') and #oauth2.hasScope('read')")
    public Page<Produto> findAll(ProdutoFilter produtoFilter, Pageable pageable) {
        return produtoService.filtrar(produtoFilter, pageable);
    }

    //@CrossOrigin(maxAge = 10, allowCredentials = "false") //origins = "http://localhost:8080/categorias")
    @GetMapping("/pesquisa")
    //@PreAuthorize("hasAuthority('ROLE_PESQUISAR') and #oauth2.hasScope('read')")
    public List<Produto> findAllProdutos() {
        return produtoService.findAll();
    }

    @GetMapping("/destaque/{destaque}")
    //@PreAuthorize("hasAuthority('ROLE_PESQUISAR') and #oauth2.hasScope('read')")
    public List<Produto> findByDestaque(@PathVariable boolean destaque) {
        return produtoService.findByDestaque(destaque);
    }

    @GetMapping("/promocao/sem/{id}")
    //@PreAuthorize("hasAuthority('ROLE_PESQUISAR') and #oauth2.hasScope('read')")
    public List<Produto> findByIsPromocaoBy(@PathVariable Long id) {
        return produtoService.findByIsPromocaoBy(id);
    }

    @GetMapping("/{id}")
//    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope('read')")
    public Optional<Produto> findById(@PathVariable Long id) {
        Optional<Produto> produto = produtoService.findById(id);
        return produto;
    }

    @CrossOrigin(maxAge = 10, allowCredentials = "false") //origins = "http://localhost:8080/categorias")
    @GetMapping("/paginacao")
//    @PreAuthorize("hasAuthority('ROLE_PESQUISAR') and #oauth2.hasScope('read')")
    public Page<Produto> findByPageable(Pageable pageable) {
        return produtoService.findAllByPage(pageable);
    }

    @CrossOrigin(maxAge = 10, allowCredentials = "false") //origins = "http://localhost:8080/categorias")
    @GetMapping("/nome/{nome}")
//    @PreAuthorize("hasAuthority('ROLE_PESQUISAR') and #oauth2.hasScope('read')")
    public List<Produto> findAllByNome(@PathVariable String nome) {
        return produtoService.findByNome(nome);
    }

    @GetMapping("/filter")
//    @PreAuthorize("hasAuthority('ROLE_PESQUISAR') and #oauth2.hasScope('read')")
    public Page<Produto> findByFilter(ProdutoFilter produtoFilter, Pageable pageable) {
        return produtoService.filtrar(produtoFilter, pageable);
    }

//    public Page<Produto> pesquisar(ProdutoFilter produtoFilter, Pageable pageable) {
//        return produtoService.filtrar(produtoFilter, pageable);
//    }
    @GetMapping("/subcategoria/{id}")
//    @PreAuthorize("hasAuthority('ROLE_PESQUISAR') and #oauth2.hasScope('read')")
    public List<Produto> findCategoriaById(@PathVariable Long id) {
        List<Produto> produto = produtoService.findBySubCategoriaById(id);
        return produto;
    }

    @GetMapping("/promocao/{id}")
//    @PreAuthorize("hasAuthority('ROLE_PESQUISAR') and #oauth2.hasScope('read')")
    public List<Produto> findPromocaoById(@PathVariable Long id) {
        List<Produto> produto = produtoService.findByPromocaoById(id);
        return produto;
    }

    @GetMapping("/loja/{id}")
//    @PreAuthorize("hasAuthority('ROLE_PESQUISAR') and #oauth2.hasScope('read')")
    public List<Produto> findPessoaById(@PathVariable Long id) {
        List<Produto> produto = produtoService.findByPessoaById(id);
        return produto;
    }

    @PostMapping("/create")
//    @PreAuthorize("hasAuthority('ROLE_CADASTRAR_CATEGORIA') and #oauth2.hasScope('write')")
    public ResponseEntity<Produto> create(@Valid @RequestBody Produto produto, HttpServletResponse response) {
        Produto produtoSalva = produtoService.create(produto);
        publisher.publishEvent(new RecursoCriadoEvent(this, response, produtoSalva.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoSalva);
    }

    @PutMapping("/update/{id}")
//    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA')")
    public ResponseEntity<Produto> update(@PathVariable Long id, @Valid @RequestBody Produto produto) {
        try {
            Produto produtoSalva = produtoService.findById(id).get();
            if (produtoSalva == null) {
                throw new EmptyResultDataAccessException(1);
            }
            BeanUtils.copyProperties(produto, produtoSalva, "id");
            produtoService.create(produtoSalva);
            return ResponseEntity.ok(produtoSalva);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        return produtoService.delete(id);
    }

    @DeleteMapping("/delete/foto/{foto}")
    public void excluirFoto(@PathVariable String foto) {
        produtoService.excluirFoto(foto);
    }

    @GetMapping("/codigobarra/{codigobarra}")
//    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope('read')")
    public Produto findPessoaByCodBar(@PathVariable String codigobarra) {
        Produto produto = produtoService.findByCodBarra(codigobarra);
        return produto;
    }

    @PostMapping("/upload")
    public UploadFileResponse uploadFile(@RequestParam MultipartFile foto) {
        String fileName = fileStorageService.storeFile(foto);
        System.out.println("FileName: " + fileName);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/produtos/download/").path(fileName).toUriString();

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
