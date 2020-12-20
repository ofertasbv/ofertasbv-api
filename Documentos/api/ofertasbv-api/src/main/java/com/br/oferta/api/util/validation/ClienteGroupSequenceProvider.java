package com.br.oferta.api.util.validation;

import com.br.oferta.api.model.Cliente;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;

public class ClienteGroupSequenceProvider implements DefaultGroupSequenceProvider<Cliente> {

    @Override
    public List<Class<?>> getValidationGroups(Cliente cliente) {
        List<Class<?>> grupos = new ArrayList<>();
        grupos.add(Cliente.class);

        if (isPessoaSelecionada(cliente)) {
            grupos.add(cliente.getTipoPessoa().getGrupo());
        }

        return grupos;
    }

    private boolean isPessoaSelecionada(Cliente cliente) {
        return cliente != null && cliente.getTipoPessoa() != null;
    }

}
