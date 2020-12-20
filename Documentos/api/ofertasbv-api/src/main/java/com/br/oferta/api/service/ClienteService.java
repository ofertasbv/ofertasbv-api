package com.br.oferta.api.service;

import com.br.oferta.api.service.serviceImpl.ClienteServiceImpl;
import com.br.oferta.api.model.Permissao;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.br.oferta.api.model.Cliente;
import com.br.oferta.api.repository.PermissaoRepository;
import com.br.oferta.api.util.error.ServiceNotFoundExeception;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.http.ResponseEntity;
import com.br.oferta.api.repository.ClienteRepository;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

@Service
public class ClienteService implements ClienteServiceImpl {

    private final ClienteRepository clienteRepository;

    private final PermissaoRepository permissaoRepository;

    @PersistenceContext
    private final EntityManager em;

    @Value("${contato.disco.raiz}")
    private Path local;

    private static final Logger logger = LoggerFactory.getLogger(ClienteService.class);

    @Autowired
    public ClienteService(ClienteRepository clienteRepository, PermissaoRepository permissaoRepository, EntityManager em) {
        this.clienteRepository = clienteRepository;
        this.permissaoRepository = permissaoRepository;
        this.em = em;
    }

    @Override
    public List<Cliente> findBySort() {
        Query query = em.createQuery("SELECT a FROM Cliente a ORDER BY a.id DESC");
        return query.getResultList();
    }

    @Override
    public Optional<Cliente> findById(Long id) {
        return clienteRepository.findById(id);
    }

    @Override
    public List<Cliente> findByNome(String nome) {
        Query query = em.createQuery("SELECT a FROM Cliente a WHERE a.nome =:nome");
        return query.getResultList();
    }

    @Override
    public List<Cliente> findByNomeContaining(String nome) {
        Query query = em.createQuery("SELECT a FROM Cliente a WHERE a.nome =:nome");
        return query.getResultList();
    }

    @Override
    public Cliente findByTelefone(String telefone) {
        Query query = em.createQuery("SELECT p FROM Cliente p WHERE p.telefone =:telefone");
        query.setParameter("telefone", telefone);
        return (Cliente) query.getSingleResult();
    }

    @Override
    public Cliente create(Cliente p) {
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
        return clienteRepository.saveAndFlush(p);
    }

    @Override
    public ResponseEntity update(Long id, Cliente p) {
        Cliente salva = clienteRepository.findById(id).get();
        if (salva == null) {
            throw new ServiceNotFoundExeception("PessoaFisica não encotrado com ID: " + id);
        }
        BeanUtils.copyProperties(p, salva, "id");
        clienteRepository.save(salva);
        return ResponseEntity.ok(salva);
    }

    @Override
    public ResponseEntity delete(Long id) {
        Cliente exclui = clienteRepository.findById(id).get();
        if (exclui == null) {
            throw new EmptyResultDataAccessException(1);
        }
        clienteRepository.deleteById(id);
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