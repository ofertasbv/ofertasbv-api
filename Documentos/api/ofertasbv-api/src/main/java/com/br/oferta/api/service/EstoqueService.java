/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.oferta.api.service;

import com.br.oferta.api.service.serviceImpl.EstoqueServiceImpl;
import com.br.oferta.api.model.Estoque;
import com.br.oferta.api.repository.EstoqueRepository;
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
public class EstoqueService implements EstoqueServiceImpl {

    private final EstoqueRepository estoqueRepository;

    @PersistenceContext
    private final EntityManager em;

    @Autowired
    public EstoqueService(EstoqueRepository estoqueRepository, EntityManager em) {
        this.estoqueRepository = estoqueRepository;
        this.em = em;
    }

    @Override
    public List<Estoque> findBySort() {
        Query query = em.createQuery("SELECT a FROM Estoque a ORDER BY a.id ASC");
        return query.getResultList();
    }

    @Override
    public Optional<Estoque> findById(Long id) {
        return estoqueRepository.findById(id);
    }

    @Override
    public Estoque create(Estoque a) {
        return estoqueRepository.save(a);
    }

    @Override
    public ResponseEntity update(Long id, Estoque a) {
        Estoque salva = estoqueRepository.findById(id).get();
        if (salva == null) {
            throw new ServiceNotFoundExeception("Estoque não encotrado com ID: " + id);
        }
        BeanUtils.copyProperties(a, salva, "id");
        estoqueRepository.save(salva);
        return ResponseEntity.ok(salva);
    }

    @Override
    public ResponseEntity delete(Long id) {
        Estoque exclui = estoqueRepository.findById(id).get();
        if (exclui == null) {
            throw new EmptyResultDataAccessException(1);
        }

        estoqueRepository.deleteById(id);
        return ResponseEntity.ok(exclui);
    }

}
