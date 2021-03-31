package com.br.oferta.api.repository;

import com.br.oferta.api.model.Vendedor;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendedorRepository extends JpaRepository<Vendedor, Long> {

    public Optional<Vendedor> findByCPFIgnoreCase(String cpf);
}
