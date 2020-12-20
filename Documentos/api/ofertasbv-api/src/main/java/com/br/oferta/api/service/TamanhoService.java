/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.oferta.api.service;

import com.br.oferta.api.model.Tamanho;
import com.br.oferta.api.repository.TamanhoRepository;
import com.br.oferta.api.service.serviceImpl.TamanhoServiceImpl;
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
public class TamanhoService implements TamanhoServiceImpl {

    private final TamanhoRepository tamanhoRepository;

    @PersistenceContext
    private final EntityManager em;

    @Autowired
    public TamanhoService(TamanhoRepository tamanhoRepository, EntityManager em) {
        this.tamanhoRepository = tamanhoRepository;
        this.em = em;
    }

    @Override
    public Tamanho create(Tamanho c) {
        return tamanhoRepository.save(c);
    }

    @Override
    public ResponseEntity update(Long id, Tamanho c) {
        Tamanho salva = tamanhoRepository.findById(id).get();
        if (salva == null) {
            throw new ServiceNotFoundExeception("tamanho não encotrado com ID: " + id);
        }
        BeanUtils.copyProperties(c, salva, "id");
        tamanhoRepository.save(salva);
        return ResponseEntity.ok(salva);
    }

    @Override
    public ResponseEntity delete(Long id) {
        Tamanho exclui = tamanhoRepository.findById(id).get();
        if (exclui == null) {
            throw new EmptyResultDataAccessException(1);
        }

        tamanhoRepository.deleteById(id);
        return ResponseEntity.ok(exclui);
    }

    @Override
    public List<Tamanho> findBySort() {
        Query query = em.createQuery("SELECT s FROM Tamanho s ORDER BY s.id ASC");
        return query.getResultList();
    }

    @Override
    public Optional<Tamanho> findById(Long id) {
        return tamanhoRepository.findById(id);
    }

}
