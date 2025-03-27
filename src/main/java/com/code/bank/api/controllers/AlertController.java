package com.code.bank.api.controllers;


import com.code.bank.api.dtos.responses.Response;
import com.code.bank.api.dtos.responses.ResponseSuccess;
import com.code.bank.api.exceptions.DataNotFoundException;
import com.code.bank.models.enums.AlertStatus;
import com.code.bank.repositories.AlertRepository;
import com.code.bank.services.interfaces.AlertService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/alert")
@RequiredArgsConstructor
@SecurityRequirements({@SecurityRequirement(name = "bearerAuth")})
public class AlertController {

    private final AlertService alertService;

    @GetMapping("/pending")
    public Response getAllAlertNotYetProcessed() {
        return new ResponseSuccess<>(HttpStatus.OK.value(),
                "Get all alert not yet processed successfully",
                alertService.findAlertByStatus(AlertStatus.TRIGGERED));
    }

    @PutMapping("/{alertId}/resolve")
    public Response resolveAlert(@PathVariable Long alertId) throws DataNotFoundException {
        return new ResponseSuccess<>(HttpStatus.OK.value(),
                "alert resolved successfully",
                alertService.resolveAlert(alertId));
    }
}
