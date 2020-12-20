/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.oferta.api.service;

import com.br.oferta.api.service.serviceImpl.EnderecoServiceImpl;
import com.br.oferta.api.model.Endereco;
import com.br.oferta.api.repository.EnderecoRepository;
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
public class EnderecoService implements EnderecoServiceImpl {

    private final EnderecoRepository enderecoRepository;

    @PersistenceContext
    private final EntityManager em;

    @Autowired
    public EnderecoService(EnderecoRepository enderecoRepository, EntityManager em) {
        this.enderecoRepository = enderecoRepository;
        this.em = em;
    }

    @Override
    public List<Endereco> findBySort() {
        Query query = em.createQuery("SELECT a FROM Endereco a ORDER BY a.id ASC");
        return query.getResultList();
    }

    @Override
    public List<Endereco> findAllByPessoa(Long id) {
        Query query = em.createQuery("SELECT p FROM Endereco p JOIN p.pessoas c WHERE c.id =:id");
        query.setParameter("id", id);
        return query.getResultList();
    }

    @Override
    public Optional<Endereco> findById(Long id) {
        return enderecoRepository.findById(id);
    }

    @Override
    public Endereco create(Endereco e) {
        return enderecoRepository.save(e);
    }

    @Override
    public ResponseEntity update(Long id, Endereco e) {
        Endereco salva = enderecoRepository.findById(id).get();
        if (salva == null) {
            throw new ServiceNotFoundExeception("Categoria não encotrado com ID: " + id);
        }
        BeanUtils.copyProperties(e, salva, "id");
        enderecoRepository.save(salva);
        return ResponseEntity.ok(salva);
    }

    @Override
    public ResponseEntity delete(Long id) {
        Endereco exclui = enderecoRepository.findById(id).get();
        if (exclui == null) {
            throw new EmptyResultDataAccessException(1);
        }
        enderecoRepository.deleteById(id);
        return ResponseEntity.ok(exclui);
    }

}
