package com.code.bank.services.exportReport.accounts;

import com.code.bank.api.dtos.responses.account.AccountStatisticalResponse;
import com.code.bank.models.Account;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class PdfAccountStatisticalView extends AbstractPdfView {

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document,
                                    PdfWriter writer, HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"account_statistical.pdf\"");
        response.setHeader("Content-Transfer-Encoding", "binary");
        response.setHeader("Pragma", "public");
        response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Expires", "0");


        @SuppressWarnings("unchecked")
        AccountStatisticalResponse data = (AccountStatisticalResponse) model.get("data");
        List<Account> accounts = data.getAccounts();

        // Thêm tiêu đề cho báo cáo
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
        titleFont.setColor(Color.RED);
        Paragraph title = new Paragraph("Account Statistical Report", titleFont);
        title.setAlignment(Paragraph.ALIGN_CENTER);
        title.setSpacingAfter(10);
        document.add(title);

        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10);
        table.setWidths(new float[]{1.5f, 3, 3.5f, 2, 5, 3});

        Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        headFont.setColor(Color.WHITE);
        PdfPCell hcell;

        String[] headers = {"ID", "Account Number", "Balance(VND)", "Status", "Open Date", "Customer ID"};
        for (String header : headers) {
            hcell = new PdfPCell(new Phrase(header, headFont));
            hcell.setBackgroundColor(Color.BLUE);
            hcell.setPadding(6);
            table.addCell(hcell);
        }

        for (Account account : accounts) {
            table.addCell(String.valueOf(account.getId()));
            table.addCell(account.getAccountNumber());
            table.addCell(String.valueOf(account.getBalance()));
            table.addCell(account.getAccountStatus().name());
            table.addCell(account.getOpenDate().toString());
            table.addCell(String.valueOf(account.getCustomer().getId()));
        }

        document.add(table);
    }
}