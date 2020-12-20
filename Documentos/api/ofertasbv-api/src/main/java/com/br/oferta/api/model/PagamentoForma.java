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
public enum PagamentoForma {

    BOLETO_BANCARIO("BOLETO_BANCARIO"),
    TRANSFERENCIA_BANCARIA("TRANSFERENCIA_BANCARIA"),
    CARTAO_CREDITO("CARTAO_CREDITO");

    private String descricao;

    PagamentoForma(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
