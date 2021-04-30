
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.oferta.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
@Table(name = "pedido")
public class Pedido implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "descricao")
    private String descricao;

    @NotNull(message = "Data registro é obrigatório")
    @Column(name = "data_registro", nullable = false)
    private LocalDate dataRegistro;

    @NotNull(message = "Data entrega é obrigatório")
    @Column(name = "data_entrega", nullable = false)
    private LocalDate dataEntrega;

    @NotNull(message = "Valor da entrega é obrigatório")
    @Column(name = "valor_frete", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorFrete;

    @NotNull(message = "Valor do desconto é obrigatório")
    @Column(name = "valor_desconto", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorDesconto;

    @NotNull(message = "Valor inicial é obrigatório")
    @Column(name = "valor_inicial", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorInicial;

    @NotNull(message = "O valor total é obrigatório")
    @Column(name = "valor_total", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorTotal = BigDecimal.ZERO;

    @OneToMany(mappedBy = "pedido", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PedidoItem> pedidoItems = new ArrayList<>();

    @JsonIgnoreProperties({"enderecos", "usuario"})
    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false, foreignKey = @ForeignKey(name = "fk_pedido_cliente"))
    private Cliente cliente;

    @JsonIgnoreProperties({"enderecos", "usuario", "produtos"})
    @ManyToOne
    @JoinColumn(name = "loja_id", nullable = false, foreignKey = @ForeignKey(name = "fk_pedido_loja"))
    private Loja loja;

    @Enumerated(EnumType.STRING)
    @Column(name = "pedido_status", nullable = false)
    private PedidoStatus pedidoStatus = PedidoStatus.CRIADO;

    @JsonIgnoreProperties({"parcelas", "pedido"})
    @OneToMany(mappedBy = "pedido")
    private List<Pagamento> pagamentos;

    public void adicionarItens(List<PedidoItem> itens) {
        this.setPedidoItems(itens);
        this.getPedidoItems().forEach(i -> i.setPedido(this));
    }

    public BigDecimal getValorTotalItens() {
        return getPedidoItems().stream()
                .map(PedidoItem::getValorTotal)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    public void calcularValorTotal() {
        this.setValorTotal(calcularValorTotal(getValorTotalItens(), getValorFrete(), getValorDesconto()));
    }

    public Long getDiasCriacao() {
        LocalDate inicio = getDataRegistro() != null ? getDataRegistro() : LocalDate.now();
        return ChronoUnit.DAYS.between(inicio, LocalDate.now());
    }

    public boolean isSalvarPermitido() {
        return !pedidoStatus.equals(PedidoStatus.CANCELADO);
    }

    public boolean isSalvarProibido() {
        return !isSalvarPermitido();
    }

    private BigDecimal calcularValorTotal(BigDecimal valorTotalItens, BigDecimal valorFrete, BigDecimal valorDesconto) {
        BigDecimal total = valorTotalItens
                .add(Optional.ofNullable(valorFrete).orElse(BigDecimal.ZERO))
                .subtract(Optional.ofNullable(valorDesconto).orElse(BigDecimal.ZERO));
        return total;
    }
}
