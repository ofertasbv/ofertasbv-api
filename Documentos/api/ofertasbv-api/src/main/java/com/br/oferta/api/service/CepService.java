/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.oferta.api.service;

import com.br.oferta.api.model.Cep;
import com.br.oferta.api.repository.CepRepository;
import com.br.oferta.api.service.serviceImpl.CepServiceImpl;
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
public class CepService implements CepServiceImpl {

    private final CepRepository cepRepository;

    @PersistenceContext
    private final EntityManager em;

    @Autowired
    public CepService(CepRepository cepRepository, EntityManager em) {
        this.cepRepository = cepRepository;
        this.em = em;
    }

    @Override
    public List<Cep> findBySort() {
        Query query = em.createQuery("SELECT s Cep Cor s ORDER BY s.id ASC");
        return query.getResultList();
    }

    @Override
    public Optional<Cep> findById(Long id) {
        return cepRepository.findById(id);
    }

    @Override
    public Cep create(Cep c) {
        return cepRepository.save(c);
    }

    @Override
    public ResponseEntity update(Long id, Cep c) {
        Cep salva = cepRepository.findById(id).get();
        if (salva == null) {
            throw new ServiceNotFoundExeception("Cep não encotrado com ID: " + id);
        }
        BeanUtils.copyProperties(c, salva, "id");
        cepRepository.save(salva);
        return ResponseEntity.ok(salva);
    }

    @Override
    public ResponseEntity delete(Long id) {
        Cep exclui = cepRepository.findById(id).get();
        if (exclui == null) {
            throw new EmptyResultDataAccessException(1);
        }

        cepRepository.deleteById(id);
        return ResponseEntity.ok(exclui);
    }

}
