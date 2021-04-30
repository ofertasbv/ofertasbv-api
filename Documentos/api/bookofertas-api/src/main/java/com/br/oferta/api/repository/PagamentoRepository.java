package com.br.oferta.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.oferta.api.model.Pagamento;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
}
