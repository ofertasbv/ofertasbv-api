/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.oferta.api.service;

import com.br.oferta.api.service.serviceImpl.UsuarioServiceImpl;
import com.br.oferta.api.model.Usuario;
import com.br.oferta.api.repository.UsuarioRepository;
import com.br.oferta.api.util.error.ServiceNotFoundExeception;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 *
 * @author fabio
 */
@Service
public class UsuarioService implements UsuarioServiceImpl {

    private final UsuarioRepository usuarioRepository;

    @PersistenceContext
    private final EntityManager em;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, EntityManager em) {
        this.usuarioRepository = usuarioRepository;
        this.em = em;
    }

    @Override
    public List<Usuario> findBySort() {
        Query query = em.createQuery("SELECT a FROM Usuario a ORDER BY a.id ASC");
        return query.getResultList();
    }

    @Override
    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }

    @Override
    public Usuario create(Usuario u) {
        return usuarioRepository.save(u);
    }

    @Override
    public ResponseEntity update(Long id, Usuario u) {
        Usuario salva = usuarioRepository.findById(id).get();
        if (salva == null) {
            throw new ServiceNotFoundExeception("Arquivo não encotrado com ID: " + id);
        }
        BeanUtils.copyProperties(u, salva, "id");
        usuarioRepository.save(salva);
        return ResponseEntity.ok(salva);
    }

    @Override
    public ResponseEntity delete(Long id) {
        Usuario exclui = usuarioRepository.findById(id).get();
        if (exclui == null) {
            throw new EmptyResultDataAccessException(1);
        }

        usuarioRepository.deleteById(id);
        return ResponseEntity.ok(exclui);
    }

    @Override
    public Usuario findByEmail(String email) {
        Query query = em.createQuery("SELECT p FROM Usuario p WHERE p.email =:email");
        query.setParameter("email", email);
        return (Usuario) query.getSingleResult();
    }

    @Override
    public List<Usuario> findByPermissoesDescricao(String permissaoDescricao) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Usuario findByLogin(String email, String senha) {
        Query query = em.createQuery("SELECT p FROM Usuario p WHERE p.email =:email AND p.senha =:senha");
        query.setParameter("email", email);
        query.setParameter("senha", senha);
        return (Usuario) query.getSingleResult();
    }

}
