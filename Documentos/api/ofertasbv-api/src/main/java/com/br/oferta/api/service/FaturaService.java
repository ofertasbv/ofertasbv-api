/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.oferta.api.service;

import com.br.oferta.api.service.serviceImpl.FaturaServiceImpl;
import com.br.oferta.api.model.Fatura;
import com.br.oferta.api.repository.FaturaRepository;
import com.br.oferta.api.util.error.ServiceNotFoundExeception;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 *
 * @author fabio
 */
@Service
public class FaturaService implements FaturaServiceImpl {

    private final FaturaRepository faturaRepository;

    @PersistenceContext
    private final EntityManager em;

    public FaturaService(FaturaRepository faturaRepository, EntityManager em) {
        this.faturaRepository = faturaRepository;
        this.em = em;
    }

    @Override
    public List<Fatura> findBySort() {
        Query query = em.createQuery("SELECT a FROM Fatura a ORDER BY a.id DESC");
        return query.getResultList();
    }

    @Override
    public Optional<Fatura> findById(Long id) {
        return faturaRepository.findById(id);
    }

    @Override
    public Fatura create(Fatura f) {
        return faturaRepository.saveAndFlush(f);
    }

    @Override
    public ResponseEntity update(Long id, Fatura f) {
        Fatura salva = faturaRepository.findById(id).get();
        if (salva == null) {
            throw new ServiceNotFoundExeception("Fatura não encotrado com ID: " + id);
        }
        BeanUtils.copyProperties(f, salva, "id");
        faturaRepository.save(salva);
        return ResponseEntity.ok(salva);
    }

    @Override
    public ResponseEntity delete(Long id) {
        Fatura exclui = faturaRepository.findById(id).get();
        if (exclui == null) {
            throw new EmptyResultDataAccessException(1);
        }

        faturaRepository.deleteById(id);
        return ResponseEntity.ok(exclui);
    }

}
