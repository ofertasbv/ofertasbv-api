/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.oferta.api.service.serviceImpl;

import com.br.oferta.api.model.CaixaFluxoSaida;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author fabio
 */
public interface CaixaFluxoSaidaServiceImpl {

    List<CaixaFluxoSaida> findBySort();

    Optional<CaixaFluxoSaida> findById(Long id);

    BigDecimal valorTotalByDataRegistro(LocalDate dataInicio, LocalDate dataFinal);

    CaixaFluxoSaida create(CaixaFluxoSaida a);

    ResponseEntity update(Long id, CaixaFluxoSaida a);

    ResponseEntity delete(Long id);

}
