package com.br.oferta.api.repository;

import com.br.oferta.api.model.Cep;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CepRepository extends JpaRepository<Cep, Long> {
}
