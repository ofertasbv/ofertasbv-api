/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.oferta.api.service.serviceImpl;

import com.br.oferta.api.model.Cor;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author fabio
 */
public interface CorServiceImpl {

    List<Cor> findBySort();

    Optional<Cor> findById(Long id);

    Cor create(Cor a);

    ResponseEntity update(Long id, Cor a);

    ResponseEntity delete(Long id);

}
