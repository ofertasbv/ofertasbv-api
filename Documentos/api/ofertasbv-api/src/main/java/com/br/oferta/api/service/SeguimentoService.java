/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.oferta.api.service;

import com.br.oferta.api.model.Seguimento;
import com.br.oferta.api.repository.SeguimentoRepository;
import com.br.oferta.api.service.serviceImpl.SeguimentoServiceImpl;
import com.br.oferta.api.util.error.ServiceNotFoundExeception;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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
public class SeguimentoService implements SeguimentoServiceImpl {

    private final SeguimentoRepository seguimentoRepository;

    @PersistenceContext
    private final EntityManager em;

    @Autowired
    public SeguimentoService(SeguimentoRepository seguimentoRepository, EntityManager em) {
        this.seguimentoRepository = seguimentoRepository;
        this.em = em;
    }

    @Override
    public List<Seguimento> findBySort() {
        Query query = em.createQuery("SELECT s FROM Seguimento s");
        return query.getResultList();
    }

    @Override
    public Optional<Seguimento> findById(Long id) {
        return seguimentoRepository.findById(id);
    }

    @Override
    public List<Seguimento> findByNome(String nome) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Seguimento> query = criteriaBuilder.createQuery(Seguimento.class);
        Root<Seguimento> n = query.from(Seguimento.class);

        javax.persistence.criteria.Path<String> nomePath = n.<String>get("nome");
        List<Predicate> predicates = new ArrayList<>();

        if (nome != null) {
            Predicate paramentro = criteriaBuilder.like(criteriaBuilder.lower(nomePath), "%" + nome.toLowerCase() + "%");
            predicates.add(paramentro);
        }

        query.where((Predicate[]) predicates.toArray(new Predicate[0]));
        query.orderBy(criteriaBuilder.desc(n.get("id")));
        TypedQuery<Seguimento> typedQuery = em.createQuery(query);

        return typedQuery.getResultList();
    }

    @Override
    public Seguimento create(Seguimento c) {
        return seguimentoRepository.save(c);
    }

    @Override
    public ResponseEntity update(Long id, Seguimento c) {
        Seguimento salva = seguimentoRepository.findById(id).get();
        if (salva == null) {
            throw new ServiceNotFoundExeception("Seguimento não encotrado com ID: " + id);
        }
        BeanUtils.copyProperties(c, salva, "id");
        seguimentoRepository.save(salva);
        return ResponseEntity.ok(salva);
    }

    @Override
    public ResponseEntity delete(Long id) {
        Seguimento exclui = seguimentoRepository.findById(id).get();
        if (exclui == null) {
            throw new EmptyResultDataAccessException(1);
        }

        seguimentoRepository.deleteById(id);
        return ResponseEntity.ok(exclui);
    }

}
