/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.oferta.api.service.serviceImpl;

import com.br.oferta.api.model.Estado;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author fabio
 */
public interface EstadoServiceImpl {

    List<Estado> findBySort();

    Optional<Estado> findById(Long id);

    Estado create(Estado a);

    ResponseEntity update(Long id, Estado e);

    ResponseEntity delete(Long id);
}
