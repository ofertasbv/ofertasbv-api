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
import com.br.oferta.api.model.Estoque;
import com.br.oferta.api.service.EstoqueService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/estoques")
@Api(value="API REST E-COMMERCE")
@CrossOrigin(origins = "*")
public class EstoqueController {

    private final Logger logger = LoggerFactory.getLogger(EstoqueController.class);

    @Autowired
    private EstoqueService estoqueService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @CrossOrigin(maxAge = 10, allowCredentials = "false") //origins = "http://localhost:8080/categorias")
    @GetMapping
//    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope('read')")
    public List<Estoque> findAll() {
        return estoqueService.findBySort();
    }

    @GetMapping("/{id}")
//    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope('read')")
    public ResponseEntity<Estoque> findById(@PathVariable Long id) {
        Optional<Estoque> estoque = estoqueService.findById(id);
        return estoque.isPresent() ? ResponseEntity.ok(estoque.get()) : ResponseEntity.notFound().build();
    }

    @PostMapping("/create")
//    @PreAuthorize("hasAuthority('ROLE_CADASTRAR_CATEGORIA') and #oauth2.hasScope('write')")
    public ResponseEntity<Estoque> create(@Validated @RequestBody Estoque estoque, HttpServletResponse response) {
        Estoque estoqueSalva = estoqueService.create(estoque);
        publisher.publishEvent(new RecursoCriadoEvent(this, response, estoqueSalva.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(estoqueSalva);
    }

    @PutMapping("/update/{id}")
//    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA')")
    public ResponseEntity<Estoque> update(@PathVariable Long id, @Validated @RequestBody Estoque estoque) {
        try {
            Estoque estoqueSalva = estoqueService.findById(id).get();
            if (estoqueSalva == null) {
                throw new EmptyResultDataAccessException(1);
            }
            BeanUtils.copyProperties(estoque, estoqueSalva, "id");
            estoqueService.create(estoqueSalva);
            return ResponseEntity.ok(estoqueSalva);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        return estoqueService.delete(id);
    }

}
