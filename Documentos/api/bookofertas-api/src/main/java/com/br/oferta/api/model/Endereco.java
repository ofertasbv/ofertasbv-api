package com.br.oferta.api.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
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
@Table(name = "endereco", schema = "oferta")
public class Endereco implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "logradouro")
    private String logradouro;

    @Column(name = "numero")
    private String numero;

    @Column(name = "complemento")
    private String complemento;

    @Column(name = "bairro")
    private String bairro;

    @Column(name = "cep")
    private String cep;

    @Column(name = "latitude")
    private double latitude;

    @Column(name = "longitude")
    private double longitude;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_endereco")
    private TipoEndereco tipoEndereco;

//    @JsonIgnore
//    @Column(name = "local", columnDefinition = "GEOMETRY(Point,4326)")
//    private Point local;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cidade_id")
    private Cidade cidade;
}
