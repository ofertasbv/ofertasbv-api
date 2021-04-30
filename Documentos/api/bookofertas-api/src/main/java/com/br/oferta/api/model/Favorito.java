package com.br.oferta.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "favorito", schema = "oferta")
public class Favorito implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull(message = "O nome é obrigatório")
    @Column(name = "status")
    private boolean status;

    @JsonIgnoreProperties({"promocao", "loja", "subCategoria", "arquivos", "marca", "estoque", "tamanhos", "cores"})
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "produto_id", foreignKey = @ForeignKey(name = "fk_favorito_produto"))
    private Produto produto;

    @JsonIgnoreProperties({"usuario", "enderecos"})
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cliente_id", foreignKey = @ForeignKey(name = "fk_favorito_cliente"))
    private Cliente cliente;
}
