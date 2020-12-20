/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.oferta.api.util.filter;

import java.time.LocalDate;

/**
 *
 * @author fabio
 */
public class PromocaoFilter {

    private Long id;
    private String nome;
    private String loja;
    private LocalDate inicio;
    private LocalDate encerramento;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLoja() {
        return loja;
    }

    public void setLoja(String loja) {
        this.loja = loja;
    }

    public LocalDate getInicio() {
        return inicio;
    }

    public void setInicio(LocalDate inicio) {
        this.inicio = inicio;
    }

    public LocalDate getEncerramento() {
        return encerramento;
    }

    public void setEncerramento(LocalDate encerramento) {
        this.encerramento = encerramento;
    }
}
