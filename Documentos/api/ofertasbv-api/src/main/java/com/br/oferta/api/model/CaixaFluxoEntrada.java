package com.br.oferta.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "caixa_fluxo_entrada", schema = "oferta")
@SuppressWarnings("PersistenceUnitPresent")
public class CaixaFluxoEntrada implements Serializable {

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

    @NotNull(message = "A data de registro é obrigatória")
    @Column(name = "data_registro")
    private LocalDateTime dataRegistro;

    @JsonIgnoreProperties({"caixa", "vendedor"})
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "caixafluxo_id", foreignKey = @ForeignKey(name = "fk_caixa_fluxo_entrada_caixa_fluxo"))
    private CaixaFluxo caixaFluxo;

    @JsonIgnoreProperties({"cliente", "loja", "pagamentos", "pedidoItems"})
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "pedido_id", foreignKey = @ForeignKey(name = "fk_caixa_fluxo_entrada_pedido"))
    private Pedido pedido;

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
        CaixaFluxoEntrada other = (CaixaFluxoEntrada) obj;
        if (getId() == null) {
            if (other.getId() != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
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

    public BigDecimal getValorEntrada() {
        return valorEntrada;
    }

    public void setValorEntrada(BigDecimal valorEntrada) {
        this.valorEntrada = valorEntrada;
    }

    public LocalDateTime getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(LocalDateTime dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    public CaixaFluxo getCaixaFluxo() {
        return caixaFluxo;
    }

    public void setCaixaFluxo(CaixaFluxo caixaFluxo) {
        this.caixaFluxo = caixaFluxo;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

}
