package umu.tds.controller;

import java.io.FileOutputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import umu.tds.model.User;

public class ExcelGen {
	public static void genExcel(User user, List<User> followers, String path) {
		try {
			// creating an instance of HSSFWorkbook class
			HSSFWorkbook workbook = new HSSFWorkbook();
			// invoking creatSheet() method and passing the name of the sheet to be created
			HSSFSheet sheet = workbook.createSheet("Usuario");
			// creating the 0th row using the createRow() method
			HSSFRow rowhead = sheet.createRow((short) 0);
			// creating cell by using the createCell() method and setting the values to the
			// cell by using the setCellValue() method
			rowhead.createCell(1).setCellValue("Nombre de usuario");
			rowhead.createCell(2).setCellValue("Email");
			rowhead.createCell(3).setCellValue("Descripción");

			if(!followers.isEmpty()) {
				int numUser = 1;
				for (User u : followers) {
					// creating the 1st row
					HSSFRow row = sheet.createRow((short) numUser);
					// inserting data in the first row
					row.createCell(1).setCellValue(u.getUsername());
					row.createCell(2).setCellValue(u.getEmail());
					row.createCell(3).setCellValue(u.getDescription());
					numUser++;
				}
			}

			FileOutputStream fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
			// closing the Stream
			fileOut.close();
			// closing the workbook
			workbook.close();
			// prints the message on the console
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
