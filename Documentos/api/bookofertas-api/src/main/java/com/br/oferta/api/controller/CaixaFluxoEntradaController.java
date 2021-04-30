package com.br.oferta.api.controller;

import com.br.oferta.api.model.CaixaFluxoEntrada;
import com.br.oferta.api.service.CaixaFluxoEntradaService;
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
import io.swagger.annotations.Api;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/caixafluxoentradas")
@Api(value = "API REST E-COMMERCE")
@CrossOrigin(origins = "*")
public class CaixaFluxoEntradaController {

    private final Logger logger = LoggerFactory.getLogger(CaixaFluxoEntradaController.class);

    @Autowired
    private CaixaFluxoEntradaService caixaFluxoService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @CrossOrigin(maxAge = 10, allowCredentials = "false") //origins = "http://localhost:8080/categorias")
    @GetMapping
//    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope('read')")
    public List<CaixaFluxoEntrada> findAll() {
        return caixaFluxoService.findBySort();
    }

    @GetMapping("/{id}")
//    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope('read')")
    public ResponseEntity<CaixaFluxoEntrada> findById(@PathVariable Long id) {
        Optional<CaixaFluxoEntrada> caixa = caixaFluxoService.findById(id);
        return caixa.isPresent() ? ResponseEntity.ok(caixa.get()) : ResponseEntity.notFound().build();
    }

    @GetMapping("/valor/registro")
//    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope('read')")
    public BigDecimal valorTotalByDataRegistro(LocalDate dataInicio, LocalDate dataFinal) {
        BigDecimal valorEntrada = caixaFluxoService.valorTotalByDataRegistro(dataInicio, dataFinal);
        System.out.println("Valor total: " + valorEntrada);
        return valorEntrada;
    }

    @PostMapping("/create")
//    @PreAuthorize("hasAuthority('ROLE_CADASTRAR_CATEGORIA') and #oauth2.hasScope('write')")
    public ResponseEntity<CaixaFluxoEntrada> create(@Validated @RequestBody CaixaFluxoEntrada caixa, HttpServletResponse response) {
        CaixaFluxoEntrada salva = caixaFluxoService.create(caixa);
        publisher.publishEvent(new RecursoCriadoEvent(this, response, salva.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(salva);
    }

    @PutMapping("/update/{id}")
//    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA')")
    public ResponseEntity<CaixaFluxoEntrada> update(@PathVariable Long id, @Validated @RequestBody CaixaFluxoEntrada caixa) {
        try {
            CaixaFluxoEntrada salva = caixaFluxoService.findById(id).get();
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
