/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.oferta.api.service;

import com.br.oferta.api.model.Cartao;
import com.br.oferta.api.repository.CartaoRepository;
import com.br.oferta.api.service.serviceImpl.CartaoServiceImpl;
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
public class CartaoService implements CartaoServiceImpl {

    private final CartaoRepository cartaoRepository;

    @PersistenceContext
    private final EntityManager em;

    @Autowired
    public CartaoService(CartaoRepository cartaoRepository, EntityManager em) {
        this.cartaoRepository = cartaoRepository;
        this.em = em;
    }

    @Override
    public List<Cartao> findBySort() {
        Query query = em.createQuery("SELECT s FROM Cartao s ORDER BY s.id ASC");
        return query.getResultList();
    }

    @Override
    public Optional<Cartao> findById(Long id) {
        return cartaoRepository.findById(id);
    }

    @Override
    public Cartao create(Cartao c) {
        return cartaoRepository.save(c);
    }

    @Override
    public ResponseEntity update(Long id, Cartao c) {
        Cartao salva = cartaoRepository.findById(id).get();
        if (salva == null) {
            throw new ServiceNotFoundExeception("Cartao não encotrado com ID: " + id);
        }
        BeanUtils.copyProperties(c, salva, "id");
        cartaoRepository.save(salva);
        return ResponseEntity.ok(salva);
    }

    @Override
    public ResponseEntity delete(Long id) {
        Cartao exclui = cartaoRepository.findById(id).get();
        if (exclui == null) {
            throw new EmptyResultDataAccessException(1);
        }

        cartaoRepository.deleteById(id);
        return ResponseEntity.ok(exclui);
    }

}
