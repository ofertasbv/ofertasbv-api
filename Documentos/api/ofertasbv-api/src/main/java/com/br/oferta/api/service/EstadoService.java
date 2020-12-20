/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.oferta.api.service;

import com.br.oferta.api.model.Estado;
import com.br.oferta.api.repository.EstadoRepository;
import com.br.oferta.api.service.serviceImpl.EstadoServiceImpl;
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
public class EstadoService implements EstadoServiceImpl {

    private final EstadoRepository estadoRepository;

    @PersistenceContext
    private final EntityManager em;

    @Autowired
    public EstadoService(EstadoRepository estadoRepository, EntityManager em) {
        this.estadoRepository = estadoRepository;
        this.em = em;
    }

    @Override
    public List<Estado> findBySort() {
        Query query = em.createQuery("SELECT a FROM Estado a ORDER BY a.id ASC");
        return query.getResultList();
    }

    @Override
    public Optional<Estado> findById(Long id) {
        return estadoRepository.findById(id);
    }

    @Override
    public Estado create(Estado a) {
        return estadoRepository.save(a);
    }

    @Override
    public ResponseEntity update(Long id, Estado a) {
        Estado salva = estadoRepository.findById(id).get();
        if (salva == null) {
            throw new ServiceNotFoundExeception("Estado não encotrado com ID: " + id);
        }
        BeanUtils.copyProperties(a, salva, "id");
        estadoRepository.save(salva);
        return ResponseEntity.ok(salva);
    }

    @Override
    public ResponseEntity delete(Long id) {
        Estado exclui = estadoRepository.findById(id).get();
        if (exclui == null) {
            throw new EmptyResultDataAccessException(1);
        }

        estadoRepository.deleteById(id);
        return ResponseEntity.ok(exclui);
    }

}
