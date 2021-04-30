/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.oferta.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author fabio
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "loja", schema = "oferta")
@PrimaryKeyJoinColumn(name = "loja_id", foreignKey = @ForeignKey(name = "fk_loja"))
public class Loja extends Pessoa implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "A razão social é obrigatório")
    @Column(name = "razao_social")
    private String razaoSocial;

    @NotNull(message = "O cnpj é obrigatório")
    @Column(name = "cnpj", unique = true)
    private String cnpj;

    @JsonIgnore
    @OneToMany(mappedBy = "loja", fetch = FetchType.LAZY)
    private List<Promocao> promocaos;

    @JsonIgnoreProperties({"loja", "subCategoria", "promocao", "marca", "estoque", "arquivos", "cores", "tamanhos"})
    @OneToMany(mappedBy = "loja", fetch = FetchType.LAZY)
    private List<Produto> produtos;

}
