/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.oferta.api.service.serviceImpl;

import com.br.oferta.api.model.Vendedor;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author fabio
 */
public interface VendedorServiceImpl {

    List<Vendedor> findBySort();

    Optional<Vendedor> findById(Long id);

    Vendedor findByTelefone(String telefone);

    List<Vendedor> findByNome(String nome);

    List<Vendedor> findByNomeContaining(String nome);

    Vendedor create(Vendedor p);

    ResponseEntity update(Long id, Vendedor p);

    ResponseEntity delete(Long id);

    void excluirFoto(String foto);
}
