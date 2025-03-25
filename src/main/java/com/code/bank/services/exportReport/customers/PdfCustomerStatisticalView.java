//package com.code.bank.services.exportReport.customers;
//
//import com.code.bank.api.dtos.responses.customer.CustomerStatisticalResponse;
//import com.code.bank.models.Customer;
//import com.lowagie.text.Document;
//import com.lowagie.text.Font;
//import com.lowagie.text.FontFactory;
//import com.lowagie.text.Phrase;
//import com.lowagie.text.pdf.PdfPCell;
//import com.lowagie.text.pdf.PdfPTable;
//import com.lowagie.text.pdf.PdfWriter;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.web.servlet.view.document.AbstractPdfView;
//
//import java.awt.*;
//import java.util.List;
//import java.util.Map;
//
//public class PdfCustomerStatisticalView extends AbstractPdfView {
//    @Override
//    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
//                                    HttpServletRequest request, HttpServletResponse response) throws Exception {
//        response.setContentType("application/pdf");
//        response.setHeader("Content-Disposition", "attachment; filename=\"customer_statistical.pdf\"");
//        response.setHeader("Content-Transfer-Encoding", "binary");
//        response.setHeader("Pragma", "public");
//        response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
//        response.setHeader("Expires", "0");
//
//        @SuppressWarnings("unchecked")
//        CustomerStatisticalResponse data = (CustomerStatisticalResponse) model.get("data");
//        List<Customer> customers = data.getCustomers();
//
//        PdfPTable table = new PdfPTable(8);
//        table.setWidthPercentage(100);
//        table.setSpacingBefore(10);
//        table.setWidths(new float[]{1.5f, 3, 3, 2, 4, 8, 4, 4});
//
//        Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
//        headFont.setColor(Color.WHITE);
//        PdfPCell hcell;
//
//        String[] headers = {"ID", "Full Name","Phone","Email", "Gender","Temporary Address","CCCD", "Birth Date"};
//        for (String header : headers) {
//            hcell = new PdfPCell(new Phrase(header, headFont));
//            hcell.setBackgroundColor(Color.BLUE);
//            hcell.setPadding(8);
//            table.addCell(hcell);
//        }
//
//        for (Customer customer : customers) {
//            table.addCell(String.valueOf(customer.getId()));
//            table.addCell(String.valueOf(customer.getFullName()));
//            table.addCell(String.valueOf(customer.getPhone()));
//            table.addCell(String.valueOf(customer.getEmail()));
//            table.addCell(String.valueOf(customer.getGender()));
//            table.addCell(String.valueOf(customer.getTemporaryAddress()));
//            table.addCell(String.valueOf(customer.getIdCard()));
//            table.addCell(String.valueOf(customer.getDateOfBirth()));
//        }
//        document.add(table);
//    }
//}
package com.code.bank.services.exportReport.customers;

import com.code.bank.api.dtos.responses.customer.CustomerStatisticalResponse;
import com.code.bank.models.Customer;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import java.awt.*;
import java.util.List;
import java.util.Map;

public class PdfCustomerStatisticalView extends AbstractPdfView {
    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
                                    HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"customer_statistical.pdf\"");
        response.setHeader("Content-Transfer-Encoding", "binary");
        response.setHeader("Pragma", "public");
        response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Expires", "0");

        @SuppressWarnings("unchecked")
        CustomerStatisticalResponse data = (CustomerStatisticalResponse) model.get("data");
        List<Customer> customers = data.getCustomers();

        // Thêm tiêu đề cho báo cáo
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
        titleFont.setColor(Color.RED);
        Paragraph title = new Paragraph("Customer Statistical Report", titleFont);
        title.setAlignment(Paragraph.ALIGN_CENTER);
        title.setSpacingAfter(10);
        document.add(title);

        // Load font hỗ trợ tiếng Việt
        BaseFont baseFont = BaseFont.createFont("src/main/resources/fonts/arial-unicode-ms.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font unicodeFont = new Font(baseFont, 12, Font.NORMAL);
        Font headFont = new Font(baseFont, 12, Font.BOLD);
        headFont.setColor(Color.WHITE);

        PdfPTable table = new PdfPTable(8);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10);
        table.setWidths(new float[]{2f, 3, 3.5f, 4, 4, 8, 4, 4});

        PdfPCell hcell;
        String[] headers = {"ID", "Full Name", "Phone", "Email", "Gender", "Temporary Address", "CCCD", "Birth Date"};
        for (String header : headers) {
            hcell = new PdfPCell(new Phrase(header, headFont));
            hcell.setBackgroundColor(Color.BLUE);
            hcell.setPadding(8);
            table.addCell(hcell);
        }

        for (Customer customer : customers) {
            table.addCell(new PdfPCell(new Phrase(String.valueOf(customer.getId()), unicodeFont)));
            table.addCell(new PdfPCell(new Phrase(customer.getFullName(), unicodeFont)));
            table.addCell(new PdfPCell(new Phrase(customer.getPhone(), unicodeFont)));
            table.addCell(new PdfPCell(new Phrase(customer.getEmail(), unicodeFont)));
            table.addCell(new PdfPCell(new Phrase(String.valueOf(customer.getGender()), unicodeFont)));
            table.addCell(new PdfPCell(new Phrase(customer.getTemporaryAddress(), unicodeFont)));
            table.addCell(new PdfPCell(new Phrase(customer.getIdCard(), unicodeFont)));
            table.addCell(new PdfPCell(new Phrase(String.valueOf(customer.getDateOfBirth()), unicodeFont)));
        }
        document.add(table);
    }
}

