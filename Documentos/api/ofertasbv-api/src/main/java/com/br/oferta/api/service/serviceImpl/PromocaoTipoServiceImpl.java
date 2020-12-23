/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.oferta.api.service.serviceImpl;

import com.br.oferta.api.model.PromocaoTipo;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author fabio
 */
public interface PromocaoTipoServiceImpl {

    List<PromocaoTipo> findBySort();

    Optional<PromocaoTipo> findById(Long id);

    PromocaoTipo create(PromocaoTipo a);

    ResponseEntity update(Long id, PromocaoTipo a);

    ResponseEntity delete(Long id);

}
