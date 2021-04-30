package com.br.oferta.api.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.DecimalMax;
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
@Table(name = "promocao", schema = "oferta")
public class Promocao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull(message = "O nome da promoção é obrigatório")
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotNull(message = "A descrição é obrigatório")
    @Column(name = "descricao", nullable = false)
    private String descricao;

    @Column(name = "foto", nullable = true)
    private String foto;

    @NotNull(message = "O desconto é obrigatório")
    @DecimalMax(value = "100.0", message = "O valor do desconto deve ser menor que 100")
    @Column(name = "desconto", nullable = false)
    private BigDecimal desconto = BigDecimal.ZERO;

    @Column(name = "status")
    private boolean status;

    @NotNull(message = "A data de registro é obrigatório")
    @Column(name = "data_registro", nullable = false)
    private LocalDate dataRegistro;

    @NotNull(message = "A daga de início é obrigatório")
    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;

    @NotNull(message = "A data de encerramento é obrigatório")
    @Column(name = "data_encerramento", nullable = false)
    private LocalDate dataFinal;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "promocaotipo_id", foreignKey = @ForeignKey(name = "fk_promocao_tipo"))
    private PromocaoTipo promocaoTipo;

    @JsonIgnoreProperties({"promocao", "loja", "estoque", "arquivos", "marca", "cores", "tamanhos", "subCategoria", "medida"})
    @OneToMany(mappedBy = "promocao", fetch = FetchType.LAZY)
    private List<Produto> produtos;

    @JsonIgnoreProperties({"usuario", "produtos", "enderecos"})
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "loja_id", foreignKey = @ForeignKey(name = "fk_promocao_loja"))
    private Loja loja;
}
