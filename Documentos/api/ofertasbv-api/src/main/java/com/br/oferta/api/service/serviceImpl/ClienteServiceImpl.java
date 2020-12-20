/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.oferta.api.service.serviceImpl;

import com.br.oferta.api.model.Cliente;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author fabio
 */
public interface ClienteServiceImpl {

    List<Cliente> findBySort();

    Optional<Cliente> findById(Long id);
    
    Cliente findByTelefone(String telefone);

    List<Cliente> findByNome(String nome);

    List<Cliente> findByNomeContaining(String nome);

    Cliente create(Cliente p);

    ResponseEntity update(Long id, Cliente p);

    ResponseEntity delete(Long id);

    void excluirFoto(String foto);
}
