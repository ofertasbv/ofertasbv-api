/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.oferta.api.model;

/**
 *
 * @author fabio
 */
public enum EstoqueStatus {

    BAIXO("BAIXO"),
    MEDIO("MEDIO"),
    ALTO("ALTO");

    private String descricao;

    EstoqueStatus(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
