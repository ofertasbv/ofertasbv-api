/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.oferta.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

/**
 *
 * @author fabio
 */
@Entity
@Table(name = "pagamento", schema = "oferta")
public class Pagamento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull(message = "A quantidade em estoque é obrigatória")
    @Max(value = 9999, message = "A quantidade em estoque deve ser menor que 9.999")
    @Column(name = "quantidade", nullable = false)
    private Integer quantidade;

    @NotNull(message = "Valor é obrigatório")
    @DecimalMin(value = "0.50", message = "O valor do produto deve ser maior que R$0,00")
    @DecimalMax(value = "9999999.99", message = "O valor do produto deve ser menor que R$9.999.999,99")
    private BigDecimal valor;

    @Column(name = "data_pagamento")
    private LocalDateTime dataPagamento;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "pagamento_forma", nullable = false)
    private PagamentoForma pagamentoForma = PagamentoForma.BOLETO_BANCARIO;

    @JsonIgnore
    @OneToOne(mappedBy = "estoque")
    private Produto produto;

     public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDateTime getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDateTime dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public PagamentoForma getPagamentoForma() {
        return pagamentoForma;
    }

    public void setPagamentoForma(PagamentoForma pagamentoForma) {
        this.pagamentoForma = pagamentoForma;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Pagamento other = (Pagamento) obj;
        if (getId() == null) {
            if (other.getId() != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }

   
}