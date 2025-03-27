package com.code.bank.api.controllers.statisticals;

import com.code.bank.api.dtos.responses.Response;
import com.code.bank.api.dtos.responses.ResponseSuccess;
import com.code.bank.services.exportReport.customers.ExcelCustomerStatisticalView;
import com.code.bank.services.exportReport.customers.PdfCustomerStatisticalView;
import com.code.bank.services.interfaces.statisticals.CustomerStatisticalService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/api/v1/statistical/customers")
@RequiredArgsConstructor
@SecurityRequirements({@SecurityRequirement(name = "bearerAuth")})
public class CustomerStatistical {

    private final CustomerStatisticalService customerStatisticalService;

    @GetMapping("/location")
    public Response getCustomers(@RequestParam String location, @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return new ResponseSuccess<>(HttpStatus.OK.value(),
                "Get customer by location successfully",
                customerStatisticalService.getCustomerCountByLocation(location, pageable));
    }

    @GetMapping("/export-excel")
    public ModelAndView exportExcel(@RequestParam String location,
                                    @RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        ModelAndView mav = new ModelAndView(new ExcelCustomerStatisticalView());
        mav.addObject("data", customerStatisticalService.getCustomerCountByLocation(location, pageable));
        return mav;
    }

    @GetMapping("/export-pdf")
    public ModelAndView exportPdf(@RequestParam String location,
                                    @RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        ModelAndView mav = new ModelAndView(new PdfCustomerStatisticalView());
        mav.addObject("data", customerStatisticalService.getCustomerCountByLocation(location, pageable));
        return mav;
    }
}
