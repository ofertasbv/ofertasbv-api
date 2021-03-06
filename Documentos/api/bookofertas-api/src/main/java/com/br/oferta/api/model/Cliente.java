/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.oferta.api.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
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
@Table(name = "cliente", schema = "oferta")
@PrimaryKeyJoinColumn(name = "cliente_id", foreignKey = @ForeignKey(name = "fk_cliente"))
public class Cliente extends Pessoa implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "O cpf é obrigatório")
    @Column(name = "cpf", unique = true)
    private String cpf;

    @Column(name = "sexo")
    private String sexo;
}
