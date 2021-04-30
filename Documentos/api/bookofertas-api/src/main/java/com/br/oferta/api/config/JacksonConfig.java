///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.br.oferta.api.config;
//
///**
// *
// * @author fabio
// */
//import com.bedatadriven.jackson.datatype.jts.JtsModule;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
//
//@Configuration
//public class JacksonConfig {
//
//    @Bean
//    public JtsModule jtsModule() {
//        return new JtsModule();
//    }
//
//    @Bean
//    public Jackson2ObjectMapperBuilder jacksonBuilder() {
//        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
//        builder.modulesToInstall(new JtsModule());
//        return builder;
//    }
//}
