/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.oferta.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author frc
 */
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "subcategoria", schema = "oferta")
public class SubCategoria implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull(message = "O nome é obrigatório")
    @Column(name = "nome", nullable = false)
    private String nome;

    @JsonIgnoreProperties({"subCategorias", "seguimento"})
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "categoria_id", foreignKey = @ForeignKey(name = "fk_subcategoria_categoria"))
    private Categoria categoria;

    @JsonIgnoreProperties({"promocao", "loja", "estoque", "arquivos", "marca", "cores", "tamanhos", "subCategoria"})
    @OneToMany(mappedBy = "subCategoria", fetch = FetchType.EAGER)
    private List<Produto> produtos;
}
