/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.oferta.api.util.filter;

import com.br.oferta.api.model.PedidoStatus;
import java.time.LocalDate;

/**
 *
 * @author fabio
 */
public class PedidoFilter {

    private Long id;
    private String descricao;
    private Long cliente;
    private Long loja;
    private LocalDate dataRegistro;
    private LocalDate dataEntrega;
    private Boolean status;
    private PedidoStatus pedidoStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getCliente() {
        return cliente;
    }

    public void setCliente(Long cliente) {
        this.cliente = cliente;
    }

    public Long getLoja() {
        return loja;
    }

    public void setLoja(Long loja) {
        this.loja = loja;
    }

    public LocalDate getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(LocalDate dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    public LocalDate getDataEntrega() {
        return dataEntrega;
    }

    public void setDataEntrega(LocalDate dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public PedidoStatus getPedidoStatus() {
        return pedidoStatus;
    }

    public void setPedidoStatus(PedidoStatus pedidoStatus) {
        this.pedidoStatus = pedidoStatus;
    }

}
