package com.br.oferta.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.oferta.api.model.Produto;
import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    public Optional<Produto> findByCodigoBarraIgnoreCase(String codigoBarra);

}
