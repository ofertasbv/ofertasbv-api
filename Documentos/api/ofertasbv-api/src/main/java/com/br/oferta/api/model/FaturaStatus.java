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
public enum FaturaStatus {

    PAGA("PAGA"),
    ATRASADA("ATRASADA"),
    CANCELADA("CANCELA");

    private String descricao;

    FaturaStatus(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
