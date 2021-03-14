/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.oferta.api.service.serviceImpl;

import com.br.oferta.api.model.Promocao;
import com.br.oferta.api.util.filter.PromocaoFilter;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author fabio
 */
public interface PromocaoServiceImpl {

    List<Promocao> findBySort();

    List<Promocao> filtrar(PromocaoFilter filtro);

    Optional<Promocao> findById(Long id);

    List<Promocao> findByNome(String nome);

    Promocao create(Promocao c);

    ResponseEntity update(Long id, Promocao c);

    ResponseEntity delete(Long id);

    List<Promocao> findByDia(LocalDate dia);

    List<Promocao> findBySemana(LocalDate semana);

    List<Promocao> findByMes(LocalDate mes);

    List<Promocao> findByPessoa(Long id);

    void excluirFoto(String foto);
}
