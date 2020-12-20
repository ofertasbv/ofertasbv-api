/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.oferta.api.service.serviceImpl;

import com.br.oferta.api.model.Caixa;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author fabio
 */
public interface CaixaServiceImpl {

    List<Caixa> findBySort();

    Optional<Caixa> findById(Long id);

    Caixa create(Caixa a);

    ResponseEntity update(Long id, Caixa a);

    ResponseEntity delete(Long id);

}
