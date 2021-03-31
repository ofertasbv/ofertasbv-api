/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.oferta.api.service;

import com.br.oferta.api.model.CaixaFluxoEntrada;
import com.br.oferta.api.model.Pedido;
import com.br.oferta.api.model.Produto;
import com.br.oferta.api.repository.CaixaFluxoEntradaRepository;
import com.br.oferta.api.service.serviceImpl.CaixaFluxoEntradaServiceImpl;
import com.br.oferta.api.util.error.ServiceNotFoundExeception;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
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
public class CaixaFluxoEntradaService implements CaixaFluxoEntradaServiceImpl {

    private final CaixaFluxoEntradaRepository caixaFluxoRepository;

    @PersistenceContext
    private final EntityManager em;

    @Autowired
    public CaixaFluxoEntradaService(CaixaFluxoEntradaRepository caixaFluxoRepository, EntityManager em) {
        this.caixaFluxoRepository = caixaFluxoRepository;
        this.em = em;
    }

    @Override
    public List<CaixaFluxoEntrada> findBySort() {
        Query query = em.createQuery("SELECT s FROM CaixaFluxoEntrada s ORDER BY s.id ASC");
        return query.getResultList();
    }

    @Override
    public Optional<CaixaFluxoEntrada> findById(Long id) {
        return caixaFluxoRepository.findById(id);
    }

    @Override
    public BigDecimal valorTotalByDataRegistro(LocalDate dataInicio, LocalDate dataFinal) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<BigDecimal> criteriaQuery = criteriaBuilder.createQuery(BigDecimal.class);

        Root<CaixaFluxoEntrada> root = criteriaQuery.from(CaixaFluxoEntrada.class);
        criteriaQuery.select(criteriaBuilder.sum(root.get("valorEntrada")));
        criteriaQuery.where(criteriaBuilder.between(root.get("dataRegistro"), dataInicio, dataFinal));
        BigDecimal valorTotal = em.createQuery(criteriaQuery).getSingleResult();
        em.close();
        return valorTotal;
    }

    @Override
    public CaixaFluxoEntrada create(CaixaFluxoEntrada c) {
        return caixaFluxoRepository.save(c);
    }

    @Override
    public ResponseEntity update(Long id, CaixaFluxoEntrada c) {
        CaixaFluxoEntrada salva = caixaFluxoRepository.findById(id).get();
        if (salva == null) {
            throw new ServiceNotFoundExeception("caixa fluxo não encotrado com ID: " + id);
        }
        BeanUtils.copyProperties(c, salva, "id");
        caixaFluxoRepository.save(salva);
        return ResponseEntity.ok(salva);
    }

    @Override
    public ResponseEntity delete(Long id) {
        CaixaFluxoEntrada exclui = caixaFluxoRepository.findById(id).get();
        if (exclui == null) {
            throw new EmptyResultDataAccessException(1);
        }

        caixaFluxoRepository.deleteById(id);
        return ResponseEntity.ok(exclui);
    }

}
