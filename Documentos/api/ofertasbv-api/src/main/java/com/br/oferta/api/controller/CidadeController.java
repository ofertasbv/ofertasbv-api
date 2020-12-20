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
import com.br.oferta.api.model.Cidade;
import com.br.oferta.api.service.CidadeService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/cidades")
@Api(value = "API REST E-COMMERCE")
@CrossOrigin(origins = "*")
public class CidadeController {

    private final Logger logger = LoggerFactory.getLogger(CidadeController.class);

    @Autowired
    private CidadeService cidadeService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @CrossOrigin(maxAge = 10, allowCredentials = "false") //origins = "http://localhost:8080/categorias")
    @GetMapping
//    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope('read')")
    public List<Cidade> findAll() {
        return cidadeService.findBySort();
    }

    @GetMapping("/{id}")
//    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope('read')")
    public ResponseEntity<Cidade> findById(@PathVariable Long id) {
        Optional<Cidade> cidade = cidadeService.findById(id);
        return cidade.isPresent() ? ResponseEntity.ok(cidade.get()) : ResponseEntity.notFound().build();
    }

    @GetMapping("/estado/{id}")
    public List<Cidade> findAllByEstadoId(@PathVariable Long id) {
        return cidadeService.findByEstadoId(id);
    }

    @PostMapping("/create")
//    @PreAuthorize("hasAuthority('ROLE_CADASTRAR_CATEGORIA') and #oauth2.hasScope('write')")
    public ResponseEntity<Cidade> create(@Valid @RequestBody Cidade cidade, HttpServletResponse response) {
        Cidade salva = cidadeService.create(cidade);
        publisher.publishEvent(new RecursoCriadoEvent(this, response, salva.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(salva);
    }

    @PutMapping("/update/{id}")
//    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA')")
    public ResponseEntity<Cidade> update(@PathVariable Long id, @Valid @RequestBody Cidade cidade) {
        try {
            Cidade salava = cidadeService.findById(id).get();
            if (salava == null) {
                throw new EmptyResultDataAccessException(1);
            }
            BeanUtils.copyProperties(cidade, salava, "id");
            cidadeService.create(salava);
            return ResponseEntity.ok(salava);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        return cidadeService.delete(id);
    }

}
