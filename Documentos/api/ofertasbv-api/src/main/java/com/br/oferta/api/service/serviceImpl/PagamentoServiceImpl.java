/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.oferta.api.service.serviceImpl;

import com.br.oferta.api.model.Pagamento;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author fabio
 */
public interface PagamentoServiceImpl {

    List<Pagamento> findBySort();

    Optional<Pagamento> findById(Long id);

    Pagamento create(Pagamento a);

    ResponseEntity update(Long id, Pagamento a);

    ResponseEntity delete(Long id);
}
