package com.br.oferta.api.repository;

import com.br.oferta.api.model.Parcela;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FaturaRepository extends JpaRepository<Parcela, Long> {
}
