/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.oferta.api.service;

import com.br.oferta.api.model.Pagamento;
import com.br.oferta.api.repository.PagamentoRepository;
import com.br.oferta.api.service.serviceImpl.PagamentoServiceImpl;
import com.br.oferta.api.util.error.ServiceNotFoundExeception;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 *
 * @author fabio
 */
@Service
public class PagamentoService implements PagamentoServiceImpl {

    private final PagamentoRepository pagamentoRepository;

    @PersistenceContext
    private final EntityManager em;

    @Value("${contato.disco.raiz}")
    private Path local;

    private static final Logger logger = LoggerFactory.getLogger(PagamentoService.class);

    @Autowired
    public PagamentoService(PagamentoRepository pagamentoRepository, EntityManager em) {
        this.pagamentoRepository = pagamentoRepository;
        this.em = em;
    }

    @Override
    public List<Pagamento> findBySort() {
        Query query = em.createQuery("SELECT a FROM Pagamento a");
        return query.getResultList();
    }

    @Override
    public Optional<Pagamento> findById(Long id) {
        return pagamentoRepository.findById(id);
    }

    @Override
    public Pagamento create(Pagamento a) {
        return pagamentoRepository.save(a);
    }

    @Override
    public ResponseEntity update(Long id, Pagamento a) {
        Pagamento salva = pagamentoRepository.findById(id).get();
        if (salva == null) {
            throw new ServiceNotFoundExeception("Arquivo não encotrado com ID: " + id);
        }
        BeanUtils.copyProperties(a, salva, "id");
        pagamentoRepository.save(salva);
        return ResponseEntity.ok(salva);
    }

    @Override
    public ResponseEntity delete(Long id) {
        Pagamento exclui = pagamentoRepository.findById(id).get();
        if (exclui == null) {
            throw new EmptyResultDataAccessException(1);
        }

        pagamentoRepository.deleteById(id);
        return ResponseEntity.ok(exclui);
    }
}
