/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.oferta.api.service.serviceImpl;

import com.br.oferta.api.model.Tamanho;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author fabio
 */
public interface TamanhoServiceImpl {

    List<Tamanho> findBySort();

    Optional<Tamanho> findById(Long id);

    Tamanho create(Tamanho a);

    ResponseEntity update(Long id, Tamanho a);

    ResponseEntity delete(Long id);

}
