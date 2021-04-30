package com.br.oferta.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
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
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "pessoa", schema = "oferta")
@Inheritance(strategy = InheritanceType.JOINED)
@SuppressWarnings({"ConsistentAccessType", "IdDefinedInHierarchy"})
public class Pessoa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Column(name = "nome")
    private String nome;

    @NotBlank(message = "Telefone é obrigatório")
    @Column(name = "telefone")
    private String telefone;

    @Column(name = "ativo")
    private boolean ativo = Boolean.TRUE;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_pessoa")
    private TipoPessoa tipoPessoa = TipoPessoa.FISICA;

    @Column(name = "foto")
    private String foto;

    @JsonIgnoreProperties({"pessoa"})
    @OneToOne(cascade = {CascadeType.ALL, CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "usuario_id", foreignKey = @ForeignKey(name = "fk_pessoa_usuario"), nullable = false)
    private Usuario usuario = new Usuario();

    @JsonIgnoreProperties({"pessoas"})
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "endereco_pessoa",
            joinColumns = @JoinColumn(name = "pessoa_id"),
            inverseJoinColumns = @JoinColumn(name = "endereco_id"),
            foreignKey = @ForeignKey(name = "fk_pessoa_id"),
            inverseForeignKey = @ForeignKey(name = "fk_endereco_id"))
    private List<Endereco> enderecos = new ArrayList<>();

}
