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

    @GetMapping("/assignation/plan")
    public String plan(@RequestParam("date") String date, Model model) {
        try {
            JsonNode result = client.getPlan(date);
            model.addAttribute("date", date);
            if (result != null) {
                // Grouper les assignations par véhicule
                JsonNode assignedNode = result.get("assigned");
                java.util.Map<String, java.util.List<JsonNode>> assignedGrouped = new java.util.HashMap<>();
                if (assignedNode != null && assignedNode.isArray()) {
                    for (JsonNode trip : assignedNode) {
                        String immat = trip.get("vehicule").asText();
                        if (!assignedGrouped.containsKey(immat)) {
                            assignedGrouped.put(immat, new java.util.ArrayList<>());
                        }
                        assignedGrouped.get(immat).add(trip);
                    }
                }
                model.addAttribute("assignedGrouped", assignedGrouped);
                
                model.addAttribute("unassigned", result.get("unassigned"));
                model.addAttribute("unusedVehicles", result.get("unusedVehicles"));
            }
            return "assignationResult";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "assignationForm";
        }
    }
}
