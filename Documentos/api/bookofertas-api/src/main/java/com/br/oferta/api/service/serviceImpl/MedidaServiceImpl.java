/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.oferta.api.service.serviceImpl;

import com.br.oferta.api.model.Medida;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author fabio
 */
public interface MedidaServiceImpl {

    List<Medida> findBySort();

    Optional<Medida> findById(Long id);
    
    List<Medida> findByNome(String nome);

    Medida create(Medida m);

    ResponseEntity update(Long id, Medida m);

    ResponseEntity delete(Long id);

}
