/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.oferta.api.service;

import com.br.oferta.api.service.serviceImpl.CategoriaServiceImpl;
import com.br.oferta.api.model.Categoria;
import com.br.oferta.api.repository.CategoriaRepository;
import com.br.oferta.api.util.error.ServiceNotFoundExeception;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
public class CategoriaService implements CategoriaServiceImpl {

    private final CategoriaRepository categoriaRepository;

    @PersistenceContext
    private final EntityManager em;

    @Value("${contato.disco.raiz}")
    private Path local;

    private static final Logger logger = LoggerFactory.getLogger(CategoriaService.class);

    @Autowired
    public CategoriaService(CategoriaRepository categoriaRepository, EntityManager em) {
        this.categoriaRepository = categoriaRepository;
        this.em = em;
    }

    @Override
    public List<Categoria> findBySort() {
        Query query = em.createQuery("SELECT c FROM Categoria c");
        return query.getResultList();
    }

    @Override
    public Optional<Categoria> findById(Long id) {
        return categoriaRepository.findById(id);
    }

    @Override
    public Categoria findBySubCategoriaId(Long id) {
        Query query = em.createQuery("SELECT p FROM Categoria p JOIN p.subCategorias o WHERE o.id =:id");
        query.setParameter("id", id);
        return (Categoria) query.getSingleResult();
    }

    @Override
    public List<Categoria> findByNome(String nome) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Categoria> query = criteriaBuilder.createQuery(Categoria.class);
        Root<Categoria> n = query.from(Categoria.class);

        javax.persistence.criteria.Path<String> nomePath = n.<String>get("nome");
        List<Predicate> predicates = new ArrayList<>();

        if (nome != null) {
            Predicate paramentro = criteriaBuilder.like(criteriaBuilder.lower(nomePath), "%" + nome.toLowerCase() + "%");
            predicates.add(paramentro);
        }

        query.where((Predicate[]) predicates.toArray(new Predicate[0]));
        query.orderBy(criteriaBuilder.desc(n.get("id")));
        TypedQuery<Categoria> typedQuery = em.createQuery(query);

        return typedQuery.getResultList();
    }

    @Override
    public Categoria create(Categoria c) {
        return categoriaRepository.save(c);
    }

    @Override
    public ResponseEntity update(Long id, Categoria c) {
        Optional<Categoria> existe = categoriaRepository.findById(c.getId());
        if (existe.isPresent()) {
            throw new ServiceNotFoundExeception("Categoria não encontrada: " + c.getId());
        }
        BeanUtils.copyProperties(c, existe, "id");
        categoriaRepository.save(existe.get());
        return ResponseEntity.ok(existe);
    }

    @Override
    public ResponseEntity delete(Long id) {
        Optional<Categoria> existe = categoriaRepository.findById(id);
        if (existe.isPresent()) {
            throw new EmptyResultDataAccessException(1);
        }

        categoriaRepository.deleteById(id);
        return ResponseEntity.ok(existe);
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
