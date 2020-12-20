package com.br.oferta.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "caixa_fluxo", schema = "oferta")
@SuppressWarnings("PersistenceUnitPresent")
public class CaixaFluxo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "descricao")
    private String descricao;

    @NotNull(message = "Valor da entrada é obrigatório")
    @DecimalMin(value = "0.50", message = "O valor da entrada deve ser maior que R$0,00")
    @DecimalMax(value = "9999999.99", message = "O valor da entrada deve ser menor que R$9.999.999,99")
    @Column(name = "valor_entrada")
    private BigDecimal valorEntrada;

    @NotNull(message = "Valor da saida é obrigatório")
    @DecimalMin(value = "0.50", message = "O valor da saida deve ser maior que R$0,00")
    @DecimalMax(value = "9999999.99", message = "O valor da saida deve ser menor que R$9.999.999,99")
    @Column(name = "valor_saida")
    private BigDecimal valorSaida;

    @NotNull(message = "Valor total é obrigatório")
    @DecimalMin(value = "0.50", message = "O valor da saida deve ser maior que R$0,00")
    @DecimalMax(value = "9999999.99", message = "O valor da saida deve ser menor que R$9.999.999,99")
    @Column(name = "valor_total")
    private BigDecimal valorTotal;

    @NotNull(message = "A data de registro é obrigatória")
    @Column(name = "data_registro")
    private LocalDateTime dataRegistro;

    @Enumerated(EnumType.STRING)
    @Column(name = "pedido_status", nullable = false)
    private CaixaStatus caixaStatus = CaixaStatus.FECHADO;

    @JsonIgnoreProperties({"enderecos", "usuario"})
    @ManyToOne
    @JoinColumn(name = "vendedor_id", nullable = false, foreignKey = @ForeignKey(name = "fk_caixa_vendedor"))
    private Vendedor vendedor;

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

    public BigDecimal getValorEntrada() {
        return valorEntrada;
    }

    public void setValorEntrada(BigDecimal valorEntrada) {
        this.valorEntrada = valorEntrada;
    }

    public BigDecimal getValorSaida() {
        return valorSaida;
    }

    public void setValorSaida(BigDecimal valorSaida) {
        this.valorSaida = valorSaida;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public LocalDateTime getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(LocalDateTime dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    public CaixaStatus getCaixaStatus() {
        return caixaStatus;
    }

    public void setCaixaStatus(CaixaStatus caixaStatus) {
        this.caixaStatus = caixaStatus;
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
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
        CaixaFluxo other = (CaixaFluxo) obj;
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