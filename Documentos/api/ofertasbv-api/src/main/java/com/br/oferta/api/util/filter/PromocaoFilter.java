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
    private String nomePromocao;
    private Long promocaoTipo;
    private Long loja;
    private LocalDate dataInicio;
    private LocalDate dataFinal;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomePromocao() {
        return nomePromocao;
    }

    public void setNomePromocao(String nomePromocao) {
        this.nomePromocao = nomePromocao;
    }

    public Long getPromocaoTipo() {
        return promocaoTipo;
    }

    public void setPromocaoTipo(Long promocaoTipo) {
        this.promocaoTipo = promocaoTipo;
    }

    public Long getLoja() {
        return loja;
    }

    public void setLoja(Long loja) {
        this.loja = loja;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(LocalDate dataFinal) {
        this.dataFinal = dataFinal;
    }

}
