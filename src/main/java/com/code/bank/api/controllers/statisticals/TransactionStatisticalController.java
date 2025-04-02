package com.code.bank.api.controllers.statisticals;

import com.code.bank.api.dtos.responses.Response;
import com.code.bank.api.dtos.responses.ResponseSuccess;
import com.code.bank.models.enums.SortDirection;
import com.code.bank.models.enums.TransactionType;
import com.code.bank.services.exportReport.transactions.ExcelTransactionStatisticalView;
import com.code.bank.services.exportReport.transactions.PdfTransactionStatisticalView;
import com.code.bank.services.interfaces.statisticals.TransactionStatisticalService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/statistical/transaction")
@RequiredArgsConstructor
@SecurityRequirements({@SecurityRequirement(name = "bearerAuth")})
public class TransactionStatisticalController {

    private final TransactionStatisticalService transactionStatisticalService;

    @GetMapping("/date")
    public Response findTransactionByDate(@RequestParam LocalDate date, TransactionType transactionType,
                                          @RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "5") int size,
                                          @RequestParam(defaultValue = "amount") String sortBy,
                                          @RequestParam(required = false) SortDirection sortDirection) {
        Pageable pageable;
        if (sortBy != null && sortDirection != null) {
            Sort sort = (sortDirection == SortDirection.DESC) ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
            pageable = PageRequest.of(page, size, sort);
        } else {
            pageable = PageRequest.of(page, size);
        }
        return new ResponseSuccess<>(HttpStatus.OK.value(),
                "Get transaction by date successfully",
                transactionStatisticalService.findTransactionByDate(date, transactionType, pageable));
    }


    @GetMapping("/week")
    public Response findTransactionByWeek(@RequestParam LocalDate startDate,@RequestParam LocalDate endDate,
                                          TransactionType transactionType,
                                          @RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "5") int size,
                                          @RequestParam(defaultValue = "amount") String sortBy,
                                          @RequestParam(required = false) SortDirection sortDirection) {
        Pageable pageable;
        if (sortBy != null && sortDirection != null) {
            Sort sort = (sortDirection == SortDirection.DESC) ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
            pageable = PageRequest.of(page, size, sort);
        } else {
            pageable = PageRequest.of(page, size);
        }
        return new ResponseSuccess<>(HttpStatus.OK.value(),
                "Get quantity transaction by week successfully",
                transactionStatisticalService.findTransactionByWeek(startDate, endDate, transactionType, pageable));
    }

    @GetMapping("/month")
    public Response findTransactionByMonth(@RequestParam int month, @RequestParam int year,
                                           TransactionType transactionType,
                                           @RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "5") int size,
                                           @RequestParam(defaultValue = "amount") String sortBy,
                                           @RequestParam(required = false) SortDirection sortDirection) {
        Pageable pageable;
        if (sortBy != null && sortDirection != null) {
            Sort sort = (sortDirection == SortDirection.DESC) ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
            pageable = PageRequest.of(page, size, sort);
        } else {
            pageable = PageRequest.of(page, size);
        }
        return new ResponseSuccess<>(HttpStatus.OK.value(),
                "Get quantity transaction by month successfully",
                transactionStatisticalService.findTransactionByMonth(month, year, transactionType, pageable));
    }

    @GetMapping("/quarter")
    public Response findTransactionByQuarter(@RequestParam int quarter,@RequestParam int year,
                                             TransactionType transactionType,
                                             @RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "5") int size,
                                             @RequestParam(defaultValue = "amount") String sortBy,
                                             @RequestParam(required = false) SortDirection sortDirection) {
        List<Integer> months = switch (quarter) {
            case 1 -> Arrays.asList(1, 2, 3);
            case 2 -> Arrays.asList(4, 5, 6);
            case 3 -> Arrays.asList(7, 8, 9);
            case 4 -> Arrays.asList(10, 11, 12);
            default -> throw new IllegalArgumentException("Quý không hợp lệ: " + quarter);
        };
        Pageable pageable;
        if (sortBy != null && sortDirection != null) {
            Sort sort = (sortDirection == SortDirection.DESC) ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
            pageable = PageRequest.of(page, size, sort);
        } else {
            pageable = PageRequest.of(page, size);
        }
        return new ResponseSuccess<>(HttpStatus.OK.value(),
                "Get quantity transaction by quarter successfully",
                transactionStatisticalService.findTransactionByQuarter(months, year, transactionType, pageable));
    }

    @GetMapping("/year")
    public Response findTransactionByYear(@RequestParam int year,
                                           TransactionType transactionType,
                                           @RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "5") int size,
                                          @RequestParam(defaultValue = "amount") String sortBy,
                                          @RequestParam(required = false) SortDirection sortDirection) {
        Pageable pageable;
        if (sortBy != null && sortDirection != null) {
            Sort sort = (sortDirection == SortDirection.DESC) ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
            pageable = PageRequest.of(page, size, sort);
        } else {
            pageable = PageRequest.of(page, size); // Không sắp xếp, giữ nguyên thứ tự mặc định từ DB
        }
        return new ResponseSuccess<>(HttpStatus.OK.value(),
                "Get quantity transaction by month successfully",
                transactionStatisticalService.findTransactionByYear(year, transactionType, pageable));
    }

    @GetMapping("/date/export-excel")
    public ModelAndView exportTransactionByDateExcel(@RequestParam LocalDate date, TransactionType transactionType,
                                                     @RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "5") int size){
        Pageable pageable = PageRequest.of(page, size);
        ModelAndView mav = new ModelAndView(new ExcelTransactionStatisticalView());
        mav.addObject("data", transactionStatisticalService.findTransactionByDate(date, transactionType, pageable));
        return mav;
    }

    @GetMapping("/week/export-excel")
    public ModelAndView exportTransactionByWeekExcel(@RequestParam LocalDate startDate,
                                                     @RequestParam LocalDate endDate,
                                                     TransactionType transactionType,
                                                     @RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "5") int size){
        Pageable pageable = PageRequest.of(page, size);
        ModelAndView mav = new ModelAndView(new ExcelTransactionStatisticalView());
        mav.addObject("data", transactionStatisticalService.findTransactionByWeek(startDate, endDate, transactionType, pageable));
        return mav;
    }

    @GetMapping("/month/export-excel")
    public ModelAndView exportTransactionByMonthExcel(@RequestParam int month, @RequestParam int year,
                                                      TransactionType transactionType,
                                                      @RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "5") int size){
        Pageable pageable = PageRequest.of(page, size);
        ModelAndView mav = new ModelAndView(new ExcelTransactionStatisticalView());
        mav.addObject("data", transactionStatisticalService.findTransactionByMonth(month, year, transactionType, pageable));
        return mav;
    }

    @GetMapping("/quarter/export-excel")
    public ModelAndView exportTransactionByQuarterExcel(@RequestParam int quarter,@RequestParam int year,
                                                        TransactionType transactionType,
                                                        @RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "5") int size){
        List<Integer> months = switch (quarter) {
            case 1 -> Arrays.asList(1, 2, 3);
            case 2 -> Arrays.asList(4, 5, 6);
            case 3 -> Arrays.asList(7, 8, 9);
            case 4 -> Arrays.asList(10, 11, 12);
            default -> throw new IllegalArgumentException("Quý không hợp lệ: " + quarter);
        };
        Pageable pageable = PageRequest.of(page, size);
        ModelAndView mav = new ModelAndView(new ExcelTransactionStatisticalView());
        mav.addObject("data", transactionStatisticalService.findTransactionByQuarter(months, year, transactionType, pageable));
        return mav;
    }

    @GetMapping("/year/export-excel")
    public ModelAndView exportTransactionByYearExcel(@RequestParam int year,
                                          TransactionType transactionType,
                                          @RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        ModelAndView mav = new ModelAndView(new ExcelTransactionStatisticalView());
        mav.addObject("data", transactionStatisticalService.findTransactionByYear(year, transactionType, pageable));
        return mav;
    }

    @GetMapping("/date/export-pdf")
    public ModelAndView exportTransactionByDatePdf(@RequestParam LocalDate date, TransactionType transactionType,
                                                     @RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "5") int size){
        Pageable pageable = PageRequest.of(page, size);
        ModelAndView mav = new ModelAndView(new PdfTransactionStatisticalView());
        mav.addObject("data", transactionStatisticalService.findTransactionByDate(date, transactionType, pageable));
        return mav;
    }

    @GetMapping("/week/export-pdf")
    public ModelAndView exportTransactionByWeekPdf(@RequestParam LocalDate startDate,
                                                     @RequestParam LocalDate endDate,
                                                     TransactionType transactionType,
                                                     @RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "5") int size){
        Pageable pageable = PageRequest.of(page, size);
        ModelAndView mav = new ModelAndView(new PdfTransactionStatisticalView());
        mav.addObject("data", transactionStatisticalService.findTransactionByWeek(startDate, endDate, transactionType, pageable));
        return mav;
    }

    @GetMapping("/month/export-pdf")
    public ModelAndView exportTransactionByMonthPdf(@RequestParam int month, @RequestParam int year,
                                                      TransactionType transactionType,
                                                      @RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "5") int size){
        Pageable pageable = PageRequest.of(page, size);
        ModelAndView mav = new ModelAndView(new PdfTransactionStatisticalView());
        mav.addObject("data", transactionStatisticalService.findTransactionByMonth(month, year, transactionType, pageable));
        return mav;
    }

    @GetMapping("/quarter/export-pdf")
    public ModelAndView exportTransactionByQuarterPdf(@RequestParam int quarter,@RequestParam int year,
                                                        TransactionType transactionType,
                                                        @RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "5") int size){
        List<Integer> months = switch (quarter) {
            case 1 -> Arrays.asList(1, 2, 3);
            case 2 -> Arrays.asList(4, 5, 6);
            case 3 -> Arrays.asList(7, 8, 9);
            case 4 -> Arrays.asList(10, 11, 12);
            default -> throw new IllegalArgumentException("Quý không hợp lệ: " + quarter);
        };
        Pageable pageable = PageRequest.of(page, size);
        ModelAndView mav = new ModelAndView(new PdfTransactionStatisticalView());
        mav.addObject("data", transactionStatisticalService.findTransactionByQuarter(months, year, transactionType, pageable));
        return mav;
    }

    @GetMapping("/year/export-pdf")
    public ModelAndView exportTransactionByYearPdf(@RequestParam int year,
                                                     TransactionType transactionType,
                                                     @RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        ModelAndView mav = new ModelAndView(new PdfTransactionStatisticalView());
        mav.addObject("data", transactionStatisticalService.findTransactionByYear(year, transactionType, pageable));
        return mav;
    }
}
