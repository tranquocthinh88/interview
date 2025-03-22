package com.code.bank.api.statisticals;

import com.code.bank.api.dtos.responses.Response;
import com.code.bank.api.dtos.responses.ResponseSuccess;
import com.code.bank.models.enums.TransactionType;
import com.code.bank.services.interfaces.statisticals.TransactionStatisticalService;
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

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/statistical/transaction")
@RequiredArgsConstructor
@SecurityRequirements({@SecurityRequirement(name = "bearerAuth")})
public class TransactionStatistical {

    private final TransactionStatisticalService transactionStatisticalService;

    @GetMapping("/date")
    public Response findTransactionByDate(@RequestParam LocalDate date, TransactionType transactionType,
                                          @RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return new ResponseSuccess<>(HttpStatus.OK.value(),
                "Get transaction by date successfully",
                transactionStatisticalService.findTransactionByDate(date, transactionType, pageable));
    }


    @GetMapping("/week")
    public Response findTransactionByWeek(@RequestParam LocalDate startDate,@RequestParam LocalDate endDate,
                                          TransactionType transactionType,
                                          @RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return new ResponseSuccess<>(HttpStatus.OK.value(),
                "Get quantity transaction by week successfully",
                transactionStatisticalService.findTransactionByWeek(startDate, endDate, transactionType, pageable));
    }

    @GetMapping("/month")
    public Response findTransactionByMonth(@RequestParam int month, @RequestParam int year,
                                           TransactionType transactionType,
                                           @RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return new ResponseSuccess<>(HttpStatus.OK.value(),
                "Get quantity transaction by month successfully",
                transactionStatisticalService.findTransactionByMonth(month, year, transactionType, pageable));
    }

    @GetMapping("/quarter")
    public Response findTransactionByQuarter(@RequestParam int quarter,@RequestParam int year,
                                             TransactionType transactionType,
                                             @RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "5") int size) {
        List<Integer> months = switch (quarter) {
            case 1 -> Arrays.asList(1, 2, 3);
            case 2 -> Arrays.asList(4, 5, 6);
            case 3 -> Arrays.asList(7, 8, 9);
            case 4 -> Arrays.asList(10, 11, 12);
            default -> throw new IllegalArgumentException("Quý không hợp lệ: " + quarter);
        };
        Pageable pageable = PageRequest.of(page, size);
        return new ResponseSuccess<>(HttpStatus.OK.value(),
                "Get quantity transaction by quarter successfully",
                transactionStatisticalService.findTransactionByQuarter(months, year, transactionType, pageable));
    }
}
