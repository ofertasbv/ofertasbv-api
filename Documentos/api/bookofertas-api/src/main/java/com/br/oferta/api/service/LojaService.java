package com.br.oferta.api.service;

import com.br.oferta.api.service.serviceImpl.LojaServiceImpl;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.br.oferta.api.model.Loja;
import com.br.oferta.api.model.Usuario;
import com.br.oferta.api.util.error.ServiceNotFoundExeception;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.http.ResponseEntity;
import com.br.oferta.api.repository.LojaRepository;
import com.br.oferta.api.service.exception.NegocioException;
import com.br.oferta.api.util.geradorsenha.MyPasswordEncoder;
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
public class LojaService implements LojaServiceImpl {

    @Autowired
    private LojaRepository lojaRepository;

    @Autowired
    private UsuarioService usuarioService;

    @PersistenceContext
    private EntityManager em;

    @Value("${contato.disco.raiz}")
    private Path local;

    private static final Logger logger = LoggerFactory.getLogger(LojaService.class);

    @Override
    public List<Loja> findBySort() {
        Query query = em.createQuery("SELECT a FROM Loja a");
        return query.getResultList();
    }

    @Override
    public Optional<Loja> findById(Long id) {
        return lojaRepository.findById(id);
    }

    @Override
    public List<Loja> findByNome(String nome) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Loja> query = criteriaBuilder.createQuery(Loja.class);
        Root<Loja> n = query.from(Loja.class);

        javax.persistence.criteria.Path<String> nomePath = n.<String>get("nome");
        List<Predicate> predicates = new ArrayList<>();

        if (nome != null) {
            Predicate paramentro = criteriaBuilder.like(criteriaBuilder.lower(nomePath), "%" + nome.toLowerCase() + "%");
            predicates.add(paramentro);
        }

        query.where((Predicate[]) predicates.toArray(new Predicate[0]));
        query.orderBy(criteriaBuilder.desc(n.get("id")));
        TypedQuery<Loja> typedQuery = em.createQuery(query);

        return typedQuery.getResultList();
    }

    @Override
    public Loja create(Loja p) {
        p.getUsuario().setSenha(MyPasswordEncoder.getPasswordEncoder(p.getUsuario().getSenha()));

        System.out.println("Email: " + p.getUsuario().getEmail());
        System.out.println("Senha: " + p.getUsuario().getSenha());

//        Optional<Usuario> usuarioExistente = usuarioService.findByEmail(p.getUsuario().getEmail());
//
//        if (usuarioExistente != null && !usuarioExistente.equals(p.getUsuario())) {
//            throw new NegocioException("Já existe um cliente cadastrado com este e-mail.");
//        }
        return lojaRepository.save(p);

    }

    @Override
    public ResponseEntity update(Long id, Loja p) {
        Loja salva = lojaRepository.findById(id).get();
        if (salva == null) {
            throw new ServiceNotFoundExeception("Pessoa não encotrado com ID: " + id);
        }
        BeanUtils.copyProperties(p, salva, "id");
        lojaRepository.save(salva);
        return ResponseEntity.ok(salva);
    }

    @Override
    public ResponseEntity delete(Long id) {
        Loja exclui = lojaRepository.findById(id).get();
        if (exclui == null) {
            throw new EmptyResultDataAccessException(1);
        }
        lojaRepository.deleteById(id);
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
