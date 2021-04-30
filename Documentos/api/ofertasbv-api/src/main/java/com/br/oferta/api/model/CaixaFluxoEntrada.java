package com.br.oferta.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
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
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
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
    private LocalDate dataRegistro;

    @JsonIgnoreProperties({"caixa", "vendedor", "caixaFluxoEntradas"})
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "caixafluxo_id", foreignKey = @ForeignKey(name = "fk_caixa_fluxo_entrada_caixa_fluxo"))
    private CaixaFluxo caixaFluxo;

    @JsonIgnoreProperties({"cliente", "loja", "pagamentos", "pedidoItems"})
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "pedido_id", foreignKey = @ForeignKey(name = "fk_caixa_fluxo_entrada_pedido"))
    private Pedido pedido;
}
