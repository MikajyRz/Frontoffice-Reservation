package com.frontoffice;

import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class BackofficeReservationsClient {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final String baseUrl;

    public BackofficeReservationsClient(
            RestTemplate restTemplate,
            ObjectMapper objectMapper,
            @Value("${backoffice.base-url}") String baseUrl) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.baseUrl = baseUrl;
    }

    public List<ReservationRowDto> listReservations() {
        try {
            String json = restTemplate.getForObject(baseUrl + "/api/reservations", String.class);
            if (json == null || json.isBlank()) {
                return Collections.emptyList();
            }

            JsonNode root = objectMapper.readTree(json);
            JsonNode data = root.get("data");
            if (data == null || !data.isArray()) {
                return Collections.emptyList();
            }

            return objectMapper.readerForListOf(ReservationRowDto.class).readValue(data);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de l'appel API backoffice: " + baseUrl + "/api/reservations", e);
        }
    }
}
