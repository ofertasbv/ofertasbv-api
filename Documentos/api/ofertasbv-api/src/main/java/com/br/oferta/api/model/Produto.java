package com.br.oferta.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "produto", schema = "oferta")
public class Produto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

//    @SKU
    @NotBlank
    private String sku;

    @NotNull(message = "O nome é obrigatório")
    @Column(name = "nome")
    private String nome;

    @NotNull(message = "A descrição é obrigatória")
    @Column(name = "descricao")
    private String descricao;

    @NotNull(message = "A data de registro é obrigatória")
    @Column(name = "data_registro")
    private LocalDate dataRegistro;

    @Column(name = "codigo_barra")
    private String codigoBarra;

    @Column(name = "status")
    private boolean status;

    @Column(name = "novo")
    private boolean novo;

    @Column(name = "destaque")
    private boolean destaque;

    @NotNull(message = "A medida é obrigatória")
    @Enumerated(EnumType.STRING)
    private Medida medida = Medida.OUTRO;

    @NotNull(message = "A origem é obrigatória")
    @Enumerated(EnumType.STRING)
    private Origem origem;

    @JsonIgnoreProperties({"categoria", "produtos"})
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subcategoria_id", foreignKey = @ForeignKey(name = "fk_produto_subcategoria"))
    private SubCategoria subCategoria;

    @JsonIgnoreProperties({"produtos", "loja"})
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public LocalDate getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(LocalDate dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    public String getCodigoBarra() {
        return codigoBarra;
    }

    public void setCodigoBarra(String codigoBarra) {
        this.codigoBarra = codigoBarra;
    }

    public Medida getMedida() {
        return medida;
    }

    public void setMedida(Medida medida) {
        this.medida = medida;
    }

    public SubCategoria getSubCategoria() {
        return subCategoria;
    }

    public void setSubCategoria(SubCategoria subCategoria) {
        this.subCategoria = subCategoria;
    }

    public Loja getLoja() {
        return loja;
    }

    public void setLoja(Loja loja) {
        this.loja = loja;
    }

    public List<Arquivo> getArquivos() {
        return arquivos;
    }

    public void setArquivos(List<Arquivo> arquivos) {
        this.arquivos = arquivos;
    }

    public Estoque getEstoque() {
        return estoque;
    }

    public void setEstoque(Estoque estoque) {
        this.estoque = estoque;
    }

    public boolean isDestaque() {
        return destaque;
    }

    public void setDestaque(boolean destaque) {
        this.destaque = destaque;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public boolean isNovo() {
        return novo;
    }

    public void setNovo(boolean novo) {
        this.novo = novo;
    }

    public Origem getOrigem() {
        return origem;
    }

    public void setOrigem(Origem origem) {
        this.origem = origem;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Promocao getPromocao() {
        return promocao;
    }

    public void setPromocao(Promocao promocao) {
        this.promocao = promocao;
    }

    public List<Tamanho> getTamanhos() {
        return tamanhos;
    }

    public void setTamanhos(List<Tamanho> tamanhos) {
        this.tamanhos = tamanhos;
    }

    public List<Cor> getCores() {
        return cores;
    }

    public void setCores(List<Cor> cores) {
        this.cores = cores;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
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
        Produto other = (Produto) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }

}
