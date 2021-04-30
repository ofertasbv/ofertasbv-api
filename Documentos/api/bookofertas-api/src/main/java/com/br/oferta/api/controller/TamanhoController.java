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
import com.br.oferta.api.model.Tamanho;
import com.br.oferta.api.service.TamanhoService;
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
@RequestMapping("/tamanhos")
@Api(value = "API REST E-COMMERCE")
@CrossOrigin(origins = "*")
public class TamanhoController {

    private final Logger logger = LoggerFactory.getLogger(TamanhoController.class);

    @Autowired
    private TamanhoService tamanhoService;


    @Autowired
    private ApplicationEventPublisher publisher;

    @CrossOrigin(maxAge = 10, allowCredentials = "false") //origins = "http://localhost:8080/categorias")
    @GetMapping
//    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope('read')")
    public List<Tamanho> findAll() {
        return tamanhoService.findBySort();
    }

    @GetMapping("/{id}")
//    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope('read')")
    public ResponseEntity<Tamanho> findById(@PathVariable Long id) {
        Optional<Tamanho> tamanho = tamanhoService.findById(id);
        return tamanho.isPresent() ? ResponseEntity.ok(tamanho.get()) : ResponseEntity.notFound().build();
    }

    @PostMapping("/create")
//    @PreAuthorize("hasAuthority('ROLE_CADASTRAR_CATEGORIA') and #oauth2.hasScope('write')")
    public ResponseEntity<Tamanho> create(@Validated @RequestBody Tamanho tamanho, HttpServletResponse response) {
        Tamanho salva = tamanhoService.create(tamanho);
        publisher.publishEvent(new RecursoCriadoEvent(this, response, salva.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(salva);
    }

    @PutMapping("/update/{id}")
//    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA')")
    public ResponseEntity<Tamanho> update(@PathVariable Long id, @Validated @RequestBody Tamanho tamanho) {
        try {
            Tamanho salva = tamanhoService.findById(id).get();
            if (salva == null) {
                throw new EmptyResultDataAccessException(1);
            }
            BeanUtils.copyProperties(tamanho, salva, "id");
            tamanhoService.create(salva);
            return ResponseEntity.ok(salva);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        return tamanhoService.delete(id);
    }

  
}
