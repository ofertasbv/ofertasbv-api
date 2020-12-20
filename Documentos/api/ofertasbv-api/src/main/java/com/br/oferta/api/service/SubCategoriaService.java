/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.oferta.api.service;

import com.br.oferta.api.service.serviceImpl.SubCategoriaServiceImpl;
import com.br.oferta.api.model.SubCategoria;
import com.br.oferta.api.repository.SubCategoriaRepository;
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
public class SubCategoriaService implements SubCategoriaServiceImpl {

    private final SubCategoriaRepository subCategoriaRepository;

    @PersistenceContext
    private final EntityManager em;

    @Autowired
    public SubCategoriaService(SubCategoriaRepository subCategoriaRepository, EntityManager em) {
        this.subCategoriaRepository = subCategoriaRepository;
        this.em = em;
    }

    @Override
    public List<SubCategoria> findBySort() {
        Query query = em.createQuery("SELECT s FROM SubCategoria s");
        return query.getResultList();
    }

    @Override
    public Optional<SubCategoria> findById(Long id) {
        return subCategoriaRepository.findById(id);
    }

    @Override
    public List<SubCategoria> findCategoriaById(Long id) {
        Query query = em.createQuery("SELECT s FROM SubCategoria s JOIN s.categoria c WHERE c.id =:id");
        query.setParameter("id", id);
        return query.getResultList();
    }

    @Override
    public List<SubCategoria> findByNome(String nome) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<SubCategoria> query = criteriaBuilder.createQuery(SubCategoria.class);
        Root<SubCategoria> n = query.from(SubCategoria.class);

        javax.persistence.criteria.Path<String> nomePath = n.<String>get("nome");
        List<Predicate> predicates = new ArrayList<>();

        if (nome != null) {
            Predicate paramentro = criteriaBuilder.like(criteriaBuilder.lower(nomePath), "%" + nome.toLowerCase() + "%");
            predicates.add(paramentro);
        }

        query.where((Predicate[]) predicates.toArray(new Predicate[0]));
        query.orderBy(criteriaBuilder.desc(n.get("id")));
        TypedQuery<SubCategoria> typedQuery = em.createQuery(query);

        return typedQuery.getResultList();
    }

    @Override
    public SubCategoria create(SubCategoria c) {
        return subCategoriaRepository.save(c);
    }

    @Override
    public ResponseEntity update(Long id, SubCategoria c) {
        SubCategoria subCategoriaSalva = subCategoriaRepository.findById(id).get();
        if (subCategoriaSalva == null) {
            throw new ServiceNotFoundExeception("subCategoria não encotrado com ID: " + id);
        }
        BeanUtils.copyProperties(c, subCategoriaSalva, "id");
        subCategoriaRepository.save(subCategoriaSalva);
        return ResponseEntity.ok(subCategoriaSalva);
    }

    @Override
    public ResponseEntity delete(Long id) {
        SubCategoria subCategoriaExclui = subCategoriaRepository.findById(id).get();
        if (subCategoriaExclui == null) {
            throw new EmptyResultDataAccessException(1);
        }

        subCategoriaRepository.deleteById(id);
        return ResponseEntity.ok(subCategoriaExclui);
    }

}
