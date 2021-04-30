package com.br.oferta.api.repository;

import com.br.oferta.api.model.Seguimento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SeguimentoRepository extends JpaRepository<Seguimento, Long> {

    public Optional<Seguimento> findByNomeIgnoreCase(String nome);
}
