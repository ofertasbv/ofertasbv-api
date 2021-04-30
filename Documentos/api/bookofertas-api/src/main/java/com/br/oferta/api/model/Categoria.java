package com.br.oferta.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
@Table(name = "categoria", schema = "oferta")
public class Categoria implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull(message = "O nome é obrigatório")
    @Column(name = "nome")
    private String nome;

    @NotNull(message = "A cor é obrigatório")
    @Column(name = "color")
    private String color;

    @Column(name = "foto")
    private String foto;

    @JsonIgnoreProperties({"categorias"})
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "seguimento_id", foreignKey = @ForeignKey(name = "fk_categoria_seguimento"))
    private Seguimento seguimento;

    @JsonIgnoreProperties({"categoria", "produtos"})
    @OneToMany(mappedBy = "categoria", fetch = FetchType.LAZY)
    private List<SubCategoria> subCategorias;

}
