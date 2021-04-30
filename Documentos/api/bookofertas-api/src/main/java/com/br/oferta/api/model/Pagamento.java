/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.oferta.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
    @Column(name = "pagamento_tipo", nullable = false)
    private PagamentoTipo pagamentoTipo = PagamentoTipo.PRAZO;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "pagamento_forma", nullable = false)
    private PagamentoForma pagamentoForma = PagamentoForma.DINHEIRO;

    @JsonIgnoreProperties({"pagamento"})
    @OneToMany(mappedBy = "pagamento", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Parcela> parcelas;

    @JsonIgnoreProperties({"pagamentos", "cliente", "loja"})
    @ManyToOne
    @JoinColumn(name = "pedido_id", nullable = false, foreignKey = @ForeignKey(name = "fk_pagamento_pedido"))
    private Pedido pedido;
}
