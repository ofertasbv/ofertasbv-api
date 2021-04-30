package com.br.oferta.api.repository;

import com.br.oferta.api.model.PedidoItem;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PedidoItemRepository extends JpaRepository<PedidoItem, Long> {
}
