package com.br.oferta.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
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
@Table(name = "boleto", schema = "oferta")
@PrimaryKeyJoinColumn(name = "boleto_id", foreignKey = @ForeignKey(name = "fk_boleto"))
@SuppressWarnings("PersistenceUnitPresent")
public class Boleto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull(message = "Valor é obrigatório")
    @DecimalMin(value = "0.50", message = "O valor do produto deve ser maior que R$0,00")
    @DecimalMax(value = "9999999.99", message = "O valor do produto deve ser menor que R$9.999.999,99")
    private BigDecimal valor;

    @NotNull(message = "A data de emissão é obrigatória")
    @Column(name = "data_emissao")
    private LocalDate dataEmissao;

    @NotNull(message = "A data de vencimento é obrigatória")
    @Column(name = "data_vencimento")
    private LocalDate dataVencimento;

    @JsonIgnoreProperties({"enderecos", "usuario"})
    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false, foreignKey = @ForeignKey(name = "fk_boleto_cliente"))
    private Cliente cliente;
}
