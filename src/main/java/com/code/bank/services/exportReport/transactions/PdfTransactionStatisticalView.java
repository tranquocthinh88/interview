package com.code.bank.services.exportReport.transactions;

import com.code.bank.api.dtos.responses.transaction.TransactionStatisticalResponse;
import com.code.bank.models.Transaction;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import java.awt.*;
import java.util.List;
import java.util.Map;

public class PdfTransactionStatisticalView extends AbstractPdfView {
    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
                                    HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=transactions.pdf");
        response.setHeader("Content-Transfer-Encoding", "binary");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        @SuppressWarnings("unchecked")
        TransactionStatisticalResponse data = (TransactionStatisticalResponse) model.get("data");
        List<Transaction> transactions = data.getTransactions();

        // Thêm tiêu đề cho báo cáo
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
        titleFont.setColor(Color.RED);
        Paragraph title = new Paragraph("Transaction Statistical Report", titleFont);
        title.setAlignment(Paragraph.ALIGN_CENTER);
        title.setSpacingAfter(10);
        document.add(title);

        // Load font hỗ trợ tiếng Việt
        BaseFont baseFont = BaseFont.createFont("src/main/resources/fonts/arial-unicode-ms.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font unicodeFont = new Font(baseFont, 12, Font.NORMAL);
        Font headFont = new Font(baseFont, 12, Font.BOLD);
        headFont.setColor(Color.WHITE);

        PdfPTable table = new PdfPTable(7);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10);
        table.setWidths(new float[]{2, 3.5f, 4, 4, 2.5f, 4, 4});

        PdfPCell hcell;
        String[] headers = {"ID", "Type", "Amount", "Date", "Fee", "Location", "Receiver Account"};
        for (String header : headers) {
            hcell = new PdfPCell(new Phrase(header, headFont));
            hcell.setBackgroundColor(Color.BLUE);
            hcell.setPadding(7);
            table.addCell(hcell);
        }

        for (Transaction transaction : transactions) {
            table.addCell(new PdfPCell(new Phrase(transaction.getId(), unicodeFont)));
            table.addCell(new PdfPCell(new Phrase(String.valueOf(transaction.getTransactionType()), unicodeFont)));
            table.addCell(new PdfPCell(new Phrase(String.valueOf(transaction.getAmount()), unicodeFont)));
            table.addCell(new PdfPCell(new Phrase(String.valueOf(transaction.getTransactionDate()), unicodeFont)));
            table.addCell(new PdfPCell(new Phrase(String.valueOf(transaction.getFee()), unicodeFont)));
            table.addCell(new PdfPCell(new Phrase(transaction.getLocation(), unicodeFont)));
            table.addCell(new PdfPCell(new Phrase(transaction.getReceiverAccount(), unicodeFont)));
        }
        document.add(table);
    }
}
