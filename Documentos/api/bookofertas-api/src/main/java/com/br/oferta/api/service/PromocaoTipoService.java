/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.oferta.api.service;

import com.br.oferta.api.model.PromocaoTipo;
import com.br.oferta.api.repository.PromocaoTpoRepository;
import com.br.oferta.api.service.serviceImpl.PromocaoTipoServiceImpl;
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
public class PromocaoTipoService implements PromocaoTipoServiceImpl {

    private final PromocaoTpoRepository promocaoTpoRepository;

    @PersistenceContext
    private final EntityManager em;

    @Autowired
    public PromocaoTipoService(PromocaoTpoRepository promocaoTpoRepository, EntityManager em) {
        this.promocaoTpoRepository = promocaoTpoRepository;
        this.em = em;
    }

    @Override
    public List<PromocaoTipo> findBySort() {
        Query query = em.createQuery("SELECT s FROM PromocaoTipo s ORDER BY s.id ASC");
        return query.getResultList();
    }

    @Override
    public Optional<PromocaoTipo> findById(Long id) {
        return promocaoTpoRepository.findById(id);
    }

    @Override
    public PromocaoTipo create(PromocaoTipo c) {
        return promocaoTpoRepository.save(c);
    }

    @Override
    public ResponseEntity update(Long id, PromocaoTipo c) {
        PromocaoTipo salva = promocaoTpoRepository.findById(id).get();
        if (salva == null) {
            throw new ServiceNotFoundExeception("promoção tipo não encotrado com ID: " + id);
        }
        BeanUtils.copyProperties(c, salva, "id");
        promocaoTpoRepository.save(salva);
        return ResponseEntity.ok(salva);
    }

    @Override
    public ResponseEntity delete(Long id) {
        PromocaoTipo exclui = promocaoTpoRepository.findById(id).get();
        if (exclui == null) {
            throw new EmptyResultDataAccessException(1);
        }

        promocaoTpoRepository.deleteById(id);
        return ResponseEntity.ok(exclui);
    }

}
