/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.oferta.api.service;

import com.br.oferta.api.model.Caixa;
import com.br.oferta.api.repository.CaixaRepository;
import com.br.oferta.api.service.serviceImpl.CaixaServiceImpl;
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
public class CaixaService implements CaixaServiceImpl {

    private final CaixaRepository caixaRepository;

    @PersistenceContext
    private final EntityManager em;

    @Autowired
    public CaixaService(CaixaRepository caixaRepository, EntityManager em) {
        this.caixaRepository = caixaRepository;
        this.em = em;
    }

    @Override
    public List<Caixa> findBySort() {
        Query query = em.createQuery("SELECT s FROM Caixa s ORDER BY s.id ASC");
        return query.getResultList();
    }

    @Override
    public Optional<Caixa> findById(Long id) {
        return caixaRepository.findById(id);
    }

    @Override
    public Caixa create(Caixa c) {
        return caixaRepository.save(c);
    }

    @Override
    public ResponseEntity update(Long id, Caixa c) {
        Caixa salva = caixaRepository.findById(id).get();
        if (salva == null) {
            throw new ServiceNotFoundExeception("caixa não encotrado com ID: " + id);
        }
        BeanUtils.copyProperties(c, salva, "id");
        caixaRepository.save(salva);
        return ResponseEntity.ok(salva);
    }

    @Override
    public ResponseEntity delete(Long id) {
        Caixa exclui = caixaRepository.findById(id).get();
        if (exclui == null) {
            throw new EmptyResultDataAccessException(1);
        }

        caixaRepository.deleteById(id);
        return ResponseEntity.ok(exclui);
    }

}
