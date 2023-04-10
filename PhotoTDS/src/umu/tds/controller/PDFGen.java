package umu.tds.controller;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import umu.tds.model.User;

public class PDFGen {
	public static void genPDF(User user, List<User> followers, String path) {

		Document doc = new Document();
		try {
			PdfWriter.getInstance(doc, new FileOutputStream(path));
		} catch (FileNotFoundException | DocumentException e) {
			e.printStackTrace();
		}
		doc.open();
		PdfPTable tabla = new PdfPTable(4);
		tabla.addCell("Número");
		tabla.addCell("Usuario");
		tabla.addCell("Email");
		tabla.addCell("Descripción");

		if (!followers.isEmpty()) {
			int numUser = 1;
			for (User u : followers) {
				tabla.addCell(Integer.toString(numUser));
				tabla.addCell(u.getUsername());
				tabla.addCell(u.getEmail());
				tabla.addCell(u.getDescription());
				numUser++;
			}
		}

		try {
			doc.add(tabla);
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		doc.close();

	}
}
