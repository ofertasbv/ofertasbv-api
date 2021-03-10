package com.br.oferta.api.service;

import com.br.oferta.api.model.Permissao;
import com.br.oferta.api.model.Promocao;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.br.oferta.api.model.Vendedor;
import com.br.oferta.api.repository.PermissaoRepository;
import com.br.oferta.api.util.error.ServiceNotFoundExeception;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.http.ResponseEntity;
import com.br.oferta.api.repository.VendedorRepository;
import com.br.oferta.api.service.serviceImpl.VendedorServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

@Service
public class VendedorService implements VendedorServiceImpl {

    private final VendedorRepository vendedoRepository;

    private final PermissaoRepository permissaoRepository;

    @PersistenceContext
    private final EntityManager em;

    @Value("${contato.disco.raiz}")
    private Path local;

    private static final Logger logger = LoggerFactory.getLogger(VendedorService.class);

    @Autowired
    public VendedorService(VendedorRepository vendedorRepository, PermissaoRepository permissaoRepository, EntityManager em) {
        this.vendedoRepository = vendedorRepository;
        this.permissaoRepository = permissaoRepository;
        this.em = em;
    }

    @Override
    public List<Vendedor> findBySort() {
        Query query = em.createQuery("SELECT a FROM Vendedor a ORDER BY a.id DESC");
        return query.getResultList();
    }

    @Override
    public Optional<Vendedor> findById(Long id) {
        return vendedoRepository.findById(id);
    }

    @Override
    public List<Vendedor> findByNome(String nome) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Vendedor> query = criteriaBuilder.createQuery(Vendedor.class);
        Root<Vendedor> n = query.from(Vendedor.class);

        javax.persistence.criteria.Path<String> nomePath = n.<String>get("nome");
        List<Predicate> predicates = new ArrayList<>();

        if (nome != null) {
            Predicate paramentro = criteriaBuilder.like(criteriaBuilder.lower(nomePath), "%" + nome.toLowerCase() + "%");
            predicates.add(paramentro);
        }

        query.where((Predicate[]) predicates.toArray(new Predicate[0]));
        query.orderBy(criteriaBuilder.desc(n.get("id")));
        TypedQuery<Vendedor> typedQuery = em.createQuery(query);

        return typedQuery.getResultList();
    }

    @Override
    public List<Vendedor> findByNomeContaining(String nome) {
        Query query = em.createQuery("SELECT a FROM Cliente a WHERE a.nome =:nome");
        return query.getResultList();
    }

    @Override
    public Vendedor findByTelefone(String telefone) {
        Query query = em.createQuery("SELECT p FROM Vendedor p WHERE p.telefone =:telefone");
        query.setParameter("telefone", telefone);
        return (Vendedor) query.getSingleResult();
    }

    @Override
    public Vendedor create(Vendedor p) {
        Permissao id = permissaoRepository.getOne(1L);
        p.getUsuario().getPermissoes().clear();
        p.getUsuario().getPermissoes().add(0, id);
//        p.getUsuario().setSenha(MyPasswordEncoder.getPasswordEncoder(p.getUsuario().getSenha()));

        System.out.println("Permissão:" + id);
        System.out.println("Email: " + p.getUsuario().getEmail());
        System.out.println("Senha: " + p.getUsuario().getSenha());

//        Usuario usuarioExistente = usuarioService.findByEmail(p.getUsuario().getEmail());
//
//        if (usuarioExistente != null && !usuarioExistente.equals(p.getUsuario())) {
//            throw new NegocioException("Já existe um cliente cadastrado com este e-mail.");
//        }
        return vendedoRepository.saveAndFlush(p);
    }

    @Override
    public ResponseEntity update(Long id, Vendedor p) {
        Vendedor salva = vendedoRepository.findById(id).get();
        if (salva == null) {
            throw new ServiceNotFoundExeception("PessoaFisica não encotrado com ID: " + id);
        }
        BeanUtils.copyProperties(p, salva, "id");
        vendedoRepository.save(salva);
        return ResponseEntity.ok(salva);
    }

    @Override
    public ResponseEntity delete(Long id) {
        Vendedor exclui = vendedoRepository.findById(id).get();
        if (exclui == null) {
            throw new EmptyResultDataAccessException(1);
        }
        vendedoRepository.deleteById(id);
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
