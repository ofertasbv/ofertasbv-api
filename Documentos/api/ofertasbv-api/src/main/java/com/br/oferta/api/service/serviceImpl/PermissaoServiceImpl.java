/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.oferta.api.service.serviceImpl;

import com.br.oferta.api.model.Permissao;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author fabio
 */
public interface PermissaoServiceImpl {

    List<Permissao> findBySort();

    Optional<Permissao> findById(Long id);

    Permissao create(Permissao p);

    ResponseEntity update(Long id, Permissao p);

    ResponseEntity delete(Long id);

}
