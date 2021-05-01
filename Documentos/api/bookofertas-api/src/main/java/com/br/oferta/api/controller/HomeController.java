/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.oferta.api.controller;

import io.swagger.annotations.Api;
import java.time.LocalDate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 *
 * @author frc
 */
@RestController
@RequestMapping("/")
@Api(value = "API REST E-COMMERCE")
@CrossOrigin(origins = "*")
public class HomeController {

    @CrossOrigin(maxAge = 10, allowCredentials = "false")
    @GetMapping()
    public String home() {
        String descricao = " ************ BOOKOFERTAS - " + LocalDate.now() + " ************ ";
        return descricao;
    }

    @GetMapping("swagger-ui.html")
    public String swagger() {
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/swagger-ui.html").path("").toUriString();
        return fileDownloadUri;
    }

}
