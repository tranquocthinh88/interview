package com.code.bank.services.exportReport.accounts;

import com.code.bank.api.dtos.responses.account.AccountStatisticalResponse;
import com.code.bank.models.Account;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public class ExcelAccountStatisticalView extends AbstractXlsxView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook,
                                      HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        response.setContentType("application/vnd.ms-excel");
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=\"account_statistical.xlsx\"");

        @SuppressWarnings("unchecked")
        AccountStatisticalResponse data = (AccountStatisticalResponse) model.get("data");
        List<Account> accounts = data.getAccounts();

        Sheet sheet = workbook.createSheet("Accounts");
        Row header = sheet.createRow(0);

        String[] columns = {"ID", "Account Number", "Balance(VND)", "Status", "Open Date", "Customer ID"};
        for (int i = 0; i < columns.length; i++) {
            header.createCell(i).setCellValue(columns[i]);
        }

        int rowNum = 1;
        for (Account account : accounts) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(account.getId());
            row.createCell(1).setCellValue(account.getAccountNumber());
            row.createCell(2).setCellValue(account.getBalance());
            row.createCell(3).setCellValue(account.getAccountStatus().name());
            row.createCell(4).setCellValue(account.getOpenDate().toString());
            row.createCell(5).setCellValue(account.getCustomer().getId());
        }
    }
}