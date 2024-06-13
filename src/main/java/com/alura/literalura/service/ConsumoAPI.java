package com.alura.literalura.service;
import com.alura.literalura.model.ApiResults;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsumoAPI {

    public String obterDados(String endereco) throws JsonProcessingException {

        System.out.println("Saindo..enderecoenderecoendereco." + endereco);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(endereco))
                    .build();
            HttpResponse<String> response = null;
            try {
                response = client
                        .send(request, HttpResponse.BodyHandlers.ofString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

      //  String json = response.body();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(response.body());
        String json = String.valueOf(jsonNode.get("results"));

        //System.out.println("results" + algo);
        int startingIndex = 1; // Exclude the first character
        int endingIndex = json.length() - 1; // Exclude the last character

        String modifiedString = json.substring(startingIndex, endingIndex);


        System.out.println("Saindo..json." + json);
        System.out.println("Saindo..modifiedString." + modifiedString);


            return modifiedString;
        }
}
