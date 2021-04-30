/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.oferta.api.service;

import com.br.oferta.api.service.serviceImpl.UsuarioServiceImpl;
import com.br.oferta.api.model.Usuario;
import com.br.oferta.api.repository.UsuarioRepository;
import com.br.oferta.api.util.error.ServiceNotFoundExeception;
import com.br.oferta.api.util.geradorsenha.MyPasswordEncoder;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
public class UsuarioService implements UsuarioServiceImpl {

    private final UsuarioRepository usuarioRepository;

    @PersistenceContext
    private final EntityManager em;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, EntityManager em) {
        this.usuarioRepository = usuarioRepository;
        this.em = em;
    }

    @Override
    public List<Usuario> findBySort() {
        Query query = em.createQuery("SELECT a FROM Usuario a ORDER BY a.id ASC");
        return query.getResultList();
    }

    @Override
    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }

    @Override
    @SuppressWarnings("JPQLValidation")
    public Optional<Usuario> findByEmail(String email) {
        return em.createQuery("from Usuario where lower(email) = lower(:email)", Usuario.class)
                .setParameter("email", email).getResultList().stream().findFirst();
    }

    @Override
    public List<String> permissoes(Usuario usuario) {
        return em.createQuery("select distinct p.descricao from Usuario u inner join u.grupos g inner join g.permissoes p where u = :usuario", String.class).setParameter("usuario", usuario).getResultList();
    }

    @Override
    public Usuario findByLogin(String email, String senha) {
        Query query = em.createQuery("SELECT p FROM Usuario p WHERE p.email =:email AND p.senha =:senha");
        query.setParameter("email", email);
        query.setParameter("senha", senha);
        return (Usuario) query.getSingleResult();
    }

    @Override
    public Usuario create(Usuario u) {
        u.setSenha(MyPasswordEncoder.getPasswordEncoder(u.getSenha()));

        System.out.println("Email: " + u.getEmail());
        System.out.println("Senha: " + u.getSenha());

        return usuarioRepository.save(u);
    }

    @Override
    public ResponseEntity update(Long id, Usuario u) {
        Usuario salva = usuarioRepository.findById(id).get();

        u.setSenha(MyPasswordEncoder.getPasswordEncoder(u.getSenha()));

        System.out.println("Email: " + u.getEmail());
        System.out.println("Senha: " + u.getSenha());

        if (salva == null) {
            throw new ServiceNotFoundExeception("Arquivo não encotrado com ID: " + id);
        }
        BeanUtils.copyProperties(u, salva, "id");
        usuarioRepository.save(salva);
        return ResponseEntity.ok(salva);
    }

    @Override
    public ResponseEntity delete(Long id) {
        Usuario exclui = usuarioRepository.findById(id).get();
        if (exclui == null) {
            throw new EmptyResultDataAccessException(1);
        }
        usuarioRepository.deleteById(id);
        return ResponseEntity.ok(exclui);
    }
}
