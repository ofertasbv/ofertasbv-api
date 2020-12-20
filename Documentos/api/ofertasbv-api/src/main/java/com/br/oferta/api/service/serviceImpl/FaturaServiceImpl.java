/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.oferta.api.service.serviceImpl;

import com.br.oferta.api.model.Fatura;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author fabio
 */
public interface FaturaServiceImpl {

    List<Fatura> findBySort();

    Optional<Fatura> findById(Long id);

    Fatura create(Fatura f);

    ResponseEntity update(Long id, Fatura f);

    ResponseEntity delete(Long id);
}
