package com.frontoffice;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReservationsController {

    private final BackofficeReservationsClient client;

    public ReservationsController(BackofficeReservationsClient client) {
        this.client = client;
    }

    @GetMapping("/reservations")
    public String list(Model model) {
        List<ReservationRowDto> reservations = client.listReservations();
        model.addAttribute("reservations", reservations);
        return "reservations";
    }
}
