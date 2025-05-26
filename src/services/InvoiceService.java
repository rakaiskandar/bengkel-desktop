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
import models.ServiceDetail;
import models.ServiceRecord;
import models.SparePart;
import models.Vehicle;
import java.util.List;
import models.Motorcycle;

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

    public static void generateInvoicePDF(Invoice invoice, Vehicle vehicle,
            ServiceRecord serviceRecord, List<ServiceDetail> serviceDetails, List<SparePart> spareParts, String outputPath) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(outputPath));
            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Font boldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
            Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 12);

            Paragraph title = new Paragraph("INVOICE", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph(" "));

            // Informasi Invoice
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            document.add(new Paragraph("Invoice ID   : " + invoice.getId(), normalFont));
            document.add(new Paragraph("Service ID   : " + invoice.getServiceId(), normalFont));
            document.add(new Paragraph("Tanggal      : " + sdf.format(invoice.getDate()), normalFont));
            document.add(new Paragraph("Total Biaya  : Rp " + String.format("%,.2f", invoice.getTotal()), normalFont));

            document.add(new Paragraph("\nInformasi Kendaraan:", boldFont));
            document.add(new Paragraph("Tipe         : " + (vehicle instanceof Motorcycle ? "Motorcycle" : "Car"), normalFont));
            document.add(new Paragraph("Model        : " + vehicle.getModel(), normalFont));
            document.add(new Paragraph("Plat Nomor   : " + vehicle.getLicensePlate(), normalFont));

            document.add(new Paragraph("\nDetail Servis:", boldFont));
            for (ServiceDetail detail : serviceDetails) {
                document.add(new Paragraph("- " + detail.getType() + ": " + detail.getDescription(), normalFont));
            }

            document.add(new Paragraph("\nSpare Part yang Digunakan:", boldFont));

            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            table.setSpacingBefore(5f);
            table.addCell(new PdfPCell(new Phrase("Nama Spare Part", boldFont)));
            table.addCell(new PdfPCell(new Phrase("Harga (Rp)", boldFont)));

            for (SparePart sp : spareParts) {
                table.addCell(sp.getName());
                table.addCell("Rp " + String.format("%,.2f", sp.getPrice()));
            }

            document.add(table);

            document.add(new Paragraph("\nTerima kasih telah mempercayakan servis kepada kami.", normalFont));
            document.close();

            System.out.println("Invoice berhasil dicetak ke: " + outputPath);
        } catch (Exception e) {
            System.out.println("Gagal mencetak invoice: " + e.getMessage());
        }
    }
}

