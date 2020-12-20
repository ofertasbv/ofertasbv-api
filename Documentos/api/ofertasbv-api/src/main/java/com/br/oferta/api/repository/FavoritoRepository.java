package com.br.oferta.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.oferta.api.model.Favorito;

public interface FavoritoRepository extends JpaRepository<Favorito, Long> {
}
