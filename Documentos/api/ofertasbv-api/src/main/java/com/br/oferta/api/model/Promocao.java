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
import javax.persistence.CascadeType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.NotNull;

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

    @Column(name = "foto", nullable = false)
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

    @JsonIgnoreProperties({"promocao", "loja", "estoque", "arquivos", "marca", "cores", "tamanhos", "subCategoria"})
    @OneToMany(mappedBy = "promocao", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Produto> produtos;

    @JsonIgnoreProperties({"usuario", "produtos", "enderecos"})
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "loja_id", foreignKey = @ForeignKey(name = "fk_promocao_loja"))
    private Loja loja;

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
        Promocao other = (Promocao) obj;
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

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public BigDecimal getDesconto() {
        return desconto;
    }

    public void setDesconto(BigDecimal desconto) {
        this.desconto = desconto;
    }

    public LocalDate getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(LocalDate dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(LocalDate dataFinal) {
        this.dataFinal = dataFinal;
    }

    public PromocaoTipo getPromocaoTipo() {
        return promocaoTipo;
    }

    public void setPromocaoTipo(PromocaoTipo promocaoTipo) {
        this.promocaoTipo = promocaoTipo;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public Loja getLoja() {
        return loja;
    }

    public void setLoja(Loja loja) {
        this.loja = loja;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
