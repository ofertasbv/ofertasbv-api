/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.oferta.api.service.serviceImpl;

import com.br.oferta.api.model.CaixaFluxoEntrada;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author fabio
 */
public interface CaixaFluxoEntradaServiceImpl {

    List<CaixaFluxoEntrada> findBySort();

    Optional<CaixaFluxoEntrada> findById(Long id);

    CaixaFluxoEntrada create(CaixaFluxoEntrada a);

    ResponseEntity update(Long id, CaixaFluxoEntrada a);

    ResponseEntity delete(Long id);

}
