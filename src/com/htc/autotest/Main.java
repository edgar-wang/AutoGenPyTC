/**
 * 
 */
package com.htc.autotest;

import java.util.ArrayList;

import com.htc.autotest.excel.ExcelHandler;
import com.htc.autotest.python.TCGenerator;
import com.htc.autotest.python.TestComponent;
import com.htc.autotest.util.Logger;

/**
 * @author Edgar_Wang
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Logger.init();
		try{
			// check arguments, dump arguments.
			CmdLineHandler cmdHandler = CmdLineHandler.getInstance(); 
			if(cmdHandler.setArgs(args) == false){
				throw new Exception("Invalid arguments.");
			}
			
			//Get input excel path
			String xlsPath = cmdHandler.getOtionValue(Constants.ARG_INPUT_PATH);
			//Get output folder
			String outputPath = cmdHandler.getOtionValue(Constants.ARG_OUTPUT_PATH);
			
			//analyze excel
			ArrayList<TestComponent> compList = ExcelHandler.parsingExcel(xlsPath);
			//save to python
			TCGenerator.execute(outputPath, compList);
			
		}catch(Exception e){
			Logger.e("Exception occured!");
			Logger.e(e);
		}finally{
		}
	}

}
