/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.interestcalculator.controller;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.sire.interestcalculator.domain.InterestRate;
import java.io.FileOutputStream;
import javafx.collections.ObservableList;

/**
 *
 * @author balza
 */
public class PdfGeneration {

    public void pdfGeneration(String filename, ObservableList<InterestRate> rates) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(filename + ".pdf"));
            document.open();
            //logo
            Image image1 = Image.getInstance(getClass().getResource("/logo.jpg"));
            image1.scaleToFit(526, 256);
            image1.setAbsolutePosition(35f, 550f);
            document.add(image1);

            document.add(new Paragraph("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n", FontFactory.getFont("betutipus", BaseFont.IDENTITY_H, BaseFont.EMBEDDED)));
            //Táblázat
            float[] columnWidths = {1, 3, 4};
            PdfPTable table = new PdfPTable(columnWidths);
            table.setWidthPercentage(100);
            PdfPCell cell = new PdfPCell(new Phrase("Kamatok"));
            cell.setBackgroundColor(BaseColor.WHITE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setColspan(3);
            table.addCell(cell);

            table.getDefaultCell().setBackgroundColor(BaseColor.WHITE);
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell("Sorszám");
            table.addCell("Dátum");
            table.addCell("Kamat");

            table.setHeaderRows(1);

            for (int i = 0; i < rates.size(); i++) {
                table.addCell(String.valueOf(i + 1));
                InterestRate actualRate = rates.get(i);
                table.addCell(actualRate.getRateDate());
                table.addCell(actualRate.getRate());
            }
            document.add(table);
            
            Chunk signature = new Chunk("\n\n Generálva a kamatkalkulátor alkalmazás segítségével.");
            Paragraph base = new Paragraph(signature);
            document.add(base);

            document.close();
        } catch (Exception e) {
        }
    }

}
