package com.br.oferta.api.controller;

import com.br.oferta.api.model.Cor;
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
import com.br.oferta.api.service.CorService;
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
@RequestMapping("/cores")
@Api(value = "API REST E-COMMERCE")
@CrossOrigin(origins = "*")
public class CorController {

    private final Logger logger = LoggerFactory.getLogger(CorController.class);

    @Autowired
    private CorService corService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @CrossOrigin(maxAge = 10, allowCredentials = "false") //origins = "http://localhost:8080/categorias")
    @GetMapping
//    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope('read')")
    public List<Cor> findAll() {
        return corService.findBySort();
    }

    @GetMapping("/{id}")
//    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope('read')")
    public ResponseEntity<Cor> findById(@PathVariable Long id) {
        Optional<Cor> cor = corService.findById(id);
        return cor.isPresent() ? ResponseEntity.ok(cor.get()) : ResponseEntity.notFound().build();
    }

    @PostMapping("/create")
//    @PreAuthorize("hasAuthority('ROLE_CADASTRAR_CATEGORIA') and #oauth2.hasScope('write')")
    public ResponseEntity<Cor> create(@Validated @RequestBody Cor cor, HttpServletResponse response) {
        Cor salva = corService.create(cor);
        publisher.publishEvent(new RecursoCriadoEvent(this, response, salva.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(salva);
    }

    @PutMapping("/update/{id}")
//    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA')")
    public ResponseEntity<Cor> update(@PathVariable Long id, @Validated @RequestBody Cor cor) {
        try {
            Cor salva = corService.findById(id).get();
            if (salva == null) {
                throw new EmptyResultDataAccessException(1);
            }
            BeanUtils.copyProperties(cor, salva, "id");
            corService.create(salva);
            return ResponseEntity.ok(salva);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        return corService.delete(id);
    }

}
