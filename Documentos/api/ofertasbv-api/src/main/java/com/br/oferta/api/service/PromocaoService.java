/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.oferta.api.service;

import com.br.oferta.api.model.Categoria;
import com.br.oferta.api.service.serviceImpl.PromocaoServiceImpl;
import com.br.oferta.api.model.Promocao;
import com.br.oferta.api.repository.PromocaoRepository;
import com.br.oferta.api.util.error.ServiceNotFoundExeception;
import com.br.oferta.api.util.filter.PromocaoFilter;
import com.br.oferta.api.util.paginacao.PaginacaoUtil;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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
    private Path local;

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

    private Long total(PromocaoFilter filtro) {
        Criteria criteria = em.unwrap(Session.class).createCriteria(Promocao.class);
        adicionarFiltro(filtro, criteria);
        criteria.setProjection(Projections.rowCount());
        return (Long) criteria.uniqueResult();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Promocao> filtrar(PromocaoFilter filtro, Pageable pageable) {
        Criteria criteria = em.unwrap(Session.class).createCriteria(Promocao.class);

        paginacaoUtil.preparar(criteria, pageable);
        adicionarFiltro(filtro, criteria);

        return new PageImpl<>(criteria.list(), pageable, total(filtro));
    }

    private void adicionarFiltro(PromocaoFilter filtro, Criteria criteria) {
        criteria.createAlias("loja", "l");

        if (filtro != null) {

            if (!StringUtils.isEmpty(filtro.getId())) {
                criteria.add(Restrictions.eq("id", filtro.getId()));
            }

            if (!StringUtils.isEmpty(filtro.getNome())) {
                criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
            }

            if (filtro.getLoja() != null) {
                criteria.add(Restrictions.ilike("l.nome", filtro.getLoja(), MatchMode.ANYWHERE));
            }

            if (filtro.getInicio() != null) {
                LocalDateTime desde = LocalDateTime.of(filtro.getInicio(), LocalTime.of(0, 0));
                criteria.add(Restrictions.ge("dataRegistro", desde));
            }

            if (filtro.getEncerramento() != null) {
                LocalDateTime ate = LocalDateTime.of(filtro.getEncerramento(), LocalTime.of(23, 59));
                criteria.add(Restrictions.le("dataRegistro", ate));
            }
        }
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
