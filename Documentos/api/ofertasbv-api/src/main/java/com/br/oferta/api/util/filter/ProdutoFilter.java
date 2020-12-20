/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.oferta.api.util.filter;

import java.math.BigDecimal;

/**
 *
 * @author PMBV-163979
 */
public class ProdutoFilter {

    private Long id;
    private String nomeProduto;
    private Long subCategoria;
    private Long marca;
    private Long origem;
    private Long promocao;
    private Long loja;
    private Long cidade;
    private BigDecimal valorMinimo;
    private BigDecimal valorMaximo;
 

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public Long getSubCategoria() {
        return subCategoria;
    }

    public void setSubCategoria(Long subCategoria) {
        this.subCategoria = subCategoria;
    }

    public Long getMarca() {
        return marca;
    }

    public void setMarca(Long marca) {
        this.marca = marca;
    }


    public Long getOrigem() {
        return origem;
    }

    public void setOrigem(Long origem) {
        this.origem = origem;
    }

    public Long getPromocao() {
        return promocao;
    }

    public void setPromocao(Long promocao) {
        this.promocao = promocao;
    }

    public Long getLoja() {
        return loja;
    }

    public void setLoja(Long loja) {
        this.loja = loja;
    }

    public Long getCidade() {
        return cidade;
    }

    public void setCidade(Long cidade) {
        this.cidade = cidade;
    }

    public BigDecimal getValorMinimo() {
        return valorMinimo;
    }

    public void setValorMinimo(BigDecimal valorMinimo) {
        this.valorMinimo = valorMinimo;
    }

    public BigDecimal getValorMaximo() {
        return valorMaximo;
    }

    public void setValorMaximo(BigDecimal valorMaximo) {
        this.valorMaximo = valorMaximo;
    }

    
}
