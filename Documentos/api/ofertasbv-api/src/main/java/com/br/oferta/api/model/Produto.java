package com.br.oferta.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
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
@Table(name = "produto", schema = "oferta")
public class Produto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "sku")
    private String sku;

    @NotNull(message = "O nome é obrigatório")
    @Column(name = "nome")
    private String nome;

    @NotNull(message = "A descrição é obrigatória")
    @Column(name = "descricao")
    private String descricao;

    @Column(name = "foto")
    private String foto;

    @Column(name = "codigo_barra", unique = true)
    private String codigoBarra;

    @Column(name = "status")
    private boolean status;

    @Column(name = "novo")
    private boolean novo;

    @Column(name = "destaque")
    private boolean destaque;

    @Enumerated(EnumType.STRING)
    private Origem origem = Origem.NACIONAL;

    @JsonIgnoreProperties({"categoria", "produtos"})
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subcategoria_id", foreignKey = @ForeignKey(name = "fk_produto_subcategoria"))
    private SubCategoria subCategoria;

    @JsonIgnoreProperties({"produtos"})
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "medida_id", foreignKey = @ForeignKey(name = "fk_produto_medida"))
    private Medida medida;

    @JsonIgnoreProperties({"produtos", "loja", "promocaoTipo"})
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "promocao_id", foreignKey = @ForeignKey(name = "fk_produto_promocao"))
    private Promocao promocao;

    @JsonIgnoreProperties({"promocaos", "produtos", "usuario", "enderecos"})
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "loja_id", foreignKey = @ForeignKey(name = "fk_produto_loja"))
    private Loja loja;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "produto_arquivo",
            joinColumns = @JoinColumn(name = "produto_id"),
            inverseJoinColumns = @JoinColumn(name = "arquivo_id"),
            foreignKey = @ForeignKey(name = "fk_produto_id"),
            inverseForeignKey = @ForeignKey(name = "fk_arquivo_id"))
    private List<Arquivo> arquivos = new ArrayList<>();

    @OneToOne(cascade = {CascadeType.ALL, CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "estoque_id", foreignKey = @ForeignKey(name = "fk_produto_estoque"))
    private Estoque estoque = new Estoque();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "marca_id", foreignKey = @ForeignKey(name = "fk_produto_marca"), nullable = false)
    private Marca marca;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "produto_tamanho",
            joinColumns = @JoinColumn(name = "produto_id"),
            inverseJoinColumns = @JoinColumn(name = "tamanho_id"),
            foreignKey = @ForeignKey(name = "fk_produto_id"),
            inverseForeignKey = @ForeignKey(name = "fk_tamanho_id"))
    private List<Tamanho> tamanhos = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "produto_cor",
            joinColumns = @JoinColumn(name = "produto_id"),
            inverseJoinColumns = @JoinColumn(name = "cor_id"),
            foreignKey = @ForeignKey(name = "fk_produto_id"),
            inverseForeignKey = @ForeignKey(name = "fk_cor_id"))
    private List<Cor> cores = new ArrayList<>();

    @Transient
    private BigDecimal valorComDesconto;
}
