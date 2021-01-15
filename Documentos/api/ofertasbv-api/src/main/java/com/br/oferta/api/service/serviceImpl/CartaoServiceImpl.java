/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.oferta.api.service.serviceImpl;

import com.br.oferta.api.model.Cartao;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author fabio
 */
public interface CartaoServiceImpl {

    List<Cartao> findBySort();

    Optional<Cartao> findById(Long id);

    Cartao create(Cartao a);

    ResponseEntity update(Long id, Cartao a);

    ResponseEntity delete(Long id);

}
