/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.oferta.api.util.teste;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;

/**
 *
 * @author fabio
 */
public class TesteUUID {

    public String geradorNome(String nome) {
        UUID uuid = UUID.randomUUID();
        nome = uuid.toString();
        return nome;
    }

    @SuppressWarnings("ImplicitArrayToString")
    public static void main(String[] args) throws UnknownHostException {

        String ipDaMaquina = InetAddress.getLocalHost().getHostAddress();
        System.out.println(ipDaMaquina);
        //nome da maquina.
        String nomeDaMaquina = InetAddress.getLocalHost().getHostName();
        System.out.println(nomeDaMaquina);

        TesteUUID d = new TesteUUID();
        String nome = "teste.png";///d.geradorNome("teste.png");

        String[] output = nome.split("\\.");

        String caminho = output[0];
        String extenção = output[1];

        System.out.println(caminho);
        System.out.println(extenção);

        System.out.println("Random UUID String = " + nome + ".png");

        String texto = "C:/fakepath/logica.pdf ";
        System.out.println(texto.substring(texto.lastIndexOf("/") + 1));
    }
}
