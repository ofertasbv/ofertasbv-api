/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.oferta.api.service;

import com.br.oferta.api.model.CaixaFluxo;
import com.br.oferta.api.repository.CaixaFluxoRepository;
import com.br.oferta.api.service.serviceImpl.CaixaFluxoServiceImpl;
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
public class CaixaFluxoService implements CaixaFluxoServiceImpl {

    private final CaixaFluxoRepository caixaFluxoRepository;

    @PersistenceContext
    private final EntityManager em;

    @Autowired
    public CaixaFluxoService(CaixaFluxoRepository caixaFluxoRepository, EntityManager em) {
        this.caixaFluxoRepository = caixaFluxoRepository;
        this.em = em;
    }

    @Override
    public List<CaixaFluxo> findBySort() {
        Query query = em.createQuery("SELECT s FROM CaixaFluxo s ORDER BY s.id ASC");
        return query.getResultList();
    }

    @Override
    public Optional<CaixaFluxo> findById(Long id) {
        return caixaFluxoRepository.findById(id);
    }

    @Override
    public CaixaFluxo create(CaixaFluxo c) {
        return caixaFluxoRepository.save(c);
    }

    @Override
    public ResponseEntity update(Long id, CaixaFluxo c) {
        CaixaFluxo salva = caixaFluxoRepository.findById(id).get();
        if (salva == null) {
            throw new ServiceNotFoundExeception("caixa fluxo não encotrado com ID: " + id);
        }
        BeanUtils.copyProperties(c, salva, "id");
        caixaFluxoRepository.save(salva);
        return ResponseEntity.ok(salva);
    }

    @Override
    public ResponseEntity delete(Long id) {
        CaixaFluxo exclui = caixaFluxoRepository.findById(id).get();
        if (exclui == null) {
            throw new EmptyResultDataAccessException(1);
        }

        caixaFluxoRepository.deleteById(id);
        return ResponseEntity.ok(exclui);
    }

}
