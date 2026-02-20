package com.frontoffice.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.frontoffice.client.BackofficeReservationsClient;
import com.frontoffice.dto.ReservationRowDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ReservationsController {

    private final BackofficeReservationsClient client;

    public ReservationsController(BackofficeReservationsClient client) {
        this.client = client;
    }

    @GetMapping("/reservations")
    public String list(
            @RequestParam(name = "from", required = false) String from,
            @RequestParam(name = "to", required = false) String to,
            Model model) {

        List<ReservationRowDto> reservations;
        try {
            reservations = client.listReservations();
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("reservations", List.of());
            model.addAttribute("from", from == null ? "" : from);
            model.addAttribute("to", to == null ? "" : to);
            return "reservations";
        }
        List<ReservationRowDto> filtered = new ArrayList<>();

        LocalDate fromDate = parseDate(from);
        LocalDate toDate = parseDate(to);

        for (ReservationRowDto r : reservations) {
            LocalDateTime ldt = parseDateTime(r.getDate_heure_arrive());
            if (ldt == null) {
                continue;
            }
            LocalDate d = ldt.toLocalDate();
            if (fromDate != null && d.isBefore(fromDate)) {
                continue;
            }
            if (toDate != null && d.isAfter(toDate)) {
                continue;
            }
            filtered.add(r);
        }

        model.addAttribute("reservations", filtered);
        model.addAttribute("from", from == null ? "" : from);
        model.addAttribute("to", to == null ? "" : to);
        return "reservations";
    }

    private static LocalDate parseDate(String s) {
        if (s == null || s.isBlank()) {
            return null;
        }
        try {
            return LocalDate.parse(s);
        } catch (Exception e) {
            return null;
        }
    }

    private static LocalDateTime parseDateTime(String s) {
        if (s == null || s.isBlank()) {
            return null;
        }
        try {
            return LocalDateTime.parse(s);
        } catch (Exception e) {
            return null;
        }
    }
}
