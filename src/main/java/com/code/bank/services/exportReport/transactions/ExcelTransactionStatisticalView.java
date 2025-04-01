package com.code.bank.services.exportReport.transactions;

import com.code.bank.api.dtos.responses.transaction.TransactionStatisticalResponse;
import com.code.bank.models.Transaction;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import java.util.List;
import java.util.Map;

public class ExcelTransactionStatisticalView extends AbstractXlsxView {
    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook,
                                      HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=transactions.xlsx");

        @SuppressWarnings("unchecked")
        TransactionStatisticalResponse data = (TransactionStatisticalResponse) model.get("data");
        List<Transaction> transactions = data.getTransactions();

        Sheet sheet = workbook.createSheet("Transactions");
        Row header = sheet.createRow(0);

        String[] columns = {"ID", "Sender Account", "Sender Name", "Type", "Amount", "Date", "Fee", "Location", "Receiver Account", "Receiver Name"};
        for (int i = 0; i < columns.length; i++) {
            header.createCell(i).setCellValue(columns[i]);
        }

        int rowNum = 1;
        for (Transaction transaction : transactions) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(transaction.getId());
            row.createCell(1).setCellValue(transaction.getAccount().getAccountNumber());
            row.createCell(2).setCellValue(transaction.getAccount().getCustomer().getFullName());
            row.createCell(3).setCellValue(transaction.getTransactionType().toString());
            row.createCell(4).setCellValue(transaction.getAmount());
            row.createCell(5).setCellValue(transaction.getTransactionDate().toString());
            row.createCell(6).setCellValue(transaction.getFee());
            row.createCell(7).setCellValue(transaction.getLocation());
            row.createCell(8).setCellValue(transaction.getReceiverAccountNumber());
            row.createCell(9).setCellValue(transaction.getReceiverName());
        }
    }
}
