/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.oferta.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Locale;
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
@Table(name = "estoque", schema = "oferta")
public class Estoque implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull(message = "A data de registro é obrigatório")
    @Column(name = "data_registro", nullable = false)
    private LocalDate dataRegistro;

    @Column(name = "data_vencimento")
    private LocalDate dataVencimento;

    @NotNull(message = "A quantidade em estoque é obrigatória")
    @Max(value = 9999, message = "A quantidade em estoque deve ser menor que 9.999")
    @Column(name = "quantidade", nullable = false)
    private Integer quantidade;

    @NotNull(message = "Valor unitário é obrigatório")
    @DecimalMin(value = "0.50", message = "O valor unitár do produto deve ser maior que R$0,00")
    @DecimalMax(value = "9999999.99", message = "O valor do produto deve ser menor que R$9.999.999,99")
    @Column(name = "valor_unitario", nullable = false)
    private BigDecimal valorUnitario;

    @NotNull(message = "O percentual de ganho é obrigatório")
    @DecimalMax(value = "100.0", message = "O valor do percentual de ganho deve ser menor que 100")
    @Column(name = "percetual", nullable = false)
    private BigDecimal percentual;

    @NotNull(message = "Valor de venda é obrigatório")
    @DecimalMin(value = "0.50", message = "O valor de venda do produto deve ser maior que R$0,00")
    @DecimalMax(value = "9999999.99", message = "O valor de venda do produto deve ser menor que R$9.999.999,99")
    @Column(name = "valor_venda", nullable = false)
    private BigDecimal valorVenda;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "estoque_status", nullable = false)
    private EstoqueStatus estoqueStatus = EstoqueStatus.BAIXO;

    @JsonIgnore
    @OneToOne(mappedBy = "estoque")
    private Produto produto;

    public BigDecimal calcularValorTotal() {
        BigDecimal valorTotal = valorUnitario.add(percentual.divide(new BigDecimal(100)).multiply(valorUnitario));
        return valorTotal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(LocalDate dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getValorUnitario() {
        Locale locale = new Locale("pt", "Brasil");
        NumberFormat format = NumberFormat.getInstance(locale);
        DecimalFormat resultado = new DecimalFormat();
        return valorUnitario;
    }

    public void setValorUnitario(BigDecimal valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public BigDecimal getPercentual() {
        return percentual;
    }

    public void setPercentual(BigDecimal percentual) {
        this.percentual = percentual;
    }

    public BigDecimal getValorVenda() {
        return valorVenda = calcularValorTotal();
    }

    public void setValorVenda(BigDecimal valorVenda) {
        this.valorVenda = calcularValorTotal();
    }

    public EstoqueStatus getEstoqueStatus() {
        return estoqueStatus;
    }

    public void setEstoqueStatus(EstoqueStatus estoqueStatus) {
        this.estoqueStatus = estoqueStatus;
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
        Estoque other = (Estoque) obj;
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
