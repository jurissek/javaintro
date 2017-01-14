package sda.code.intermediate.part4.answers.goldretrofit;

import static sda.code.intermediate.part4.answers.goldretrofit.Messages.FAILED_SAVING_SUMMARY;
import static sda.code.intermediate.part4.answers.goldretrofit.Messages.SUCCESSFUL_SUMMARY_SAVE;
import static sda.code.intermediate.part4.answers.goldretrofit.Messages.WARNING_NOTICE;
import static sda.code.intermediate.part4.answers.goldretrofit.Messages.fmt;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import sda.code.intermediate.part4.answers.goldmodel.GoldPrice;

public class SummaryWriter {

	private File summaryFile;

	public SummaryWriter(String summaryLocation) {
		this.summaryFile = new File(summaryLocation);
	}

	public void write(List<GoldPrice> prices, String recommendation) {
		try (Workbook wb = new HSSFWorkbook(); OutputStream sout = new FileOutputStream(summaryFile)) {
			Sheet sheet = wb.createSheet();
			fillSheet(sheet, prices, recommendation);
			wb.write(sout);
			System.out.println(fmt(SUCCESSFUL_SUMMARY_SAVE, summaryFile.getAbsolutePath()));
		} catch (IOException e) {
			System.err.println(fmt(FAILED_SAVING_SUMMARY, e.getMessage()));
		}
	}

	private void fillSheet(Sheet sheet, List<GoldPrice> prices, String recommendation) {
		fillPrices(sheet, prices);

		Cell avg = sheet.getRow(0).createCell(3);
		Cell recom = sheet.getRow(0).createCell(4);
		Cell notice = sheet.getRow(2).createCell(3);

		avg.setCellFormula("AVERAGE(B1:B" + prices.size() + ")");
		recom.setCellValue(recommendation);
		notice.setCellValue(WARNING_NOTICE);
	}

	private void fillPrices(Sheet sheet, List<GoldPrice> prices) {
		int rownum = 0;
		for (GoldPrice price : prices) {
			fillSinglePrice(sheet, rownum++, price);
		}
	}

	private void fillSinglePrice(Sheet sheet, int rownum, GoldPrice price) {
		Row row = sheet.createRow(rownum);
		Cell date = row.createCell(0);
		date.setCellValue(toDate(price.getDate()));
		Cell value = row.createCell(1);
		value.setCellValue(price.getPrice());
	}

	private static Date toDate(LocalDate localDate) {
		return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}

}