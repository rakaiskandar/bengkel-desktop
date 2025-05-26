/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import utils.Database;
import models.Invoice;

/**
 *
 * @author HP
 */
public class InvoiceService {

    private final Database db;
    
    public InvoiceService() {
        db = new Database();
    }

    public boolean save(Invoice invoice) {
        String sql = "INSERT INTO invoices (service_id, total, date) VALUES (?, ?, ?)";
        try {
            int rows = db.executeUpdate(sql,
                    invoice.getServiceId(),
                    invoice.getTotal(),
                    new java.sql.Date(invoice.getDate().getTime()));
            return rows > 0;
        } catch (Exception e) {
            System.out.println("Gagal menyimpan invoice: " + e.getMessage());
            return false;
        }
    }

    public static void generateInvoicePDF(Invoice invoice, String outputPath) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(outputPath));
            document.open();

            // Font untuk judul
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 12);

            // Judul
            Paragraph title = new Paragraph("INVOICE", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            document.add(new Paragraph(" ")); // Spacer

            // Informasi Invoice
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            document.add(new Paragraph("Invoice ID : " + invoice.getId(), normalFont));
            document.add(new Paragraph("Service ID : " + invoice.getServiceId(), normalFont));
            document.add(new Paragraph("Tanggal     : " + sdf.format(invoice.getDate()), normalFont));
            document.add(new Paragraph("Total       : Rp " + String.format("%,.2f", invoice.getTotal()), normalFont));

            document.add(new Paragraph("\nTerima kasih telah menggunakan layanan kami.", normalFont));

            document.close();
            System.out.println("Invoice berhasil dicetak ke: " + outputPath);
        } catch (Exception e) {
            System.out.println("Gagal mencetak invoice: " + e.getMessage());
        }
    }

}


