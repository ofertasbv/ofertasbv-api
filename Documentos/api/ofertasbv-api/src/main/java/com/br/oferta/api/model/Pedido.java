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
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

/**
 *
 * @author fabio
 */
@Entity
@Table(name = "pedido")
@SuppressWarnings({"ConsistentAccessType", "IdDefinedInHierarchy"})
public class Pedido implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "data_criacao")
    private LocalDateTime dataRegistro;

    @Column(name = "data_hora_entrega")
    private LocalDateTime dataHoraEntrega;

    @Column(name = "valor_frete")
    private BigDecimal valorFrete;

    @Column(name = "valor_desconto")
    private BigDecimal valorDesconto;

    @NotNull(message = "O valor total é obrigatório")
    @Column(name = "valor_total", nullable = false, precision = 10, scale = 2)
    private final BigDecimal valorTotal = BigDecimal.ZERO;

//    @JsonIgnoreProperties({"produto"})
    @OneToMany(mappedBy = "pedido", fetch = FetchType.LAZY)
    private List<PedidoItem> pedidoItems = new ArrayList<>();

    @JsonIgnoreProperties({"enderecos", "usuario"})
    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false, foreignKey = @ForeignKey(name = "fk_pedido_cliente"))
    private Cliente cliente;

    @JsonIgnoreProperties({"enderecos", "usuario"})
    @ManyToOne
    @JoinColumn(name = "loja_id", nullable = false, foreignKey = @ForeignKey(name = "fk_pedido_loja"))
    private Loja loja;

    @ManyToOne
    @JoinColumn(name = "pagamento_id", nullable = false, foreignKey = @ForeignKey(name = "fk_pedido_pagamento"))
    private Pagamento pagamento = new Pagamento();

    @Enumerated(EnumType.STRING)
    @Column(name = "pedido_status", nullable = false)
    private PedidoStatus pedidoStatus = PedidoStatus.CANCELADA;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "pagamento_forma", nullable = false)
    private PagamentoForma pagamentoForma = PagamentoForma.BOLETO_BANCARIO;

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
        LocalDate inicio = getDataRegistro() != null ? getDataRegistro().toLocalDate() : LocalDate.now();
        return ChronoUnit.DAYS.between(inicio, LocalDate.now());
    }

    public boolean isSalvarPermitido() {
        return !pedidoStatus.equals(PedidoStatus.CANCELADA);
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

    public LocalDateTime getDataHoraEntrega() {
        return dataHoraEntrega;
    }

    public void setDataHoraEntrega(LocalDateTime dataHoraEntrega) {
        this.dataHoraEntrega = dataHoraEntrega;
    }

    public BigDecimal getValorFrete() {
        return valorFrete;
    }

    public void setValorFrete(BigDecimal valorFrete) {
        this.valorFrete = valorFrete;
    }

    public BigDecimal getValorDesconto() {
        return valorDesconto;
    }

    public void setValorDesconto(BigDecimal valorDesconto) {
        this.valorDesconto = valorDesconto;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.setValorTotal(valorTotal);
    }

    public List<PedidoItem> getPedidoItems() {
        return pedidoItems;
    }

    public void setPedidoItems(List<PedidoItem> pedidoItems) {
        this.pedidoItems = pedidoItems;
    }

    public PedidoStatus getPedidoStatus() {
        return pedidoStatus;
    }

    public void setStatus(PedidoStatus statusPedido) {
        this.setPedidoStatus(statusPedido);
    }

    public PagamentoForma getPagamentoForma() {
        return pagamentoForma;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Loja getLoja() {
        return loja;
    }

    public void setLoja(Loja loja) {
        this.loja = loja;
    }

    public LocalDateTime getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(LocalDateTime dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }

    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }

    public void setPedidoStatus(PedidoStatus pedidoStatus) {
        this.pedidoStatus = pedidoStatus;
    }

}
