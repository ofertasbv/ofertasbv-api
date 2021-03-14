/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.oferta.api.service;

import com.br.oferta.api.service.serviceImpl.PromocaoServiceImpl;
import com.br.oferta.api.model.Promocao;
import com.br.oferta.api.repository.PromocaoRepository;
import com.br.oferta.api.util.error.ServiceNotFoundExeception;
import com.br.oferta.api.util.filter.PromocaoFilter;
import com.br.oferta.api.util.paginacao.PaginacaoUtil;
import java.io.IOException;
import java.nio.file.Files;
import javax.persistence.criteria.Path;
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
public class PromocaoService implements PromocaoServiceImpl {

    private final PromocaoRepository promocaoRepository;

    private final PaginacaoUtil paginacaoUtil;

    @PersistenceContext
    private final EntityManager em;

    @Value("${contato.disco.raiz}")
    private java.nio.file.Path local;

    private static final Logger logger = LoggerFactory.getLogger(PromocaoService.class);

    @Autowired
    public PromocaoService(PromocaoRepository promocaoRepository, PaginacaoUtil paginacaoUtil, EntityManager em) {
        this.promocaoRepository = promocaoRepository;
        this.paginacaoUtil = paginacaoUtil;
        this.em = em;
    }

    @Override
    public Promocao create(Promocao p) {
        return promocaoRepository.save(p);
    }

    @Override
    public ResponseEntity update(Long id, Promocao p) {
        Promocao salva = promocaoRepository.findById(id).get();
        if (salva == null) {
            throw new ServiceNotFoundExeception("Promoção não encotrado com ID: " + id);
        }
        BeanUtils.copyProperties(p, salva, "id");
        promocaoRepository.save(salva);
        return ResponseEntity.ok(salva);
    }

    @Override
    public ResponseEntity delete(Long id) {
        Promocao exclui = promocaoRepository.findById(id).get();
        if (exclui == null) {
            throw new EmptyResultDataAccessException(1);
        }
        promocaoRepository.deleteById(id);
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

    @Override
    public List<Promocao> findByDia(LocalDate dia) {
        Query query = em.createQuery("SELECT p FROM Promocao p WHERE p.dataRegistro BETWEEN p.dataInicio AND p.dataFinal");
        query.setParameter("dia", dia);
        return query.getResultList();
    }

    @Override
    public List<Promocao> findBySemana(LocalDate semana) {
        Query query = em.createQuery("SELECT p FROM Promocao p WHERE p.dataRegistro BETWEEN p.dataInicio AND p.dataFinal");
        query.setParameter("semana", semana);
        return query.getResultList();
    }

    @Override
    public List<Promocao> findByMes(LocalDate mes) {
        Query query = em.createQuery("SELECT p FROM Promocao p WHERE p.dataRegistro BETWEEN p.dataInicio AND p.dataFinal");
        query.setParameter("mes", mes);
        return query.getResultList();
    }

    @Override
    public List<Promocao> findByPessoa(Long id) {
        Query query = em.createQuery("SELECT p FROM Promocao p JOIN p.loja m WHERE m.id =:id");
        query.setParameter("id", id);
        return query.getResultList();
    }

    @Override
    public Optional<Promocao> findById(Long id) {
        return promocaoRepository.findById(id);
    }

    @Override
    public List<Promocao> findBySort() {
        Query query = em.createQuery("SELECT p FROM Promocao p ORDER BY p.id ASC");
        return query.getResultList();
    }

    @Override
    public List<Promocao> filtrar(PromocaoFilter filtro) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Promocao> criteria = builder.createQuery(Promocao.class);
        Root<Promocao> root = criteria.from(Promocao.class);

        Predicate[] predicates = criarRestricoes(filtro, builder, root);
        criteria.where(predicates);

        TypedQuery<Promocao> typedQuery = em.createQuery(criteria);

        return typedQuery.getResultList();
    }

    private Predicate[] criarRestricoes(PromocaoFilter filter, CriteriaBuilder builder, Root<Promocao> root) {

        List<Predicate> predicates = new ArrayList<>();
        Path<String> nomePath = root.<String>get("nome");
        Path<LocalDate> dataRegistroPath = root.<LocalDate>get("dataRegistro");
        Path<Long> promocaoTipoPath = root.join("promocaoTipo").<Long>get("id");
        Path<Long> lojaIdPath = root.join("loja").<Long>get("id");

        if (filter.getNomePromocao() != null) {
            Predicate paramentro = builder.like(builder.lower(nomePath), "%" + filter.getNomePromocao() + "%");
            predicates.add(paramentro);
        }

        if (filter.getLoja() != null) {
            Predicate paramentro = builder.equal(lojaIdPath, filter.getLoja());
            predicates.add(paramentro);
        }

        if (filter.getPromocaoTipo() != null) {
            Predicate paramentro = builder.equal(promocaoTipoPath, filter.getPromocaoTipo());
            predicates.add(paramentro);
        }

        if (filter.getDataInicio() != null & filter.getDataFinal() != null) {
            Predicate paramentro = builder.between(dataRegistroPath, filter.getDataInicio(), filter.getDataFinal());
            predicates.add(paramentro);
        }

        return predicates.toArray(new Predicate[predicates.size()]);
    }

    @Override
    public List<Promocao> findByNome(String nome) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Promocao> query = criteriaBuilder.createQuery(Promocao.class);
        Root<Promocao> n = query.from(Promocao.class);

        javax.persistence.criteria.Path<String> nomePath = n.<String>get("nome");
        List<Predicate> predicates = new ArrayList<>();

        if (nome != null) {
            Predicate paramentro = criteriaBuilder.like(criteriaBuilder.lower(nomePath), "%" + nome.toLowerCase() + "%");
            predicates.add(paramentro);
        }

        query.where((Predicate[]) predicates.toArray(new Predicate[0]));
        query.orderBy(criteriaBuilder.desc(n.get("id")));
        TypedQuery<Promocao> typedQuery = em.createQuery(query);

        return typedQuery.getResultList();
    }
}
