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
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author fabio
 */
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "estoque", schema = "oferta")
public class Estoque implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull(message = "A data de entrada é obrigatório")
    @Column(name = "data_entrada", nullable = true)
    private LocalDate dataEntrada;

    @NotNull(message = "A data de saida é obrigatório")
    @Column(name = "data_saida", nullable = true)
    private LocalDate dataSaida;

    @Column(name = "data_fabricacao")
    private LocalDate dataFabricacao;

    @Column(name = "data_vencimento")
    private LocalDate dataVencimento;

    @NotNull(message = "A quantidade de entrada em estoque é obrigatória")
    @Max(value = 9999, message = "A quantidade de entrada em estoque deve ser menor que 9.999")
    @Column(name = "quantidade_entrada", nullable = false)
    private Integer quantidadeEntrada;

    @NotNull(message = "A quantidade de saida em estoque é obrigatória")
    @Max(value = 9999, message = "A quantidade de saida em estoque deve ser menor que 9.999")
    @Column(name = "quantidade_saida", nullable = false)
    private Integer quantidadeSaida;

    @NotNull(message = "A quantidade total do estoque é obrigatória")
    @Max(value = 9999, message = "A quantidade total do estoque deve ser menor que 9.999")
    @Column(name = "quantidade_total", nullable = false)
    private Integer quantidadeTotal;

    @NotNull(message = "Valor unitário é obrigatório")
    @DecimalMin(value = "0.50", message = "O valor de entrada do estoque deve ser maior que R$0,00")
    @DecimalMax(value = "9999999.99", message = "O valor do entrada do estoque deve ser menor que R$9.999.999,99")
    @Column(name = "valor_entrada", nullable = false)
    private BigDecimal valorEntrada;

    @NotNull(message = "Valor de saida é obrigatório")
    @DecimalMin(value = "0.50", message = "O valor de saida do produto deve ser maior que R$0,00")
    @DecimalMax(value = "9999999.99", message = "O valor de venda do produto deve ser menor que R$9.999.999,99")
    @Column(name = "valor_saida", nullable = false)
    private BigDecimal valorSaida;

    @NotNull(message = "O percentual de ganho é obrigatório")
    @DecimalMax(value = "100.0", message = "O valor do percentual de ganho deve ser menor que 100")
    @Column(name = "percetual", nullable = false)
    private BigDecimal percentual;

    @Enumerated(EnumType.STRING)
    @Column(name = "estoque_status", nullable = true)
    private EstoqueStatus estoqueStatus = EstoqueStatus.BAIXO;

    @JsonIgnore
    @OneToOne(mappedBy = "estoque")
    private Produto produto;

    public BigDecimal calcularValorTotal() {
        BigDecimal valorTotal = getValorEntrada().add(getPercentual().divide(new BigDecimal(100)).multiply(getValorEntrada()));
        return valorTotal;
    }

    public BigDecimal calcularValorTotalPromocao() {
        BigDecimal valorTotal = getValorSaida().subtract(getProduto().getPromocao().getDesconto().divide(new BigDecimal(100)).multiply(getValorSaida()));
        return valorTotal;
    }

    public BigDecimal getValorUnitario() {
        Locale locale = new Locale("pt", "Brasil");
        NumberFormat format = NumberFormat.getInstance(locale);
        DecimalFormat resultado = new DecimalFormat();
        return getValorEntrada();
    }
}
