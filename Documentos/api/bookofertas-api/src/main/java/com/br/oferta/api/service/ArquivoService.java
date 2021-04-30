/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.oferta.api.service;

import com.br.oferta.api.service.serviceImpl.ArquivoServiceImpl;
import com.br.oferta.api.model.Arquivo;
import com.br.oferta.api.repository.ArquivoRepository;
import com.br.oferta.api.util.error.ServiceNotFoundExeception;
import java.io.IOException;
import java.nio.file.Files;
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
public class ArquivoService implements ArquivoServiceImpl {

    private final ArquivoRepository arquivoRepository;

    @PersistenceContext
    private final EntityManager em;

    @Value("${contato.disco.raiz}")
    private Path local;

    private static final Logger logger = LoggerFactory.getLogger(ArquivoService.class);

    @Autowired
    public ArquivoService(ArquivoRepository arquivoRepository, EntityManager em) {
        this.arquivoRepository = arquivoRepository;
        this.em = em;
    }

    @Override
    public List<Arquivo> findBySort() {
        Query query = em.createQuery("SELECT a FROM Arquivo a");
        return query.getResultList();
    }

    @Override
    public Optional<Arquivo> findById(Long id) {
        return arquivoRepository.findById(id);
    }

    @Override
    public Arquivo create(Arquivo a) {
        return arquivoRepository.save(a);
    }

    @Override
    public ResponseEntity update(Long id, Arquivo a) {
        Arquivo salva = arquivoRepository.findById(id).get();
        if (salva == null) {
            throw new ServiceNotFoundExeception("Arquivo não encotrado com ID: " + id);
        }
        BeanUtils.copyProperties(a, salva, "id");
        arquivoRepository.save(salva);
        return ResponseEntity.ok(salva);
    }

    @Override
    public ResponseEntity delete(Long id) {
        Arquivo exclui = arquivoRepository.findById(id).get();
        if (exclui == null) {
            throw new EmptyResultDataAccessException(1);
        }

        arquivoRepository.deleteById(id);
        return ResponseEntity.ok(exclui);
    }

    @Override
    public void excluirFoto(String foto) {
        try {
            Files.deleteIfExists(this.local.resolve(foto));
        } catch (IOException e) {
            logger.warn(String.format("Erro apagando foto '%s'. Mensagem: %s", foto, e.getMessage()));
        }
    }

}
