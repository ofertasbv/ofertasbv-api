package com.br.oferta.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.oferta.api.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long>{
  
}
