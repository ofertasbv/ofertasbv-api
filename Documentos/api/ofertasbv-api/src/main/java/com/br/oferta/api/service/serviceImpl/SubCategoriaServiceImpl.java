/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.oferta.api.service.serviceImpl;

import com.br.oferta.api.model.SubCategoria;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author fabio
 */
public interface SubCategoriaServiceImpl {

    List<SubCategoria> findBySort();

    Optional<SubCategoria> findById(Long id);
    
    List<SubCategoria> findByNome(String nome);

    List<SubCategoria> findCategoriaById(Long id);

    SubCategoria create(SubCategoria c);

    ResponseEntity update(Long id, SubCategoria c);

    ResponseEntity delete(Long id);

}
