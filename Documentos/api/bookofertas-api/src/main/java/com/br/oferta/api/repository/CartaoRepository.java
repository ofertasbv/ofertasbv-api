package com.br.oferta.api.repository;

import com.br.oferta.api.model.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CartaoRepository extends JpaRepository<Cartao, Long> {
}
