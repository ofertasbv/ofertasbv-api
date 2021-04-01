package com.br.oferta.api.controller;

import com.br.oferta.api.model.Seguimento;
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
import com.br.oferta.api.service.SeguimentoService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/seguimentos")
@Api(value = "API REST E-COMMERCE")
@CrossOrigin(origins = "*")
public class SeguimentoController {

    private final Logger logger = LoggerFactory.getLogger(SeguimentoController.class);

    @Autowired
    private SeguimentoService seguimentoService;
    @Autowired
    private ApplicationEventPublisher publisher;

    @CrossOrigin(maxAge = 10, allowCredentials = "false") //origins = "http://localhost:8080/categorias")
    @GetMapping
//    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope('read')")
    public List<Seguimento> findAll() {
        return seguimentoService.findBySort();
    }

    @PostMapping("/create")
//    @PreAuthorize("hasAuthority('ROLE_CADASTRAR_CATEGORIA') and #oauth2.hasScope('write')")
    public ResponseEntity<Seguimento> criar(@Valid @RequestBody Seguimento seguimento, HttpServletResponse response) {
        Seguimento salva = seguimentoService.create(seguimento);
        publisher.publishEvent(new RecursoCriadoEvent(this, response, salva.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(salva);
    }

    @GetMapping("/{id}")
//    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope('read')")
    public ResponseEntity<Seguimento> findById(@PathVariable Long id) {
        Optional<Seguimento> seguimento = seguimentoService.findById(id);
        return seguimento.isPresent() ? ResponseEntity.ok(seguimento.get()) : ResponseEntity.notFound().build();
    }

    @CrossOrigin(maxAge = 10, allowCredentials = "false") //origins = "http://localhost:8080/categorias")
    @GetMapping("/nome/{nome}")
//    @PreAuthorize("hasAuthority('ROLE_PESQUISAR') and #oauth2.hasScope('read')")
    public List<Seguimento> findAllByNome(@PathVariable String nome) {
        return seguimentoService.findByNome(nome);
    }

    @PutMapping("/update/{id}")
//    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA')")
    public ResponseEntity<Seguimento> update(@PathVariable Long id, @Valid @RequestBody Seguimento seguimento) {
        try {
            Seguimento salvar = seguimentoService.findById(id).get();
            if (salvar == null) {
                throw new EmptyResultDataAccessException(1);
            }
            BeanUtils.copyProperties(seguimento, salvar, "id");
            seguimentoService.create(salvar);
            return ResponseEntity.ok(salvar);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        return seguimentoService.delete(id);
    }
}
