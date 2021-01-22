/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.oferta.api.service.serviceImpl;

import com.br.oferta.api.model.Endereco;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author fabio
 */
public interface EnderecoServiceImpl {

    List<Endereco> findBySort();
    
    List<Endereco> findAllByPessoa(Long id);

    Optional<Endereco> findById(Long id);
    
    Optional<Endereco> findByCep(String cep);

    Endereco create(Endereco e);

    ResponseEntity update(Long id, Endereco e);

    ResponseEntity delete(Long id);
}
