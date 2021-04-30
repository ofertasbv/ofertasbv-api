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
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
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

}
