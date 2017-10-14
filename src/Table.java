package src;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class Table {
	private ArrayList<String> header = new ArrayList<String>();
	private Workbook book;
	private Sheet sheet;
	
	public Table() {
		this.book = new HSSFWorkbook();
		this.sheet = book.createSheet();
	}
	
	public void setHeader(Object[] header) {
		
		Row row = sheet.createRow(0);
		
		for (int i = 0; i < header.length; i++) {
			this.header.add((String) header[i]);
			
			Cell cell = row.createCell(i);
			cell.setCellValue((String) header[i]);
		}
	}
	
	public void fillRow(CompanyCard card) {
		Row row = sheet.createRow(sheet.getLastRowNum() + 1);
		
		for (Map.Entry<String, String> entry : card.getProperties().entrySet()) {
			Cell cell = row.createCell(this.header.indexOf(entry.getKey()));
			cell.setCellValue(entry.getValue());
		}
	}
	
	public void write(String name) {
		// TODO: handle a exception
		try {
			book.write(new FileOutputStream(name + ".xls"));
			book.close();
		} catch(java.io.IOException e) {
			
		}
	}
	
}