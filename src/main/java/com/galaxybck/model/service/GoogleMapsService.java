package com.galaxybck.model.service;

import com.galaxybck.model.dto.DistanceResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
public class GoogleMapsService {

    private static final Logger log = LoggerFactory.getLogger(GoogleMapsService.class);

    @Value("${google.maps.api-key}")
    private String apiKey;

    @Value("${google.maps.distance-matrix-url}")
    private String distanceMatrixUrl;

    private final RestTemplate restTemplate;

    public GoogleMapsService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public double getDistanceInKm(String origin, String destination, String region) {
        URI uri = UriComponentsBuilder.fromUriString(distanceMatrixUrl)
                .queryParam("origins", origin + ", Ireland")
                .queryParam("destinations", destination + ", Ireland")
                .queryParam("units", "metric")
                .queryParam("region", region)
                .queryParam("components", "country:IE")
                .queryParam("key", apiKey)
                .build()
                .toUri();

        log.info("Consultando distância: {} -> {}", origin, destination);

        DistanceResponse response = restTemplate.getForObject(uri, DistanceResponse.class);

        if (response == null || !"OK".equals(response.getStatus())) {
            throw new RuntimeException("Erro ao consultar Google Maps API");
        }

        DistanceResponse.Element element = response.getRows().get(0).getElements().get(0);

        if (!"OK".equals(element.getStatus())) {
            throw new RuntimeException("Rota não encontrada: " + element.getStatus());
        }

        double km = element.getDistance().toKilometers();
        log.info("Resposta Google Maps: texto={}, metros={}, km={}", element.getDistance().getText(), element.getDistance().getValue(), km);
        return km;
    }
}
