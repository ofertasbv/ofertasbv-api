/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.oferta.api.util.seriazable;

import com.br.oferta.api.model.Produto;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;

/**
 *
 * @author fabio
 */
public class ExampleSampleSerializer extends JsonSerializer<Produto> {

    @Override
    public void serialize(Produto value, JsonGenerator jsonGenerator, SerializerProvider serializers) throws IOException, JsonProcessingException {

        jsonGenerator.writeStartObject();

        jsonGenerator.writeEndObject();
    }

}
