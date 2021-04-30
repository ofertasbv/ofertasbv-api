/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.oferta.api.service.serviceImpl;

import com.br.oferta.api.model.Estoque;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author fabio
 */
public interface EstoqueServiceImpl {

    List<Estoque> findBySort();

    Optional<Estoque> findById(Long id);

    Estoque create(Estoque e);

    ResponseEntity update(Long id, Estoque e);

    ResponseEntity delete(Long id);
}
