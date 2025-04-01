package com.code.bank.services.exportReport.customers;

import com.code.bank.api.dtos.responses.customer.CustomerStatisticalResponse;
import com.code.bank.models.Customer;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import java.util.List;
import java.util.Map;

public class ExcelCustomerStatisticalView extends AbstractXlsxView {
    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook,
                                      HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("application/vnd.ms-excel");
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=\"customer_statistical.xlsx\"");

        @SuppressWarnings("unchecked")
        CustomerStatisticalResponse data = (CustomerStatisticalResponse) model.get("data");
        List<Customer> customers = data.getCustomers();

        Sheet sheet = workbook.createSheet("Customer Statistical");
        Row header = sheet.createRow(0);

        String[] columns = {"ID", "Full Name","Phone","Email", "Gender","Temporary Address","CCCD", "Birth Date"};
        for (int i = 0; i < columns.length; i++) {
            header.createCell(i).setCellValue(columns[i]);
        }

        int rowNum = 1;
        for (Customer customer : customers) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(customer.getId());
            row.createCell(1).setCellValue(customer.getFullName());
            row.createCell(2).setCellValue(customer.getPhone());
            row.createCell(3).setCellValue(customer.getEmail());
            row.createCell(4).setCellValue(customer.getGender().toString());
            row.createCell(5).setCellValue(customer.getTemporaryAddress());
            row.createCell(6).setCellValue(customer.getIdCard());
            row.createCell(7).setCellValue(customer.getDateOfBirth().toString());
        }
    }
}
