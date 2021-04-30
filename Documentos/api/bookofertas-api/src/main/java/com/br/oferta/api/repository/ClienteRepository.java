package com.br.oferta.api.repository;

import com.br.oferta.api.model.Cliente;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ClienteRepository extends JpaRepository<Cliente, Long> {
     public Optional<Cliente> findByCpfIgnoreCase(String cpf);
}
