package com.br.oferta.api.util.validation;

import com.br.oferta.api.model.Pedido;
import java.math.BigDecimal;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class VendaValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Pedido.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "cliente.codigo", "", "Selecione um cliente na pesquisa rápida");

        Pedido venda = (Pedido) target;
        validarSeInformouApenasHorarioEntrega(errors, venda);
        validarSeInformouItens(errors, venda);
        validarValorTotalNegativo(errors, venda);
    }

    private void validarValorTotalNegativo(Errors errors, Pedido venda) {
        if (venda.getValorTotal().compareTo(BigDecimal.ZERO) < 0) {
            errors.reject("", "Valor total não pode ser negativo");
        }
    }

    private void validarSeInformouItens(Errors errors, Pedido venda) {
        if (venda.getPedidoItems().isEmpty()) {
            errors.reject("", "Adicione pelo menos um produto na venda");
        }
    }

    private void validarSeInformouApenasHorarioEntrega(Errors errors, Pedido venda) {
//        if (venda.getHorarioEntrega() != null && venda.getDataEntrega() == null) {
//            errors.rejectValue("dataEntrega", "", "Informe uma data da entrega para um horário");
//        }
    }

}
