/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.oferta.api.service;

import com.br.oferta.api.model.Cidade;
import com.br.oferta.api.repository.CidadeRepository;
import com.br.oferta.api.service.serviceImpl.CidadeServiceImpl;
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
public class CidadeService implements CidadeServiceImpl {

    private final CidadeRepository cidadeRepository;

    @PersistenceContext
    private final EntityManager em;

    @Autowired
    public CidadeService(CidadeRepository cidadeRepository, EntityManager em) {
        this.cidadeRepository = cidadeRepository;
        this.em = em;
    }

    @Override
    public List<Cidade> findBySort() {
        Query query = em.createQuery("SELECT a FROM Cidade a ORDER BY a.id ASC");
        return query.getResultList();
    }

    @Override
    public Optional<Cidade> findById(Long id) {
        return cidadeRepository.findById(id);
    }

    @Override
    public Cidade create(Cidade a) {
        return cidadeRepository.save(a);
    }

    @Override
    public ResponseEntity update(Long id, Cidade a) {
        Cidade salva = cidadeRepository.findById(id).get();
        if (salva == null) {
            throw new ServiceNotFoundExeception("Cidade não encotrado com ID: " + id);
        }
        BeanUtils.copyProperties(a, salva, "id");
        cidadeRepository.save(salva);
        return ResponseEntity.ok(salva);
    }

    @Override
    public ResponseEntity delete(Long id) {
        Cidade exclui = cidadeRepository.findById(id).get();
        if (exclui == null) {
            throw new EmptyResultDataAccessException(1);
        }

        cidadeRepository.deleteById(id);
        return ResponseEntity.ok(exclui);
    }

    @Override
    public List<Cidade> findByEstadoId(Long id) {
        Query query = em.createQuery("SELECT c FROM Cidade c JOIN c.estado e WHERE e.id =:id");
        query.setParameter("id", id);
        return query.getResultList();
    }

}
