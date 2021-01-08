/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.oferta.api.service.serviceImpl;

import com.br.oferta.api.model.Cep;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author fabio
 */
public interface CepServiceImpl {

    List<Cep> findBySort();

    Optional<Cep> findById(Long id);

    Cep create(Cep a);

    ResponseEntity update(Long id, Cep a);

    ResponseEntity delete(Long id);

}
