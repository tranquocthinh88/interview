package com.code.bank.api.statisticals;

import com.code.bank.api.dtos.responses.Response;
import com.code.bank.api.dtos.responses.ResponseSuccess;
import com.code.bank.services.interfaces.statisticals.CustomerStatisticalService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/statistical/customers")
@RequiredArgsConstructor
@SecurityRequirements({@SecurityRequirement(name = "bearerAuth")})
public class CustomerStatistical {

    private final CustomerStatisticalService customerStatisticalService;

    @GetMapping("/location")
    public Response getCustomers(@RequestParam String location) {
        return new ResponseSuccess<>(HttpStatus.OK.value(),
                "Get customer by location successfully",
                customerStatisticalService.getCustomerCountByLocation(location));
    }
}
