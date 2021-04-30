package com.br.oferta.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.oferta.api.model.Promocao;

public interface PromocaoRepository extends JpaRepository<Promocao, Long> {
}
