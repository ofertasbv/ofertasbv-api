/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.oferta.api.service.serviceImpl;

import com.br.oferta.api.model.PedidoItem;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author fabio
 */
public interface PedidoItemServiceImpl {

    List<PedidoItem> findBySort();

    Optional<PedidoItem> findById(Long id);

    PedidoItem create(PedidoItem p);

    ResponseEntity update(Long id, PedidoItem p);

    ResponseEntity delete(Long id);
}