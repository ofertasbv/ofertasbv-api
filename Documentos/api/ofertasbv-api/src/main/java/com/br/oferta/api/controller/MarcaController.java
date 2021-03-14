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
import com.br.oferta.api.model.Marca;
import com.br.oferta.api.service.MarcaService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/marcas")
@Api(value = "API REST E-COMMERCE")
@CrossOrigin(origins = "*")
public class MarcaController {

    private final Logger logger = LoggerFactory.getLogger(MarcaController.class);

    @Autowired
    private MarcaService marcaService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @CrossOrigin(maxAge = 10, allowCredentials = "false") //origins = "http://localhost:8080/categorias")
    @GetMapping
//    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope('read')")
    public List<Marca> findAll() {
        return marcaService.findBySort();
    }

    @GetMapping("/{id}")
//    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope('read')")
    public ResponseEntity<Marca> findById(@PathVariable Long id) {
        Optional<Marca> arquivo = marcaService.findById(id);
        return arquivo.isPresent() ? ResponseEntity.ok(arquivo.get()) : ResponseEntity.notFound().build();
    }
    
    @CrossOrigin(maxAge = 10, allowCredentials = "false") //origins = "http://localhost:8080/categorias")
    @GetMapping("/nome/{nome}")
//    @PreAuthorize("hasAuthority('ROLE_PESQUISAR') and #oauth2.hasScope('read')")
    public List<Marca> findAllByNome(@PathVariable String nome) {
        return marcaService.findByNome(nome);
    }

    @PostMapping("/create")
//    @PreAuthorize("hasAuthority('ROLE_CADASTRAR_CATEGORIA') and #oauth2.hasScope('write')")
    public ResponseEntity<Marca> create(@Valid @RequestBody Marca marca, HttpServletResponse response) {
        Marca marcaSalva = marcaService.create(marca);
        publisher.publishEvent(new RecursoCriadoEvent(this, response, marcaSalva.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(marcaSalva);
    }

    @PutMapping("/update/{id}")
//    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA')")
    public ResponseEntity<Marca> update(@PathVariable Long id, @Valid @RequestBody Marca marca) {
        try {
            Marca marcaSalva = marcaService.findById(id).get();
            if (marcaSalva == null) {
                throw new EmptyResultDataAccessException(1);
            }
            BeanUtils.copyProperties(marca, marcaSalva, "id");
            marcaService.create(marcaSalva);
            return ResponseEntity.ok(marcaSalva);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        return marcaService.delete(id);
    }
}
