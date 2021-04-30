package com.br.oferta.api.repository;

import com.br.oferta.api.model.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CidadeRepository extends JpaRepository<Cidade, Long> {
}
