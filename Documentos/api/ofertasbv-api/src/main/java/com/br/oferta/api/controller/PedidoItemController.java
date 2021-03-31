package com.br.oferta.api.controller;

import com.br.oferta.api.model.PedidoItem;
import com.br.oferta.api.service.PedidoItemService;
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
import org.springframework.beans.BeanUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/pedidositens")
@Api(value = "API REST E-COMMERCE")
@CrossOrigin(origins = "*")
public class PedidoItemController {

    @Autowired
    private PedidoItemService pedidoItemService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @CrossOrigin(maxAge = 10, allowCredentials = "false") //origins = "http://localhost:8080/categorias")
    @GetMapping
//    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope('read')")
    public List<PedidoItem> findAll() {
        return pedidoItemService.findBySort();
    }

    @GetMapping("/{id}")
//    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope('read')")
    public ResponseEntity<PedidoItem> findById(@PathVariable Long id) {
        Optional<PedidoItem> pedido = pedidoItemService.findById(id);
        return pedido.isPresent() ? ResponseEntity.ok(pedido.get()) : ResponseEntity.notFound().build();
    }

    @CrossOrigin(maxAge = 10, allowCredentials = "false") //origins = "http://localhost:8080/categorias")
    @GetMapping("/produto/{nome}")
//    @PreAuthorize("hasAuthority('ROLE_PESQUISAR') and #oauth2.hasScope('read')")
    public List<PedidoItem> findAllByNome(@PathVariable String nome) {
        return pedidoItemService.findByNome(nome);
    }

    @PostMapping("/create")
//    @PreAuthorize("hasAuthority('ROLE_CADASTRAR_CATEGORIA') and #oauth2.hasScope('write')")
    public ResponseEntity<PedidoItem> criar(@Valid @RequestBody PedidoItem pedido, HttpServletResponse response) {
        PedidoItem pedidoSalva = pedidoItemService.create(pedido);
        publisher.publishEvent(new RecursoCriadoEvent(this, response, pedidoSalva.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoSalva);
    }

    @PutMapping("/update/{id}")
//    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA')")
    public ResponseEntity<PedidoItem> update(@PathVariable Long id, @Valid @RequestBody PedidoItem pedido) {
        try {
            PedidoItem pedidoSalva = pedidoItemService.findById(id).get();
            if (pedidoSalva == null) {
                throw new EmptyResultDataAccessException(1);
            }
            BeanUtils.copyProperties(pedido, pedidoSalva, "id");
            pedidoItemService.create(pedidoSalva);
            return ResponseEntity.ok(pedidoSalva);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        return pedidoItemService.delete(id);
    }

}
