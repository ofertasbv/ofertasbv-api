package com.br.oferta.api.repository;

import com.br.oferta.api.model.Permissao;

import org.springframework.data.jpa.repository.JpaRepository;


public interface PermissaoRepository extends JpaRepository<Permissao, Long> {
}
