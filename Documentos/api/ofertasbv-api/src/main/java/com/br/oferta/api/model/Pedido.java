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
import java.util.ArrayList;
import java.util.List;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author fabio
 */
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
    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime dataRegistro;

    @NotNull(message = "Data entrega é obrigatório")
    @Column(name = "data_hora_entrega", nullable = false)
    private LocalDateTime dataHoraEntrega;

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

    @OneToOne
    @JoinColumn(name = "pagamento_id", nullable = false, foreignKey = @ForeignKey(name = "fk_pedido_pagamento"))
    private Pagamento pagamento = new Pagamento();

    @Enumerated(EnumType.STRING)
    @Column(name = "pedido_status", nullable = false)
    private PedidoStatus pedidoStatus = PedidoStatus.CANCELADA;

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

    public LocalDateTime getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(LocalDateTime dataRegistro) {
        this.dataRegistro = dataRegistro;
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

    public BigDecimal getValorInicial() {
        return valorInicial;
    }

    public void setValorInicial(BigDecimal valorInicial) {
        this.valorInicial = valorInicial;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public List<PedidoItem> getPedidoItems() {
        return pedidoItems;
    }

    public void setPedidoItems(List<PedidoItem> pedidoItems) {
        this.pedidoItems = pedidoItems;
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

    public Pagamento getPagamento() {
        return pagamento;
    }

    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }

    public PedidoStatus getPedidoStatus() {
        return pedidoStatus;
    }

    public void setPedidoStatus(PedidoStatus pedidoStatus) {
        this.pedidoStatus = pedidoStatus;
    }

    //    public void adicionarItens(List<PedidoItem> itens) {
//        this.setPedidoItems(itens);
//        this.getPedidoItems().forEach(i -> i.setPedido(this));
//    }
//
//    public BigDecimal getValorTotalItens() {
//        return getPedidoItems().stream()
//                .map(PedidoItem::getValorTotal)
//                .reduce(BigDecimal::add)
//                .orElse(BigDecimal.ZERO);
//    }
//
//    public void calcularValorTotal() {
//        this.setValorTotal(calcularValorTotal(getValorTotalItens(), getValorFrete(), getValorDesconto()));
//    }
//
//    public Long getDiasCriacao() {
//        LocalDate inicio = getDataRegistro() != null ? getDataRegistro().toLocalDate() : LocalDate.now();
//        return ChronoUnit.DAYS.between(inicio, LocalDate.now());
//    }
//
//    public boolean isSalvarPermitido() {
//        return !pedidoStatus.equals(PedidoStatus.CANCELADA);
//    }
//
//    public boolean isSalvarProibido() {
//        return !isSalvarPermitido();
//    }
//
//    private BigDecimal calcularValorTotal(BigDecimal valorTotalItens, BigDecimal valorFrete, BigDecimal valorDesconto) {
//        BigDecimal total = valorTotalItens
//                .add(Optional.ofNullable(valorFrete).orElse(BigDecimal.ZERO))
//                .subtract(Optional.ofNullable(valorDesconto).orElse(BigDecimal.ZERO));
//        return total;
//    }
}
