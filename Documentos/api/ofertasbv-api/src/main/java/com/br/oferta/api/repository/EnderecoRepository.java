package com.br.oferta.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.oferta.api.model.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
}
