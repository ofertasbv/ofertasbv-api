package com.br.oferta.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.oferta.api.model.Contato;

public interface ContatoRepository extends JpaRepository<Contato, Long> {
}
