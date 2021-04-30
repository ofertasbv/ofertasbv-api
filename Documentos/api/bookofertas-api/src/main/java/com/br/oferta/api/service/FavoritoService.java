/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.oferta.api.service;

import com.br.oferta.api.model.Favorito;
import com.br.oferta.api.repository.FavoritoRepository;
import com.br.oferta.api.service.serviceImpl.FavoritoServiceImpl;
import com.br.oferta.api.util.error.ServiceNotFoundExeception;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class FavoritoService implements FavoritoServiceImpl {

    private final FavoritoRepository favoritoRepository;

    @PersistenceContext
    private final EntityManager em;

    private static final Logger logger = LoggerFactory.getLogger(FavoritoService.class);

    @Autowired
    public FavoritoService(FavoritoRepository favoritoRepository, EntityManager em) {
        this.favoritoRepository = favoritoRepository;
        this.em = em;
    }

    @Override
    public Optional<Favorito> findById(Long id) {
        return favoritoRepository.findById(id);
    }

    @Override
    public List<Favorito> findBySort() {
        Query query = em.createQuery("SELECT c FROM Favorito c");
        return query.getResultList();
    }

    @Override
    public Favorito create(Favorito c) {
        return favoritoRepository.save(c);
    }

    @Override
    public ResponseEntity update(Long id, Favorito c) {
        Optional<Favorito> existe = favoritoRepository.findById(c.getId());
        if (existe.isPresent()) {
            throw new ServiceNotFoundExeception("Categoria não encontrada: " + c.getId());
        }
        BeanUtils.copyProperties(c, existe, "id");
        favoritoRepository.save(existe.get());
        return ResponseEntity.ok(existe);
    }

    @Override
    public ResponseEntity delete(Long id) {
        Optional<Favorito> existe = favoritoRepository.findById(id);
        if (existe.isPresent()) {
            throw new EmptyResultDataAccessException(1);
        }

        favoritoRepository.deleteById(id);
        return ResponseEntity.ok(existe);
    }

}
