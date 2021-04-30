/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.oferta.api.service.serviceImpl;

import com.br.oferta.api.model.Favorito;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author fabio
 */
public interface FavoritoServiceImpl {

    List<Favorito> findBySort();

    Optional<Favorito> findById(Long id);

    Favorito create(Favorito c);

    ResponseEntity update(Long id, Favorito c);

    ResponseEntity delete(Long id);

}
