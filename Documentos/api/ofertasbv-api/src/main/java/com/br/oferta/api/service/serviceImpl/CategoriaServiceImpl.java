/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.oferta.api.service.serviceImpl;

import com.br.oferta.api.model.Categoria;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author fabio
 */
public interface CategoriaServiceImpl {

    List<Categoria> findBySort();

    Optional<Categoria> findById(Long id);

    List<Categoria> findBySeguimento(Long id);

    List<Categoria> findByNome(String nome);

    Categoria findBySubCategoriaId(Long id);

    Categoria create(Categoria c);

    ResponseEntity update(Long id, Categoria c);

    ResponseEntity delete(Long id);

    void excluirFoto(String foto);

}
