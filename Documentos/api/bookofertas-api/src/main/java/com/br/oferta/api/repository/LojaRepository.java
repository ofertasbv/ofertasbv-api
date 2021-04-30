package com.br.oferta.api.repository;

import com.br.oferta.api.model.Loja;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LojaRepository extends JpaRepository<Loja, Long> {

    public Optional<Loja> findByCnpjIgnoreCase(String cnpj);
}
