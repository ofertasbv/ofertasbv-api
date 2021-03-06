/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.oferta.api.service.serviceImpl;

import com.br.oferta.api.model.Parcela;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author fabio
 */
public interface FaturaServiceImpl {

    List<Parcela> findBySort();

    Optional<Parcela> findById(Long id);

    Parcela create(Parcela f);

    ResponseEntity update(Long id, Parcela f);

    ResponseEntity delete(Long id);
}
