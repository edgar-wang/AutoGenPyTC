package com.htc.autotest.python;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import com.htc.autotest.util.Config;
import com.htc.autotest.util.Logger;

public class TCGenerator {

	public static void execute(String outputPath, ArrayList<TestComponent> compList){
		if(compList == null){
			Logger.e("TestComponent is null");
			return;
		}
		
		try {
			for (TestComponent testplan : compList) {
				File targetFile = FileUtils.getFile(outputPath, String.format("test_%s.py", testplan.componentName.toLowerCase()));
				if (targetFile.exists()) {
					File oldFile = FileUtils.getFile(outputPath,
							String.format("test_%s_old.py", testplan.componentName.toLowerCase()));
					int count = 1;
					while(oldFile.exists()){
						oldFile = FileUtils.getFile(outputPath,
								String.format("test_%s_old(%02d).py", testplan.componentName.toLowerCase(), count));
						count++;
					}
					FileUtils.copyFile(targetFile, oldFile);
				}
				
				//create with header
				File headFile = FileUtils.getFile(Config.getProperty("PY_TC_HEADER_FILE"));
				FileUtils.copyFile(headFile, targetFile);
				ArrayList<String> strTC = new ArrayList<String>();
				for (TestCase testcase : testplan.testcaseList) {
					strTC.clear();
					strTC.add(Config.getProperty("PY_TC_SETUP"));
					strTC.add(String.format(Config.getProperty("PY_TC_ATTR_PRIORITY"), testcase.priority));
					strTC.add(String.format(Config.getProperty("PY_TC_ATTR_DESC"), testcase.description));
					strTC.add(Config.getProperty("PY_TC_ATTR_STEP"));
					strTC.add(String.format(Config.getProperty("PY_TC_ATTR_RESULT"), testcase.expect_result));
					strTC.add(String.format(Config.getProperty("PY_TC_ATTR_TAGS"), testcase.tag));
					strTC.add(String.format(Config.getProperty("PY_TC_NAME"), testcase.name));
					strTC.add("\tpass");
					strTC.add("");
					FileUtils.writeLines(targetFile, strTC, true);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Logger.e(e);;
		}
	}
}
