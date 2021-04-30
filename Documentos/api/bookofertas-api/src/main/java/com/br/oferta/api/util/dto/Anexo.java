package com.br.oferta.api.util.dto;

public class Anexo {

    private final String nome;

    private final String url;

    public Anexo(String nome, String url) {
        this.nome = nome;
        this.url = url;
    }

    public String getNome() {
        return nome;
    }

    public String getUrl() {
        return url;
    }
}
