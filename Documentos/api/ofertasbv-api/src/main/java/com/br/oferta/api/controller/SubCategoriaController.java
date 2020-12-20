package com.br.oferta.api.controller;

import com.br.oferta.api.model.Cliente;
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
import com.br.oferta.api.model.SubCategoria;
import com.br.oferta.api.service.SubCategoriaService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/subcategorias")
@Api(value = "API REST E-COMMERCE")
@CrossOrigin(origins = "*")
public class SubCategoriaController {

    private final Logger logger = LoggerFactory.getLogger(SubCategoriaController.class);

    @Autowired
    private SubCategoriaService subCategoriaService;
    @Autowired
    private ApplicationEventPublisher publisher;

    @CrossOrigin(maxAge = 10, allowCredentials = "false") //origins = "http://localhost:8080/categorias")
    @GetMapping
//    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope('read')")
    public List<SubCategoria> findAll() {
        return subCategoriaService.findBySort();
    }

    @PostMapping("/create")
//    @PreAuthorize("hasAuthority('ROLE_CADASTRAR_CATEGORIA') and #oauth2.hasScope('write')")
    public ResponseEntity<SubCategoria> criar(@Valid @RequestBody SubCategoria subCategoria, HttpServletResponse response) {
        SubCategoria subCategoriaSalva = subCategoriaService.create(subCategoria);
        publisher.publishEvent(new RecursoCriadoEvent(this, response, subCategoriaSalva.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(subCategoriaSalva);
    }

    @GetMapping("/{id}")
//    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope('read')")
    public ResponseEntity<SubCategoria> findById(@PathVariable Long id) {
        Optional<SubCategoria> categoria = subCategoriaService.findById(id);
        return categoria.isPresent() ? ResponseEntity.ok(categoria.get()) : ResponseEntity.notFound().build();
    }

    @CrossOrigin(maxAge = 10, allowCredentials = "false") //origins = "http://localhost:8080/categorias")
    @GetMapping("/nome/{nome}")
//    @PreAuthorize("hasAuthority('ROLE_PESQUISAR') and #oauth2.hasScope('read')")
    public List<SubCategoria> findAllByNome(@PathVariable String nome) {
        return subCategoriaService.findByNome(nome);
    }

    @GetMapping("/categoria/{id}")
//    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope('read')")
    public List<SubCategoria> findCategoriaById(@PathVariable Long id) {
        List<SubCategoria> subcategoria = subCategoriaService.findCategoriaById(id);
        return subcategoria;
    }

    @PutMapping("/update/{id}")
//    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA')")
    public ResponseEntity<SubCategoria> update(@PathVariable Long id, @Valid @RequestBody SubCategoria subCategoria) {
        try {
            SubCategoria salvar = subCategoriaService.findById(id).get();
            if (salvar == null) {
                throw new EmptyResultDataAccessException(1);
            }
            BeanUtils.copyProperties(subCategoria, salvar, "id");
            subCategoriaService.create(salvar);
            return ResponseEntity.ok(salvar);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        return subCategoriaService.delete(id);
    }
}
