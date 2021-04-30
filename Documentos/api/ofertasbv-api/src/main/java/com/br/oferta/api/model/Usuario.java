package com.br.oferta.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.ArrayList;
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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@Table(name = "usuario", schema = "oferta")
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull(message = "O email é obrigatório")
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull(message = "A senha é obrigatório")
    @Column(name = "senha", nullable = false)
    private String senha;

    @Transient
    private String confirmaSenha;

    @JsonIgnoreProperties({"enderecos", "usuario", "produtos"})
    @OneToOne(mappedBy = "usuario", fetch = FetchType.EAGER, optional = false)
    private Pessoa pessoa;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL, CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinTable(name = "usuario_grupo",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "grupo_id"),
            foreignKey = @ForeignKey(name = "fk_usuario_id"),
            inverseForeignKey = @ForeignKey(name = "fk_grupo_id"))
    private List<Grupo> grupos = new ArrayList<>();
}
