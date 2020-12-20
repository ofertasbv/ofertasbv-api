/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.oferta.api.service;

import com.br.oferta.api.service.serviceImpl.PermissaoServiceImpl;
import com.br.oferta.api.model.Permissao;
import com.br.oferta.api.repository.PermissaoRepository;
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
public class PermissaoService implements PermissaoServiceImpl {

    private final PermissaoRepository permissaoRepository;

    @PersistenceContext
    private final EntityManager em;

    @Autowired
    public PermissaoService(PermissaoRepository permissaoRepository, EntityManager em) {
        this.permissaoRepository = permissaoRepository;
        this.em = em;
    }

    @Override
    public List<Permissao> findBySort() {
        Query query = em.createQuery("SELECT a FROM Permissao a ORDER BY a.id DESC");
        return query.getResultList();
    }

    @Override
    public Optional<Permissao> findById(Long id) {
        return permissaoRepository.findById(id);
    }

    @Override
    public Permissao create(Permissao p) {
        return permissaoRepository.save(p);
    }

    @Override
    public ResponseEntity update(Long id, Permissao p) {
        Permissao salva = permissaoRepository.findById(id).get();
        if (salva == null) {
            throw new ServiceNotFoundExeception("Arquivo não encotrado com ID: " + id);
        }
        BeanUtils.copyProperties(p, salva, "id");
        permissaoRepository.save(salva);
        return ResponseEntity.ok(salva);
    }

    @Override
    public ResponseEntity delete(Long id) {
        Permissao exclui = permissaoRepository.findById(id).get();
        if (exclui == null) {
            throw new EmptyResultDataAccessException(1);
        }

        permissaoRepository.deleteById(id);
        return ResponseEntity.ok(exclui);
    }

}
