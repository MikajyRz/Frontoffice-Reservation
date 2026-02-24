package com.frontoffice.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.frontoffice.client.BackofficeReservationsClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AssignationController {

    private final BackofficeReservationsClient client;

    public AssignationController(BackofficeReservationsClient client) {
        this.client = client;
    }

    @GetMapping("/assignation/form")
    public String form() {
        return "assignationForm";
    }

    @PostMapping("/assignation/plan")
    public String plan(@RequestParam("date") String date, Model model) {
        try {
            JsonNode result = client.planDate(date);
            model.addAttribute("date", date);
            if (result != null) {
                model.addAttribute("assigned", result.get("assigned"));
                model.addAttribute("unassigned", result.get("unassigned"));
            }
            return "assignationResult";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "assignationForm";
        }
    }
}
