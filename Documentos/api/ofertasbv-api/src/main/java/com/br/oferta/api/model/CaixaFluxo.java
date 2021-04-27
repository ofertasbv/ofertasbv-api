package com.br.oferta.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
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
    
    @NotNull(message = "Saldo anterior é obrigatório")
    @DecimalMax(value = "9999999.99", message = "Saldo anterior deve ser menor que R$9.999.999,99")
    @Column(name = "saldo_anterior", precision = 10, scale = 2)
    private BigDecimal saldoAnterior;
    
    @NotNull(message = "Valor da entrada é obrigatório")
    @DecimalMin(value = "0.50", message = "O valor da entrada deve ser maior que R$0,00")
    @DecimalMax(value = "9999999.99", message = "O valor da entrada deve ser menor que R$9.999.999,99")
    @Column(name = "valor_entrada", precision = 10, scale = 2)
    private BigDecimal valorEntrada;
    
    @NotNull(message = "Valor da saida é obrigatório")
    @DecimalMin(value = "0.50", message = "O valor da saida deve ser maior que R$0,00")
    @DecimalMax(value = "9999999.99", message = "O valor da saida deve ser menor que R$9.999.999,99")
    @Column(name = "valor_saida", precision = 10, scale = 2)
    private BigDecimal valorSaida;
    
    @NotNull(message = "Valor total é obrigatório")
    @DecimalMin(value = "0.50", message = "O valor da saida deve ser maior que R$0,00")
    @DecimalMax(value = "9999999.99", message = "O valor da saida deve ser menor que R$9.999.999,99")
    @Column(name = "valor_total", precision = 10, scale = 2)
    private BigDecimal valorTotal;
    
    @NotNull(message = "A data de registro é obrigatória")
    @Column(name = "data_registro")
    private LocalDate dataRegistro;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "caixa_status", nullable = false)
    private CaixaStatus caixaStatus = CaixaStatus.FECHADO;
    
    @Column(name = "status")
    private boolean status;
    
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "caixa_id", foreignKey = @ForeignKey(name = "fk_caixa_fluxo_caixa"))
    private Caixa caixa;
    
    @JsonIgnoreProperties({"caixaFluxo"})
    @OneToMany(mappedBy = "caixaFluxo", fetch = FetchType.LAZY)
    private List<CaixaFluxoEntrada> caixaFluxoEntradas;
    
    @JsonIgnoreProperties({"caixaFluxo"})
    @OneToMany(mappedBy = "caixaFluxo", fetch = FetchType.LAZY)
    private List<CaixaFluxoSaida> caixaFluxoSaidas;
    
    @JsonIgnoreProperties({"enderecos", "usuario"})
    @ManyToOne
    @JoinColumn(name = "vendedor_id", nullable = false, foreignKey = @ForeignKey(name = "fk_caixa_fluxo_vendedor"))
    private Vendedor vendedor;
    
    public BigDecimal getValorTotalEntradas() {
        return getCaixaFluxoEntradas().stream()
                .map(CaixaFluxoEntrada::getValorEntrada)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }
    
    public BigDecimal getValorTotalSaidas() {
        return getCaixaFluxoSaidas().stream()
                .map(CaixaFluxoSaida::getValorSaida)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }
    
    public BigDecimal getValorSaldoDoDia() {
        return getValorTotalEntradas().subtract(getValorTotalSaidas());
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
    
    public BigDecimal getSaldoAnterior() {
        return saldoAnterior;
    }
    
    public void setSaldoAnterior(BigDecimal saldoAnterior) {
        this.saldoAnterior = saldoAnterior;
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
    
    public LocalDate getDataRegistro() {
        return dataRegistro;
    }
    
    public void setDataRegistro(LocalDate dataRegistro) {
        this.dataRegistro = dataRegistro;
    }
    
    public CaixaStatus getCaixaStatus() {
        return caixaStatus;
    }
    
    public void setCaixaStatus(CaixaStatus caixaStatus) {
        this.caixaStatus = caixaStatus;
    }
    
    public boolean isStatus() {
        return status;
    }
    
    public void setStatus(boolean status) {
        this.status = status;
    }
    
    public Caixa getCaixa() {
        return caixa;
    }
    
    public void setCaixa(Caixa caixa) {
        this.caixa = caixa;
    }
    
    public Vendedor getVendedor() {
        return vendedor;
    }
    
    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }
    
    public List<CaixaFluxoEntrada> getCaixaFluxoEntradas() {
        return caixaFluxoEntradas;
    }
    
    public void setCaixaFluxoEntradas(List<CaixaFluxoEntrada> caixaFluxoEntradas) {
        this.caixaFluxoEntradas = caixaFluxoEntradas;
    }
    
    public List<CaixaFluxoSaida> getCaixaFluxoSaidas() {
        return caixaFluxoSaidas;
    }
    
    public void setCaixaFluxoSaidas(List<CaixaFluxoSaida> caixaFluxoSaidas) {
        this.caixaFluxoSaidas = caixaFluxoSaidas;
    }
    
}
