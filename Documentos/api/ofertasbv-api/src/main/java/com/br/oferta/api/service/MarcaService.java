/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.oferta.api.service;

import com.br.oferta.api.model.Categoria;
import com.br.oferta.api.model.Marca;
import com.br.oferta.api.repository.MarcaRepository;
import com.br.oferta.api.service.serviceImpl.MarcaServiceImpl;
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
public class MarcaService implements MarcaServiceImpl {

    private final MarcaRepository marcaRepository;

    @PersistenceContext
    private final EntityManager em;

    @Autowired
    public MarcaService(MarcaRepository marcaRepository, EntityManager em) {
        this.marcaRepository = marcaRepository;
        this.em = em;
    }

    @Override
    public List<Marca> findBySort() {
        Query query = em.createQuery("SELECT a FROM Marca a ORDER BY a.id DESC");
        return query.getResultList();
    }

    @Override
    public Optional<Marca> findById(Long id) {
        return marcaRepository.findById(id);
    }

    @Override
    public List<Marca> findByNome(String nome) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Marca> query = criteriaBuilder.createQuery(Marca.class);
        Root<Marca> n = query.from(Marca.class);

        javax.persistence.criteria.Path<String> nomePath = n.<String>get("nome");
        List<Predicate> predicates = new ArrayList<>();

        if (nome != null) {
            Predicate paramentro = criteriaBuilder.like(criteriaBuilder.lower(nomePath), "%" + nome.toLowerCase() + "%");
            predicates.add(paramentro);
        }

        query.where((Predicate[]) predicates.toArray(new Predicate[0]));
        query.orderBy(criteriaBuilder.desc(n.get("id")));
        TypedQuery<Marca> typedQuery = em.createQuery(query);

        return typedQuery.getResultList();
    }

    @Override
    public Marca create(Marca m) {
//        if (m.getId() != null) {
//            Optional<Marca> existe = marcaRepository.findById(m.getId());
//            if (existe.isPresent()) {
//                throw new ServiceNotFoundExeception("Marca já exixte: " + m.getId());
//            }
//        }

        return marcaRepository.saveAndFlush(m);
    }

    @Override
    public ResponseEntity update(Long id, Marca m) {
        Marca salva = marcaRepository.findById(id).get();
        if (salva == null) {
            throw new ServiceNotFoundExeception("Marca não encotrado com ID: " + id);
        }
        BeanUtils.copyProperties(m, salva, "id");
        marcaRepository.save(salva);
        return ResponseEntity.ok(salva);
    }

    @Override
    public ResponseEntity delete(Long id) {
        Marca exclui = marcaRepository.findById(id).get();
        if (exclui == null) {
            throw new EmptyResultDataAccessException(1);
        }

        marcaRepository.deleteById(id);
        return ResponseEntity.ok(exclui);
    }

}
