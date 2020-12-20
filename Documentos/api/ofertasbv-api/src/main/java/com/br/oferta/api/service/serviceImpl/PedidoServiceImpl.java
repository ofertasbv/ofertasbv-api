/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.oferta.api.service.serviceImpl;

import com.br.oferta.api.model.Pedido;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author fabio
 */
public interface PedidoServiceImpl {

    List<Pedido> findBySort();

    Optional<Pedido> findById(Long id);

    Pedido create(Pedido p);

    ResponseEntity update(Long id, Pedido p);

    ResponseEntity delete(Long id);
}
