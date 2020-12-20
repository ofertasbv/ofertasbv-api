/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.oferta.api.service.serviceImpl;

import com.br.oferta.api.model.CaixaFluxo;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author fabio
 */
public interface CaixaFluxoServiceImpl {

    List<CaixaFluxo> findBySort();

    Optional<CaixaFluxo> findById(Long id);

    CaixaFluxo create(CaixaFluxo a);

    ResponseEntity update(Long id, CaixaFluxo a);

    ResponseEntity delete(Long id);

}
