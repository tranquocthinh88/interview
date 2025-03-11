package com.code.bank.api.statisticals;

import com.code.bank.api.dtos.responses.Response;
import com.code.bank.api.dtos.responses.ResponseSuccess;
import com.code.bank.models.enums.TransactionType;
import com.code.bank.services.interfaces.statisticals.TransactionStatisticalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/statistical/transaction")
@RequiredArgsConstructor
public class TransactionStatistical {

    private final TransactionStatisticalService transactionStatisticalService;

    @GetMapping("/date")
    public Response countTransactionByDate(@RequestParam LocalDateTime date, TransactionType transactionType) {
        return new ResponseSuccess<>(HttpStatus.OK.value(),
                "Get quantity transaction by date successfully",
                transactionStatisticalService.countTransactionByDay(date, transactionType));
    }

    @GetMapping("/week")
    public Response countTransactionByWeek(@RequestParam LocalDateTime startDate,@RequestParam LocalDateTime endDate, TransactionType transactionType) {
        return new ResponseSuccess<>(HttpStatus.OK.value(),
                "Get quantity transaction by week successfully",
                transactionStatisticalService.countTransactionByWeek(startDate, endDate, transactionType));
    }

    @GetMapping("/month")
    public Response countTransactionByMonth(@RequestParam int month, @RequestParam int year, TransactionType transactionType) {
        return new ResponseSuccess<>(HttpStatus.OK.value(),
                "Get quantity transaction by month successfully",
                transactionStatisticalService.countTransactionByMonth(month, year, transactionType));
    }

    @GetMapping("/quarter")
    public Response countTransactionByQuarter(@RequestParam int quarter,@RequestParam int year, TransactionType transactionType) {
        List<Integer> months = switch (quarter) {
            case 1 -> Arrays.asList(1, 2, 3);
            case 2 -> Arrays.asList(4, 5, 6);
            case 3 -> Arrays.asList(7, 8, 9);
            case 4 -> Arrays.asList(10, 11, 12);
            default -> throw new IllegalArgumentException("Quý không hợp lệ: " + quarter);
        };
        return new ResponseSuccess<>(HttpStatus.OK.value(),
                "Get quantity transaction by quarter successfully",
                transactionStatisticalService.countTransactionByQuarter(months, year, transactionType));
    }
}
