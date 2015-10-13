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
					FileUtils.copyFile(targetFile, oldFile);
				}
				ArrayList<String> strTC = new ArrayList<String>();
				for (TestCase testcase : testplan.testcaseList) {
					strTC.add(Config.getProperty("PY_TC_SETUP"));
					strTC.add(String.format(Config.getProperty("PY_TC_ATTR_PRIORITY"), testcase.priority));
					strTC.add(String.format(Config.getProperty("PY_TC_ATTR_DESC"), testcase.description));
					strTC.add(Config.getProperty("PY_TC_ATTR_STEP"));
					strTC.add(String.format(Config.getProperty("PY_TC_ATTR_RESULT"), testcase.expect_result));
					strTC.add(String.format(Config.getProperty("PY_TC_ATTR_TAGS"), testcase.tag));
					strTC.add(String.format(Config.getProperty("PY_TC_NAME"), testcase.name));
					strTC.add("\tpass");
					strTC.add("");
					FileUtils.writeLines(targetFile, strTC);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Logger.e(e);;
		}
	}
}