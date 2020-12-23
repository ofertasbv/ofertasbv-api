/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.oferta.api.util.teste;

import com.br.oferta.api.model.Marca;
import com.br.oferta.api.repository.MarcaRepository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author fabio
 */
public class ProdutoCadastro {

    private MarcaRepository marcaRepository;

    @PersistenceContext
    private EntityManager em;

    private void cadastro(Marca m) {
        marcaRepository.save(m);
    }

    public static void main(String[] args) {
        ProdutoCadastro pc = new ProdutoCadastro();
        Marca m = new Marca();
        m.setNome("teste 111");
        pc.cadastro(m);
        System.out.println("sucesso...");

    }
}
