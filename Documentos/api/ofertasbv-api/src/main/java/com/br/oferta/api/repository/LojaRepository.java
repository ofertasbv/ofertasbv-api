package com.br.oferta.api.repository;

import com.br.oferta.api.model.Loja;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LojaRepository extends JpaRepository<Loja, Long> {
}
