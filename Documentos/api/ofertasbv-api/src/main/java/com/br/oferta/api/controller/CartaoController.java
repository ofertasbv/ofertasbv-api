package com.br.oferta.api.controller;

import com.br.oferta.api.model.Cartao;
import com.br.oferta.api.service.CartaoService;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/cartoes")
@Api(value = "API REST E-COMMERCE")
@CrossOrigin(origins = "*")
public class CartaoController {

    private final Logger logger = LoggerFactory.getLogger(CartaoController.class);

    @Autowired
    private CartaoService cartaoService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @CrossOrigin(maxAge = 10, allowCredentials = "false") //origins = "http://localhost:8080/categorias")
    @GetMapping
//    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope('read')")
    public List<Cartao> findAll() {
        return cartaoService.findBySort();
    }

    @GetMapping("/{id}")
//    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope('read')")
    public ResponseEntity<Cartao> findById(@PathVariable Long id) {
        Optional<Cartao> cor = cartaoService.findById(id);
        return cor.isPresent() ? ResponseEntity.ok(cor.get()) : ResponseEntity.notFound().build();
    }

    @PostMapping("/create")
//    @PreAuthorize("hasAuthority('ROLE_CADASTRAR_CATEGORIA') and #oauth2.hasScope('write')")
    public ResponseEntity<Cartao> create(@Validated @RequestBody Cartao cartao, HttpServletResponse response) {
        Cartao salva = cartaoService.create(cartao);
        publisher.publishEvent(new RecursoCriadoEvent(this, response, salva.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(salva);
    }

    @PutMapping("/update/{id}")
//    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA')")
    public ResponseEntity<Cartao> update(@PathVariable Long id, @Validated @RequestBody Cartao cartao) {
        try {
            Cartao salva = cartaoService.findById(id).get();
            if (salva == null) {
                throw new EmptyResultDataAccessException(1);
            }
            BeanUtils.copyProperties(cartao, salva, "id");
            cartaoService.create(salva);
            return ResponseEntity.ok(salva);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        return cartaoService.delete(id);
    }

}
