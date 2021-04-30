package com.br.oferta.api.repository;

import com.br.oferta.api.model.Arquivo;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ArquivoRepository extends JpaRepository<Arquivo, Long> {
}
