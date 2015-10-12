package com.htc.autotest.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.htc.autotest.Constants;


public class Config {
	private static Properties mProps;

	static {
		mProps = new Properties();
		updateConfig();
	}

	public static String getProperty(String key) {
		if (key == null || key.trim().isEmpty()) {
			return null;
		}
		
		return mProps.getProperty(key, null);
	}
	
	public static void updateConfig() {
		FileInputStream fis = null;
		
		try {			
			fis = new FileInputStream(Constants.CONFIG_FILE);
			mProps.load(fis);
		} catch (Exception e) {
			mProps = null;
			
		} finally {
			if(fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					Logger.i("Config: Exception message= "+ e.getMessage());
				}
			}
		}
	}
}