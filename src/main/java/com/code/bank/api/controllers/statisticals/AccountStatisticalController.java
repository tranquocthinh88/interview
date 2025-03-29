package com.code.bank.api.controllers.statisticals;

import com.code.bank.api.dtos.responses.Response;
import com.code.bank.api.dtos.responses.ResponseSuccess;
import com.code.bank.services.exportReport.accounts.ExcelAccountStatisticalView;
import com.code.bank.services.exportReport.accounts.PdfAccountStatisticalView;
import com.code.bank.services.interfaces.statisticals.AccountStatisticalService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/api/v1/statistical/accounts")
@RequiredArgsConstructor
@SecurityRequirements({@SecurityRequirement(name = "bearerAuth")})
public class AccountStatisticalController {

    private final AccountStatisticalService accountStatisticalService;


    @GetMapping("/balance-category")
    public Response getBalanceCategory(@RequestParam String category, @RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return new ResponseSuccess<>(HttpStatus.OK.value(),
                "get balance by category successfully",
                accountStatisticalService.getAccountsByBalanceCategory(category, pageable));
    }

    @GetMapping("/export-excel")
    public ModelAndView exportExcel(@RequestParam String category,
                                    @RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        ModelAndView mav = new ModelAndView(new ExcelAccountStatisticalView());
        mav.addObject("data", accountStatisticalService.getAccountsByBalanceCategory(category, pageable));
        return mav;
    }

    @GetMapping("/export-pdf")
    public ModelAndView exportPdf(@RequestParam String category,
                                  @RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        ModelAndView mav = new ModelAndView(new PdfAccountStatisticalView());
        mav.addObject("data", accountStatisticalService.getAccountsByBalanceCategory(category, pageable));
        return mav;
    }

}
