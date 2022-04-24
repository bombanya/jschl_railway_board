package com.bombanya.jschl_railway_board;

import com.bombanya.jschl_railway_board.entities.Station;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Getter;
import lombok.SneakyThrows;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import java.util.Collections;
import java.util.List;

@Singleton
@Startup
public class StationsHolderBean {

    private final String serverUrl = "http://localhost:8080/javaschool_railway-1.0-SNAPSHOT";
    private final ObjectMapper objectMapper = JsonMapper.builder()
            .addModule(new JavaTimeModule())
            .build();

    @Getter
    private List<Station> stations;

    @PostConstruct
    @SneakyThrows
    private void init(){
        Client client = ClientBuilder.newClient();
        String response = client.target(serverUrl)
                .path("/api/geography/public/station/all")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get(String.class);
        client.close();
        response = response
                .substring(response.indexOf('['), response.lastIndexOf(']') + 1);
        stations = Collections.unmodifiableList(objectMapper
                .readValue(response, new TypeReference<List<Station>>() {}));
        //stations.forEach(System.out::println);
    }
}
