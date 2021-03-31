/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.oferta.api.service;

import com.br.oferta.api.service.serviceImpl.PedidoItemServiceImpl;
import com.br.oferta.api.model.PedidoItem;
import com.br.oferta.api.model.Produto;
import com.br.oferta.api.repository.PedidoItemRepository;
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
import javax.persistence.criteria.Path;
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
public class PedidoItemService implements PedidoItemServiceImpl {

    private final PedidoItemRepository pedidoItemRepository;

    @PersistenceContext
    private final EntityManager em;

    @Autowired
    public PedidoItemService(PedidoItemRepository pedidoItemRepository, EntityManager em) {
        this.pedidoItemRepository = pedidoItemRepository;
        this.em = em;
    }

    @Override
    public List<PedidoItem> findBySort() {
        Query query = em.createQuery("SELECT a FROM PedidoItem a ORDER BY a.id DESC");
        return query.getResultList();
    }

    @Override
    public Optional<PedidoItem> findById(Long id) {
        return pedidoItemRepository.findById(id);
    }

    @Override
    public List<PedidoItem> findByNome(String nome) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<PedidoItem> query = criteriaBuilder.createQuery(PedidoItem.class);
        Root<PedidoItem> n = query.from(PedidoItem.class);

        Path<String> nomePath = n.join("produto").<String>get("nome");
        List<Predicate> predicates = new ArrayList<>();

        if (nome != null) {
            Predicate paramentro = criteriaBuilder.like(criteriaBuilder.lower(nomePath), "%" + nome.toLowerCase() + "%");
            predicates.add(paramentro);
        }

        query.where((Predicate[]) predicates.toArray(new Predicate[0]));
        query.orderBy(criteriaBuilder.desc(n.get("id")));
        TypedQuery<PedidoItem> typedQuery = em.createQuery(query);

        return typedQuery.getResultList();
    }

    @Override
    public PedidoItem create(PedidoItem p) {
        return pedidoItemRepository.saveAndFlush(p);
    }

    @Override
    public ResponseEntity update(Long id, PedidoItem p) {
        PedidoItem salva = pedidoItemRepository.findById(id).get();
        if (salva == null) {
            throw new ServiceNotFoundExeception("PedidoItem não encotrado com ID: " + id);
        }
        BeanUtils.copyProperties(p, salva, "id");
        pedidoItemRepository.save(salva);
        return ResponseEntity.ok(salva);
    }

    @Override
    public ResponseEntity delete(Long id) {
        PedidoItem exclui = pedidoItemRepository.findById(id).get();
        if (exclui == null) {
            throw new EmptyResultDataAccessException(1);
        }

        pedidoItemRepository.deleteById(id);
        return ResponseEntity.ok(exclui);
    }

}
