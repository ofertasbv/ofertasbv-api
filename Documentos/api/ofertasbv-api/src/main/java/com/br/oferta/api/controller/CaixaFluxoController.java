package com.br.oferta.api.controller;

import com.br.oferta.api.model.Caixa;
import com.br.oferta.api.model.CaixaFluxo;
import com.br.oferta.api.service.CaixaFluxoService;
import com.br.oferta.api.service.CaixaService;
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
import io.swagger.annotations.Api;
import java.math.BigDecimal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/caixafluxos")
@Api(value = "API REST E-COMMERCE")
@CrossOrigin(origins = "*")
public class CaixaFluxoController {

    private final Logger logger = LoggerFactory.getLogger(CaixaFluxoController.class);

    @Autowired
    private CaixaFluxoService caixaFluxoService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @CrossOrigin(maxAge = 10, allowCredentials = "false") //origins = "http://localhost:8080/categorias")
    @GetMapping
//    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope('read')")
    public List<CaixaFluxo> findAll() {
        return caixaFluxoService.findBySort();
    }

    @GetMapping("/{id}")
//    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope('read')")
    public ResponseEntity<CaixaFluxo> findById(@PathVariable Long id) {
        Optional<CaixaFluxo> caixa = caixaFluxoService.findById(id);
        return caixa.isPresent() ? ResponseEntity.ok(caixa.get()) : ResponseEntity.notFound().build();
    }

    @GetMapping("/totalEntrada/{id}")
//    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope('read')")
    public BigDecimal somaTotalEntrada(@PathVariable Long id) {
        return caixaFluxoService.totalEntradaById(id);
    }

    @GetMapping("/totalSaida/{id}")
//    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope('read')")
    public BigDecimal somaTotalSaida(@PathVariable Long id) {
        return caixaFluxoService.totalSaidaById(id);
    }

    @PostMapping("/create")
//    @PreAuthorize("hasAuthority('ROLE_CADASTRAR_CATEGORIA') and #oauth2.hasScope('write')")
    public ResponseEntity<CaixaFluxo> create(@Valid @RequestBody CaixaFluxo caixa, HttpServletResponse response) {
        CaixaFluxo salva = caixaFluxoService.create(caixa);
        publisher.publishEvent(new RecursoCriadoEvent(this, response, salva.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(salva);
    }

    @PutMapping("/update/{id}")
//    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA')")
    public ResponseEntity<CaixaFluxo> update(@PathVariable Long id, @Valid @RequestBody CaixaFluxo caixa) {
        try {
            CaixaFluxo salva = caixaFluxoService.findById(id).get();
            if (salva == null) {
                throw new EmptyResultDataAccessException(1);
            }
            BeanUtils.copyProperties(caixa, salva, "id");
            caixaFluxoService.create(salva);
            return ResponseEntity.ok(salva);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        return caixaFluxoService.delete(id);
    }

}
