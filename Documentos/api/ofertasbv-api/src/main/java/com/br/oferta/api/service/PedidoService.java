/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.oferta.api.service;

import com.br.oferta.api.service.serviceImpl.PedidoServiceImpl;
import com.br.oferta.api.model.Pedido;
import com.br.oferta.api.repository.PedidoRepository;
import com.br.oferta.api.util.error.ServiceNotFoundExeception;
import com.br.oferta.api.util.filter.PedidoFilter;
import java.math.BigDecimal;
import java.time.LocalDate;
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
public class PedidoService implements PedidoServiceImpl {
    
    private final PedidoRepository pedidoRepository;
    
    @PersistenceContext
    private final EntityManager em;
    
    @Autowired
    public PedidoService(PedidoRepository pedidoRepository, EntityManager em) {
        this.pedidoRepository = pedidoRepository;
        this.em = em;
    }
    
    @Override
    public List<Pedido> findBySort() {
        Query query = em.createQuery("SELECT a FROM Pedido a ORDER BY a.id DESC");
        return query.getResultList();
    }
    
    @Override
    public Optional<Pedido> findById(Long id) {
        return pedidoRepository.findById(id);
    }
    
    @Override
    public BigDecimal valorTotalByDataRegistro(LocalDate dataInicio, LocalDate dataFinal) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<BigDecimal> criteriaQuery = criteriaBuilder.createQuery(BigDecimal.class);
        
        Root<Pedido> root = criteriaQuery.from(Pedido.class);
        criteriaQuery.select(criteriaBuilder.sum(root.get("valorTotal")));
        criteriaQuery.where(criteriaBuilder.between(root.get("dataRegistro"), dataInicio, dataFinal));
        BigDecimal valorTotal = em.createQuery(criteriaQuery).getSingleResult();
        em.close();
        return valorTotal;
    }
    
    @Override
    public List<Pedido> filtrar(PedidoFilter filter) {
        
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteria = builder.createQuery(Pedido.class);
        Root<Pedido> root = criteria.from(Pedido.class);
        
        Predicate[] predicates = criarRestricoes(filter, builder, root);
        criteria.orderBy(builder.asc(root.get("id")));
        criteria.where(predicates);
        
        TypedQuery<Pedido> typedQuery = em.createQuery(criteria);
        
        return typedQuery.getResultList();
    }
    
    private Predicate[] criarRestricoes(PedidoFilter filter, CriteriaBuilder builder, Root<Pedido> root) {
        
        List<Predicate> predicates = new ArrayList<>();
        Path<String> descricaoPath = root.<String>get("descricao");
        Path<Long> clienteIdPath = root.join("cliente").<Long>get("id");
        Path<Long> lojaIdPath = root.join("loja").<Long>get("id");
        Path<LocalDate> dataRegistroPath = root.<LocalDate>get("dataRegistro");
        Path<LocalDate> dataEntregaPath = root.<LocalDate>get("dataEntrega");
        
        if (filter.getDescricao() != null) {
            Predicate paramentro = builder.like(builder.lower(descricaoPath), "%" + filter.getDescricao() + "%");
            predicates.add(paramentro);
        }
        
        if (filter.getCliente() != null) {
            Predicate paramentro = builder.equal(clienteIdPath, filter.getCliente());
            predicates.add(paramentro);
        }
        
        if (filter.getLoja() != null) {
            Predicate paramentro = builder.equal(lojaIdPath, filter.getLoja());
            predicates.add(paramentro);
        }
        
        if (filter.getDataRegistro() != null) {
            Predicate paramentro = builder.equal(dataRegistroPath, filter.getDataRegistro());
            predicates.add(paramentro);
        }
        
        if (filter.getDataEntrega() != null) {
            Predicate paramentro = builder.equal(dataEntregaPath, filter.getDataEntrega());
            predicates.add(paramentro);
        }
        
        return predicates.toArray(new Predicate[predicates.size()]);
    }
    
    @Override
    public Pedido create(Pedido p) {
        p.adicionarItens(p.getPedidoItems());
        return pedidoRepository.saveAndFlush(p);
    }
    
    @Override
    public ResponseEntity update(Long id, Pedido p) {
        Pedido salva = pedidoRepository.findById(id).get();
        if (salva == null) {
            throw new ServiceNotFoundExeception("Pedido não encotrado com ID: " + id);
        }
        BeanUtils.copyProperties(p, salva, "id");
        pedidoRepository.save(salva);
        return ResponseEntity.ok(salva);
    }
    
    @Override
    public ResponseEntity delete(Long id) {
        Pedido exclui = pedidoRepository.findById(id).get();
        if (exclui == null) {
            throw new EmptyResultDataAccessException(1);
        }
        
        pedidoRepository.deleteById(id);
        return ResponseEntity.ok(exclui);
    }
    
}
