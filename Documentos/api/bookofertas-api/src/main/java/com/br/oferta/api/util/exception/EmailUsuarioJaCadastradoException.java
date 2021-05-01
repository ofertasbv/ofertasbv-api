package com.br.oferta.api.util.exception;

public class EmailUsuarioJaCadastradoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public EmailUsuarioJaCadastradoException(String message) {
        super(message);
    }

}
