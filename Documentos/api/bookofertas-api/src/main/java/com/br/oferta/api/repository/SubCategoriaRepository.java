package com.br.oferta.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.oferta.api.model.SubCategoria;
import java.util.Optional;

public interface SubCategoriaRepository extends JpaRepository<SubCategoria, Long> {

    public Optional<SubCategoria> findByNomeIgnoreCase(String nome);
}
