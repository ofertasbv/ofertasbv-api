/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.oferta.api.service;

import com.br.oferta.api.model.Cor;
import com.br.oferta.api.repository.CorRepository;
import com.br.oferta.api.service.serviceImpl.CorServiceImpl;
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
public class CorService implements CorServiceImpl {

    private final CorRepository corRepository;

    @PersistenceContext
    private final EntityManager em;

    @Autowired
    public CorService(CorRepository corRepository, EntityManager em) {
        this.corRepository = corRepository;
        this.em = em;
    }

    @Override
    public List<Cor> findBySort() {
        Query query = em.createQuery("SELECT s FROM Cor s ORDER BY s.id ASC");
        return query.getResultList();
    }

    @Override
    public Optional<Cor> findById(Long id) {
        return corRepository.findById(id);
    }

    @Override
    public Cor create(Cor c) {
        return corRepository.save(c);
    }

    @Override
    public ResponseEntity update(Long id, Cor c) {
        Cor salva = corRepository.findById(id).get();
        if (salva == null) {
            throw new ServiceNotFoundExeception("cor não encotrado com ID: " + id);
        }
        BeanUtils.copyProperties(c, salva, "id");
        corRepository.save(salva);
        return ResponseEntity.ok(salva);
    }

    @Override
    public ResponseEntity delete(Long id) {
        Cor exclui = corRepository.findById(id).get();
        if (exclui == null) {
            throw new EmptyResultDataAccessException(1);
        }

        corRepository.deleteById(id);
        return ResponseEntity.ok(exclui);
    }

}
