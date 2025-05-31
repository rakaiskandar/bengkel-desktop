/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Stream;
import models.Car;
import models.Motorcycle;
import models.ServiceDetail;
import models.ServiceRecord;
import models.SparePart;
import models.Vehicle;

/**
 *
 * @author HP
 */
public class InvoiceGenerator {

    public static void generateInvoicePDF(
            String invoiceId,
            ServiceRecord serviceRecord,
            Vehicle vehicle,
            List<ServiceDetail> serviceDetails,
            List<SparePart> spareParts,
            String outputPath) {

        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(outputPath));
            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Font boldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
            Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 12);

            // Judul
            Paragraph title = new Paragraph("INVOICE", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph(" "));

            // Info Invoice pakai invoiceId acak
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            document.add(new Paragraph("Invoice ID   : " + invoiceId, normalFont));
            document.add(new Paragraph("Service ID   : " + serviceRecord.getId(), normalFont));
            document.add(new Paragraph("Tanggal      : " + sdf.format(new java.util.Date()), normalFont));
            document.add(new Paragraph("Total Biaya  : Rp "
                    + String.format("%,.2f", serviceRecord.getCost()),
                    normalFont));

            // Info Kendaraan
            document.add(new Paragraph("\nInformasi Kendaraan:", boldFont));
            String tipe = (vehicle instanceof models.Motorcycle)
                    ? "Motorcycle"
                    : "Car";
            
            Vehicle model;
            if (tipe.equals("Car")) {
                model = new Car(vehicle.getCustomerId(), vehicle.getModel(), vehicle.getLicensePlate());
            } else {
                model = new Motorcycle(vehicle.getCustomerId(), vehicle.getModel(), vehicle.getLicensePlate());
            }
            
            document.add(new Paragraph("Tipe         : " + tipe, normalFont));
            document.add(new Paragraph("Model        : " + model.printVehicleInfo(), normalFont));
            document.add(new Paragraph("Plat Nomor   : " + vehicle.getLicensePlate(), normalFont));

            // Detail Servis
            document.add(new Paragraph("\nDetail Servis:", boldFont));
            document.add(new Paragraph("Tipe Servis    : " + serviceRecord.getType(), normalFont));
            document.add(new Paragraph("Deskripsi      : " + serviceRecord.getDescription(), normalFont));

            boolean hasParts = serviceDetails.stream()
                    .anyMatch(d -> spareParts.stream()
                    .anyMatch(sp -> sp.getId() == d.getSparePartId()));

            if (!hasParts) {
                // Kasus: tidak ada spare part sama sekali
                Paragraph noParts = new Paragraph(
                        "\nTidak ada spare part yang digunakan.", normalFont
                );
                document.add(noParts);
            } else {
                // Kasus: ada spare part
                document.add(new Paragraph("\nSpare Part yang Digunakan:", boldFont));

                // Tabel 4 kolom: Nama, Qty, Harga, Sub Total
                PdfPTable table = new PdfPTable(4);
                table.setWidthPercentage(100);
                table.setSpacingBefore(5f);

                // Header
                Stream.of("Nama Spare Part", "Qty", "Harga (Rp)", "Sub Total")
                        .forEach(h -> {
                            PdfPCell cell = new PdfPCell(new Phrase(h, boldFont));
                            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            table.addCell(cell);
                        });

                // Data
                for (ServiceDetail d : serviceDetails) {
                    SparePart sp = spareParts.stream()
                            .filter(x -> x.getId() == d.getSparePartId())
                            .findFirst().orElse(null);
                    if (sp != null) {
                        double subTotal = sp.getPrice() * d.getQuantity();

                        table.addCell(new Phrase(sp.getName(), normalFont));

                        PdfPCell qtyCell = new PdfPCell(
                                new Phrase(String.valueOf(d.getQuantity()), normalFont)
                        );
                        qtyCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        table.addCell(qtyCell);

                        PdfPCell priceCell = new PdfPCell(
                                new Phrase(String.format("%,.2f", sp.getPrice()), normalFont)
                        );
                        priceCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        table.addCell(priceCell);

                        PdfPCell subCell = new PdfPCell(
                                new Phrase(String.format("%,.2f", subTotal), normalFont)
                        );
                        subCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        table.addCell(subCell);
                    }
                }

                document.add(table);
            }

            Paragraph thanks = new Paragraph("Terima kasih atas kepercayaannya.", boldFont);
            thanks.setAlignment(Element.ALIGN_CENTER);
            document.add(new Paragraph("\n"));  // spacer
            document.add(thanks);

        } catch (DocumentException | FileNotFoundException e) {
            System.err.println("Gagal mencetak invoice: " + e.getMessage());
        } finally {
            // Pastikan dokumen ditutup agar PDF valid
            if (document.isOpen()) {
                document.close();
                System.out.println("Invoice berhasil dicetak ke: " + outputPath);
            }
        }
    }
}
