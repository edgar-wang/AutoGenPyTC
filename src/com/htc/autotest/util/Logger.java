package com.htc.autotest.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.htc.autotest.Constants;

public class Logger {
	
	private static PrintStream ps;
	private static boolean bLog2File = false;//TODO: remove when formal release
	
	static {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String logFolderPath = System.getProperty("user.dir") + File.separator + "Log";
		File logFolder = new File(logFolderPath);
		if (!logFolder.exists()) {
			try {
				logFolder.mkdirs();
			} catch (SecurityException se) {
				Logger.e(se);
			}
		}

		String filename = logFolderPath + File.separator
				+ dateFormat.format(new Date()) + ".log";
		try {
			ps = new PrintStream(new FileOutputStream(filename));
		} catch (FileNotFoundException e) {
			System.out.println("[Error]Create log file cause the FileNotFoundException");
			ps = null;
		}
	}

	public static void d(String description)
	{
		if (bLog2File && ps != null)
			toFile(getFuncName(Constants.DEBUG) + description);
		else
			System.out.println(getFuncName(Constants.DEBUG) + description);
	}

	public static void i(String description)
	{
		if (bLog2File && ps != null)
			toFile(getFuncName(Constants.INFO) + description);
		else
			System.out.println(getFuncName(Constants.INFO) + description);
	}

	public static void w(String description)
	{
		if (bLog2File && ps != null)
			toFile(getFuncName(Constants.WARNING) + description);
		else
			System.out.println(getFuncName(Constants.WARNING) + description);
	}

	public static void e(String description)
	{
		if (bLog2File && ps != null)
			toFile(getFuncName(Constants.ERROR) + description);
		else
			System.out.println(getFuncName(Constants.ERROR) + description);
	}
	
	public static void e(Throwable e)
	{
		if (bLog2File && ps != null)
			toFile(getFuncName(Constants.ERROR) + e);
		else
			System.out.println(getFuncName(Constants.ERROR) + e);
	}

	public static void e(String description, Throwable e)
	{
		if (bLog2File && ps != null)
			toFile(getFuncName(Constants.ERROR) + description + ": " + e);
		else
			System.out.println(getFuncName(Constants.ERROR) + description + ": " + e);
	}
	
	public static void iFunc()
	{
		if (bLog2File && ps != null)
			toFile(getFuncName(Constants.INFO));
		else
			System.out.println(getFuncName(Constants.INFO));
	}
	
	public static void iFuncStart()
	{
		if (bLog2File && ps != null)
			toFile(getFuncName(Constants.INFO) + "+");
		else
			System.out.println(getFuncName(Constants.INFO) + "+");
	}
	
	public static void iFuncEnd()
	{
		if (bLog2File && ps != null)
			toFile(getFuncName(Constants.INFO) + "-");
		else
			System.out.println(getFuncName(Constants.INFO) + "-");
	}

	
	private static String getFuncName(String level)
	{
		StackTraceElement ste = Thread.currentThread().getStackTrace()[3];

		String className = ste.getClassName();

		//return "[" + sPkgName + "][" + className + "." +  ste.getMethodName() + "] ";
		return "[" + level + "][" + className.substring(className.lastIndexOf('.') + 1, className.length()) + "."
		        + ste.getMethodName() + "] ";
	}
	
	private static void toFile(String outstr) {
		PrintStream oldStream = System.out;
		System.out.println(outstr);
		System.setOut(ps);
		System.out.println(outstr);
		ps.flush();
		System.setOut(oldStream);
	}
}
