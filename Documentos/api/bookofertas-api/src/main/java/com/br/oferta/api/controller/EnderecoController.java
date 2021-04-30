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
import com.br.oferta.api.model.Endereco;
import com.br.oferta.api.service.EnderecoService;
import io.swagger.annotations.Api;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/enderecos")
@Api(value = "API REST E-COMMERCE")
@CrossOrigin(origins = "*")
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @CrossOrigin(maxAge = 10, allowCredentials = "false") //origins = "http://localhost:8080/categorias")
    @GetMapping
//    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope('read')")
    public List<Endereco> findAll() {
        return enderecoService.findBySort();
    }

    @GetMapping("/{id}")
//    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope('read')")
    public ResponseEntity<Endereco> findById(@PathVariable Long id) {
        Optional<Endereco> endereco = enderecoService.findById(id);
        return endereco.isPresent() ? ResponseEntity.ok(endereco.get()) : ResponseEntity.notFound().build();
    }

    @GetMapping("/pessoa/{id}")
    public List<Endereco> findAllByPessoa(@PathVariable Long id) {
        return enderecoService.findAllByPessoa(id);
    }

    @PostMapping("/create")
//    @PreAuthorize("hasAuthority('ROLE_CADASTRAR_CATEGORIA') and #oauth2.hasScope('write')")
    public ResponseEntity<Endereco> criar(@Validated @RequestBody Endereco endereco, HttpServletResponse response) {
        Endereco enderecoSalva = enderecoService.create(endereco);
        publisher.publishEvent(new RecursoCriadoEvent(this, response, enderecoSalva.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(enderecoSalva);
    }

    @PutMapping("/update/{id}")
//    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA')")
    public ResponseEntity<Endereco> atualizar(@PathVariable Long id, @Validated @RequestBody Endereco endereco) {
        try {
            Endereco enderecoSalva = enderecoService.findById(id).get();
            if (enderecoSalva == null) {
                throw new EmptyResultDataAccessException(1);
            }
            BeanUtils.copyProperties(endereco, enderecoSalva, "id");
            enderecoService.create(enderecoSalva);
            return ResponseEntity.ok(enderecoSalva);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        return enderecoService.delete(id);
    }

}
