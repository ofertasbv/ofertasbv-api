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
