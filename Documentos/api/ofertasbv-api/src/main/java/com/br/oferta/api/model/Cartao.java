package com.br.oferta.api.model;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "cartao", schema = "oferta")
@PrimaryKeyJoinColumn(name = "cartao_id", foreignKey = @ForeignKey(name = "fk_cartao"))
@SuppressWarnings("PersistenceUnitPresent")
public class Cartao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull(message = "O nome é obrigatório")
    @Column(name = "nome")
    private String nome;

    @NotNull(message = "O número é obrigatório")
    @Column(name = "numero_cartao")
    private String numeroCartao;

    @NotNull(message = "O número de segurança é obrigatório")
    @Column(name = "numero_seguranca")
    private String numeroSeguranca;

    @NotNull(message = "A data de validade é obrigatória")
    @Column(name = "data_validade")
    private LocalDate dataValidade;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "cartao_emissor", nullable = false)
    private CartaoEmissor cartaoEmissor = CartaoEmissor.VISA;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "cartao_tipo", nullable = false)
    private CartaoTipo cartaoTipo = CartaoTipo.DEBITO;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public void setNumeroCartao(String numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    public String getNumeroSeguranca() {
        return numeroSeguranca;
    }

    public void setNumeroSeguranca(String numeroSeguranca) {
        this.numeroSeguranca = numeroSeguranca;
    }

    public LocalDate getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(LocalDate dataValidade) {
        this.dataValidade = dataValidade;
    }

    public CartaoEmissor getCartaoEmissor() {
        return cartaoEmissor;
    }

    public void setCartaoEmissor(CartaoEmissor cartaoEmissor) {
        this.cartaoEmissor = cartaoEmissor;
    }

    public CartaoTipo getCartaoTipo() {
        return cartaoTipo;
    }

    public void setCartaoTipo(CartaoTipo cartaoTipo) {
        this.cartaoTipo = cartaoTipo;
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
        Cartao other = (Cartao) obj;
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
