/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.oferta.api.service;

import com.br.oferta.api.service.serviceImpl.ProdutoServiceImpl;
import com.br.oferta.api.model.Produto;
import com.br.oferta.api.repository.ProdutoRepository;
import com.br.oferta.api.util.error.ServiceNotFoundExeception;
import com.br.oferta.api.util.filter.ProdutoFilter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
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
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 *
 * @author fabio
 */
@Service
public class ProdutoService implements ProdutoServiceImpl {

    private final ProdutoRepository produtoRepository;

    @PersistenceContext
    private final EntityManager em;

    @Value("${contato.disco.raiz}")
    private java.nio.file.Path local;

    private static final Logger logger = LoggerFactory.getLogger(ProdutoService.class);

    @Autowired
    public ProdutoService(ProdutoRepository produtoRepository, EntityManager em) {
        this.produtoRepository = produtoRepository;
        this.em = em;
    }

    @Override
    public Produto create(Produto p) {
        return produtoRepository.save(p);
    }

    @Override
    public ResponseEntity update(Long id, Produto p) {
        Produto produtoSalva = produtoRepository.findById(id).get();
        if (produtoSalva == null) {
            throw new ServiceNotFoundExeception("Produto não encotrado com ID: " + id);
        }
        BeanUtils.copyProperties(p, produtoSalva, "id");
        produtoRepository.save(produtoSalva);
        return ResponseEntity.ok(produtoSalva);
    }

    @Override
    public ResponseEntity delete(Long id) {
        Produto produtoExclui = produtoRepository.findById(id).get();
        if (produtoExclui == null) {
            throw new EmptyResultDataAccessException(1);
        }

        produtoRepository.deleteById(id);
        return ResponseEntity.ok(produtoExclui);
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
    public List<Produto> findAll() {
        Query query = em.createQuery("SELECT p FROM Produto p ORDER BY p.destaque ASC");
        return query.getResultList();
    }

    @Override
    public Optional<Produto> findById(Long id) {
        return produtoRepository.findById(id);
    }

    @Override
    public Page<Produto> findAllByPage(Pageable pageable) {
        return produtoRepository.findAll(pageable);
    }

    @Override
    public List<Produto> findByNome(String nome) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Produto> query = criteriaBuilder.createQuery(Produto.class);
        Root<Produto> n = query.from(Produto.class);

        Path<String> nomePath = n.<String>get("nome");
        List<Predicate> predicates = new ArrayList<>();

        if (nome != null) {
            Predicate paramentro = criteriaBuilder.like(criteriaBuilder.lower(nomePath), "%" + nome.toLowerCase() + "%");
            predicates.add(paramentro);
        }

        query.where((Predicate[]) predicates.toArray(new Predicate[0]));
        query.orderBy(criteriaBuilder.desc(n.get("id")));
        TypedQuery<Produto> typedQuery = em.createQuery(query);

        return typedQuery.getResultList();
    }

    @Override
    public List<Produto> findBySubCategoriaById(Long id) {
        Query query = em.createQuery("SELECT p FROM Produto p JOIN p.subCategoria c WHERE c.id =:id");
        query.setParameter("id", id);
        return query.getResultList();
    }

    @Override
    public List<Produto> findByPromocaoById(Long id) {
        Query query = em.createQuery("SELECT p FROM Produto p JOIN p.promocao o WHERE o.id =:id");
        query.setParameter("id", id);
        return query.getResultList();
    }

    @Override
    public List<Produto> findByPessoaById(Long id) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Produto> query = criteriaBuilder.createQuery(Produto.class);
        Root<Produto> n = query.from(Produto.class);
        Path<Long> promocaoCodigoPath = n.join("promocao").<Long>get("id");
        List<Predicate> predicates = new ArrayList<>();
        if (id != null) {
            Predicate paramentro = criteriaBuilder.le(promocaoCodigoPath, id);
            predicates.add(paramentro);
        }
        query.where((Predicate[]) predicates.toArray(new Predicate[0]));
        query.orderBy(criteriaBuilder.desc(n.get("id")));
        TypedQuery<Produto> typedQuery = em.createQuery(query);
        return typedQuery.getResultList();
    }

    @Override
    public Produto findByCodBarra(String codigoBarra) {
        Query query = em.createQuery("SELECT p FROM Produto p WHERE p.codigoBarra =:codigoBarra");
        query.setParameter("codigoBarra", codigoBarra);
        return (Produto) query.getSingleResult();
    }

    @Override
    public List<Produto> findByIsPromocaoBy(Long id) {
        Query query = em.createQuery("SELECT p FROM Produto p, Promocao c WHERE c.id =:id AND c.id NOT MEMBER OF p.promocao");
        query.setParameter("id", id);
        return query.getResultList();
    }

    @Override
    public List<Produto> findByDestaque(boolean destaque) {
        Query query = em.createQuery("SELECT p FROM Produto p WHERE p.destaque =:destaque");
        query.setParameter("destaque", destaque);
        return query.getResultList();
    }

    @Override
    public List<Produto> filtrar(ProdutoFilter filter) {

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Produto> criteria = builder.createQuery(Produto.class);
        Root<Produto> root = criteria.from(Produto.class);

        Predicate[] predicates = criarRestricoes(filter, builder, root);
        criteria.orderBy(builder.asc(root.get("id")));
        criteria.where(predicates);

        TypedQuery<Produto> typedQuery = em.createQuery(criteria);

        return typedQuery.getResultList();
    }

    private Predicate[] criarRestricoes(ProdutoFilter filter, CriteriaBuilder builder, Root<Produto> root) {

        List<Predicate> predicates = new ArrayList<>();
        Path<String> nomeProduto = root.<String>get("nome");
        Path<Long> subCategoriaIdPath = root.join("subCategoria").<Long>get("id");
        Path<Long> marcaIdPath = root.join("marca").<Long>get("id");
        Path<Long> lojaIdPath = root.join("loja").<Long>get("id");
        Path<Long> promocaoIdPath = root.join("promocao").<Long>get("id");
        Path<BigDecimal> valorProduto = root.join("estoque").<BigDecimal>get("valorVenda");
        Path<LocalDate> dataRegistroPath = root.join("estoque").<LocalDate>get("dataRegistro");

        if (filter.getNomeProduto() != null) {
            Predicate paramentro = builder.like(builder.lower(nomeProduto), "%" + filter.getNomeProduto() + "%");
            predicates.add(paramentro);
        }

        if (filter.getSubCategoria() != null) {
            Predicate paramentro = builder.equal(subCategoriaIdPath, filter.getSubCategoria());
            predicates.add(paramentro);
        }

        if (filter.getMarca() != null) {
            Predicate paramentro = builder.equal(marcaIdPath, filter.getMarca());
            predicates.add(paramentro);
        }

        if (filter.getPromocao() != null) {
            Predicate paramentro = builder.equal(promocaoIdPath, filter.getPromocao());
            predicates.add(paramentro);
        }

        if (filter.getLoja() != null) {
            Predicate paramentro = builder.equal(lojaIdPath, filter.getLoja());
            predicates.add(paramentro);
        }

        if (filter.getValorMinimo() != null & filter.getValorMaximo() != null) {
            Predicate paramentro = builder.between(valorProduto, filter.getValorMinimo(), filter.getValorMaximo());
            predicates.add(paramentro);
        }

        if (filter.getDataRegistro() != null) {
            Predicate paramentro = builder.equal(dataRegistroPath, filter.getDataRegistro());
            predicates.add(paramentro);
        }

        return predicates.toArray(new Predicate[predicates.size()]);
    }
}
