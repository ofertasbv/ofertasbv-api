/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.oferta.api.service.serviceImpl;

import com.br.oferta.api.model.Loja;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author fabio
 */
public interface LojaServiceImpl {

    List<Loja> findBySort();

    Optional<Loja> findById(Long id);
    
    List<Loja> findByNome(String nome);
    
    Loja create(Loja p);

    ResponseEntity update(Long id, Loja p);

    ResponseEntity delete(Long id);

    void excluirFoto(String foto);
}
