package com.br.oferta.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.oferta.api.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
