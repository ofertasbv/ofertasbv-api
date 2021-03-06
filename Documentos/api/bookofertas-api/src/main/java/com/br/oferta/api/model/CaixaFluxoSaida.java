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
@Table(name = "caixa_fluxo_saida", schema = "oferta")
@SuppressWarnings("PersistenceUnitPresent")
public class CaixaFluxoSaida implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "descricao")
    private String descricao;

    @NotNull(message = "Valor da saída é obrigatório")
    @DecimalMin(value = "0.50", message = "O valor da saída deve ser maior que R$0,00")
    @DecimalMax(value = "9999999.99", message = "O valor da saída deve ser menor que R$9.999.999,99")
    @Column(name = "valor_saida")
    private BigDecimal valorSaida;

    @NotNull(message = "A data de registro é obrigatória")
    @Column(name = "data_registro")
    private LocalDate dataRegistro;

    @JsonIgnoreProperties({"caixa", "vendedor", "caixaFluxoSaidas"})
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "caixafluxo_id", foreignKey = @ForeignKey(name = "fk_caixa_fluxo_saida_caixa_fluxo"))
    private CaixaFluxo caixaFluxo;
}
