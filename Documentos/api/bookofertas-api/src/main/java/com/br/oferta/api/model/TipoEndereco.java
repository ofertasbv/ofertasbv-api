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
public enum TipoEndereco {
    RESIDENCIAL("RESIDENCIAL"),
    COMERCIAL("COMERCIAL"),
    ENTREGA("ENTREGA");

    private final String descricao;

    TipoEndereco(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
