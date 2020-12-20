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
import com.br.oferta.api.model.Fatura;
import com.br.oferta.api.service.FaturaService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/faturas")
@Api(value="API REST E-COMMERCE")
@CrossOrigin(origins = "*")
public class FaturaController {

    private final Logger logger = LoggerFactory.getLogger(FaturaController.class);

    @Autowired
    private FaturaService faturaService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @CrossOrigin(maxAge = 10, allowCredentials = "false") //origins = "http://localhost:8080/categorias")
    @GetMapping
//    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope('read')")
    public List<Fatura> findAll() {
        return faturaService.findBySort();
    }

    @GetMapping("/{id}")
//    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope('read')")
    public ResponseEntity<Fatura> findById(@PathVariable Long id) {
        Optional<Fatura> fatura = faturaService.findById(id);
        return fatura.isPresent() ? ResponseEntity.ok(fatura.get()) : ResponseEntity.notFound().build();
    }

    @PostMapping("/create")
//    @PreAuthorize("hasAuthority('ROLE_CADASTRAR_CATEGORIA') and #oauth2.hasScope('write')")
    public ResponseEntity<Fatura> create(@Valid @RequestBody Fatura fatura, HttpServletResponse response) {
        Fatura faturaSalva = faturaService.create(fatura);
        publisher.publishEvent(new RecursoCriadoEvent(this, response, faturaSalva.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(faturaSalva);
    }

    @PutMapping("/update/{id}")
//    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA')")
    public ResponseEntity<Fatura> update(@PathVariable Long id, @Valid @RequestBody Fatura fatura) {
        try {
            Fatura faturaSalva = faturaService.findById(id).get();
            if (faturaSalva == null) {
                throw new EmptyResultDataAccessException(1);
            }
            BeanUtils.copyProperties(fatura, faturaSalva, "id");
            faturaService.create(faturaSalva);
            return ResponseEntity.ok(faturaSalva);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        return faturaService.delete(id);
    }

}
