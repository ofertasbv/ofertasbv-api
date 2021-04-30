package com.br.oferta.api.repository;

import com.br.oferta.api.model.Estoque;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EstoqueRepository extends JpaRepository<Estoque, Long> {
}
