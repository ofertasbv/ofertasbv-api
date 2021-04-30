package com.br.oferta.api.repository;

import com.br.oferta.api.model.Marca;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MarcaRepository extends JpaRepository<Marca, Long> {
}
