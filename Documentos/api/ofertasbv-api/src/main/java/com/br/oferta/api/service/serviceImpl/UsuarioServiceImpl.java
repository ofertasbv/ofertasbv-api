/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.oferta.api.service.serviceImpl;

import com.br.oferta.api.model.Usuario;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author fabio
 */
public interface UsuarioServiceImpl {

    List<Usuario> findBySort();

    Optional<Usuario> findById(Long id);

    Optional<Usuario> findByEmail(String email);

    Usuario findByLogin(String email, String senha);

    List<String> permissoes(Usuario usuario);

    Usuario create(Usuario u);

    ResponseEntity update(Long id, Usuario u);

    ResponseEntity delete(Long id);
}
