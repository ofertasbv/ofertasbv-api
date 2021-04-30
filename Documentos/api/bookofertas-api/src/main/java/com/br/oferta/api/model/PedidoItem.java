/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.oferta.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
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
@Table(name = "pedidoitem", schema = "oferta")
@SuppressWarnings({"IdDefinedInHierarchy", "ConsistentAccessType", "PersistenceUnitPresent"})
public class PedidoItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull(message = "O valor unitário é obrigatório")
    @Column(name = "valor_unitario", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorUnitario;

    @NotNull(message = "O valor total é obrigatório")
    @Column(name = "valor_total", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorTotal;

    @NotNull(message = "A qauntidade é obrigatório")
    @Column(name = "quantidade", nullable = false, length = 3)
    private Integer quantidade;

    @JsonIgnoreProperties({"pedidoItems", "loja", "cliente"})
    @ManyToOne()
    @JoinColumn(name = "pedido_id", foreignKey = @ForeignKey(name = "fk_pedidoitem_pedido"), nullable = false)
    private Pedido pedido;

    @JsonIgnoreProperties({"subCategoria", "arquivos", "estoque", "promocao", "loja", "marca", "tamanhos", "cores"})
    @ManyToOne
    @JoinColumn(name = "produto_id", foreignKey = @ForeignKey(name = "fk_pedidoitem_produto"), nullable = false)
    private Produto produto;

    @ManyToOne
    @JoinColumn(name = "tamanho_id", foreignKey = @ForeignKey(name = "fk_pedidoitem_tamanho"), nullable = true)
    private Tamanho tamanho;

    @Transient
    public boolean isProdutoAssociado() {
        return this.getProduto() != null && this.getProduto().getId() != null;
    }

    @Transient
    public boolean isEstoqueSuficiente() {
        return this.getProduto().getId() == null
                || this.getQuantidade() > 0;
    }

    @Transient
    public boolean isEstoqueInsuficiente() {
        return !this.isEstoqueSuficiente();
    }
}
