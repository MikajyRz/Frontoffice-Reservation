package com.frontoffice.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ReservationRowDto {
    private int id;
    private String id_client;
    private int nombre_passager;
    private String date_heure_arrive;
    private int id_lieu;
    private String lieu_nom;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getId_client() {
        return id_client;
    }

    public void setId_client(String id_client) {
        this.id_client = id_client;
    }

    public int getNombre_passager() {
        return nombre_passager;
    }

    public void setNombre_passager(int nombre_passager) {
        this.nombre_passager = nombre_passager;
    }

    public String getDate_heure_arrive() {
        return date_heure_arrive;
    }

    public void setDate_heure_arrive(String date_heure_arrive) {
        this.date_heure_arrive = date_heure_arrive;
    }

    public String getDate_heure_arrive_formatted() {
        if (date_heure_arrive == null || date_heure_arrive.isBlank()) {
            return "";
        }
        try {
            LocalDateTime ldt = LocalDateTime.parse(date_heure_arrive);
            return ldt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        } catch (Exception e) {
            return date_heure_arrive;
        }
    }

    public int getId_hotel() {
        return id_lieu;
    }

    public void setId_hotel(int id_hotel) {
        this.id_lieu = id_hotel;
    }

    public String getHotel_nom() {
        return lieu_nom;
    }

    public void setHotel_nom(String hotel_nom) {
        this.lieu_nom = hotel_nom;
    }

    public int getId_lieu() {
        return id_lieu;
    }

    public void setId_lieu(int id_lieu) {
        this.id_lieu = id_lieu;
    }

    public String getLieu_nom() {
        return lieu_nom;
    }

    public void setLieu_nom(String lieu_nom) {
        this.lieu_nom = lieu_nom;
    }
}
