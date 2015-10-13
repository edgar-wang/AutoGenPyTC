package com.htc.autotest.excel;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.htc.autotest.Constants;
import com.htc.autotest.python.TestCase;
import com.htc.autotest.python.TestComponent;
import com.htc.autotest.util.Logger;

public class ExcelHandler {
	
	public static ArrayList<TestComponent> parsingExcel(String inputExcelPath) {
		Workbook workbook = null;
		try {
			if (inputExcelPath.length() > 0 && inputExcelPath.endsWith(".xls")) {
				workbook = new HSSFWorkbook(new FileInputStream(inputExcelPath));
			} else {
				workbook = new XSSFWorkbook(new FileInputStream(inputExcelPath));
			}
		} catch (Exception e) {
			Logger.e(e);
			return null;
		}
		
		ArrayList<TestComponent> list = new ArrayList<TestComponent>();
		
		//every sheet is a component
		for(Sheet sheet: workbook){
			//1st Row is title
			//Get Component Name
			String component = sheet.getRow(1).getCell(0).toString().trim();
			TestComponent testPlan = new TestComponent(component);
			Row row;
			String descript_head = "";
			int count = 0;
			for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
				     row = sheet.getRow(i); // get ith Row
				     String title = row.getCell(Constants.XLS_TITLE).toString().trim();
				     String type = row.getCell(Constants.XLS_TYPE).toString().trim();
				     String priority = row.getCell(Constants.XLS_PRIORITY).toString().trim();
				     String excepted_result = row.getCell(Constants.XLS_EXCEPTED_RESULT).toString().trim().replace("\n", " & ");
				     
				     if(title.isEmpty())
				    	 continue;
				     if(type.isEmpty() && priority.isEmpty() && excepted_result.isEmpty()){
				    	 descript_head = title;
				    	 continue;
				     }
				     
				     count++;
				     TestCase testcase = new TestCase();
				     try{
				     testcase.priority = priority.substring(1);
				     }catch(Exception e){
				    	 Logger.i(title);
				    	 Logger.i(excepted_result);
				     }
				     testcase.description =  descript_head + " - " + title;
				     testcase.expect_result = excepted_result;
				     testcase.tag = type;
				     testcase.name = String.format("test_%s_%06d", component.toLowerCase(), count);
				     
				     testPlan.testcaseList.add(testcase);
				}
			
			list.add(testPlan);
		}

		try {
			workbook.close();
		} catch (IOException e) {
			Logger.e(e);
		}
		return list;
	}
}
