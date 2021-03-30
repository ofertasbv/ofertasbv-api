/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.oferta.api.service;

import com.br.oferta.api.model.Medida;
import com.br.oferta.api.repository.MedidaRepository;
import com.br.oferta.api.service.serviceImpl.MedidaServiceImpl;
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
public class MedidaService implements MedidaServiceImpl {

    private final MedidaRepository medidaRepository;

    @PersistenceContext
    private final EntityManager em;

    @Autowired
    public MedidaService(MedidaRepository medidaRepository, EntityManager em) {
        this.medidaRepository = medidaRepository;
        this.em = em;
    }

    @Override
    public List<Medida> findBySort() {
        Query query = em.createQuery("SELECT s FROM Medida s");
        return query.getResultList();
    }

    @Override
    public Optional<Medida> findById(Long id) {
        return medidaRepository.findById(id);
    }

    @Override
    public List<Medida> findByNome(String nome) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Medida> query = criteriaBuilder.createQuery(Medida.class);
        Root<Medida> n = query.from(Medida.class);

        javax.persistence.criteria.Path<String> nomePath = n.<String>get("nome");
        List<Predicate> predicates = new ArrayList<>();

        if (nome != null) {
            Predicate paramentro = criteriaBuilder.like(criteriaBuilder.lower(nomePath), "%" + nome.toLowerCase() + "%");
            predicates.add(paramentro);
        }

        query.where((Predicate[]) predicates.toArray(new Predicate[0]));
        query.orderBy(criteriaBuilder.desc(n.get("id")));
        TypedQuery<Medida> typedQuery = em.createQuery(query);

        return typedQuery.getResultList();
    }

    @Override
    public Medida create(Medida c) {
        return medidaRepository.save(c);
    }

    @Override
    public ResponseEntity update(Long id, Medida c) {
        Medida salva = medidaRepository.findById(id).get();
        if (salva == null) {
            throw new ServiceNotFoundExeception("Medida não encotrado com ID: " + id);
        }
        BeanUtils.copyProperties(c, salva, "id");
        medidaRepository.save(salva);
        return ResponseEntity.ok(salva);
    }

    @Override
    public ResponseEntity delete(Long id) {
        Medida exclui = medidaRepository.findById(id).get();
        if (exclui == null) {
            throw new EmptyResultDataAccessException(1);
        }

        medidaRepository.deleteById(id);
        return ResponseEntity.ok(exclui);
    }

}
