package com.frontoffice.client;

import java.util.Collections;
import java.util.List;

import com.frontoffice.dto.ReservationRowDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class BackofficeReservationsClient {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final String baseUrl;
    private final String apiToken;

    public BackofficeReservationsClient(
            RestTemplate restTemplate,
            ObjectMapper objectMapper,
            @Value("${backoffice.base-url}") String baseUrl,
            @Value("${frontoffice.api-token}") String apiToken) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.baseUrl = baseUrl;
        this.apiToken = apiToken;
    }

    public List<ReservationRowDto> listReservations() {
        try {
            HttpHeaders headers = new HttpHeaders();
            if (apiToken != null && !apiToken.isBlank()) {
                headers.setBearerAuth(apiToken);
            }
            HttpEntity<Void> entity = new HttpEntity<>(headers);

            ResponseEntity<String> resp = restTemplate.exchange(
                    baseUrl + "/api/reservations",
                    HttpMethod.GET,
                    entity,
                    String.class);
            String json = resp.getBody();
            if (json == null || json.isBlank()) {
                return Collections.emptyList();
            }

            JsonNode root = objectMapper.readTree(json);
            JsonNode data = root.get("data");
            if (data == null || !data.isArray()) {
                return Collections.emptyList();
            }

            return objectMapper.readerForListOf(ReservationRowDto.class).readValue(data);
        } catch (HttpClientErrorException.Unauthorized e) {
            throw new RuntimeException("Token invalide ou expire", e);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de l'appel API backoffice: " + baseUrl + "/api/reservations", e);
        }
    }
}
