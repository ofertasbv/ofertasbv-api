/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.oferta.api.service.serviceImpl;

import com.br.oferta.api.model.Arquivo;
import com.br.oferta.api.model.Marca;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author fabio
 */
public interface MarcaServiceImpl {

    List<Marca> findBySort();

    Optional<Marca> findById(Long id);

    Marca create(Marca m);

    ResponseEntity update(Long id, Marca a);

    ResponseEntity delete(Long id);
}
