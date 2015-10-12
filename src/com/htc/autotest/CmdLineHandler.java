package com.htc.autotest;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.htc.autotest.util.Logger;

public class CmdLineHandler {

	private static CmdLineHandler mInstance = null;
	private Options mOptions = null;
	private CommandLine mCmdLine = null;
	private String[] mArgs = null;
	
	private CmdLineHandler() {
		mOptions = new Options();
		mOptions.addOption( Constants.ARG_INPUT_PATH, "-input", true, "input path." );
		mOptions.addOption( Constants.ARG_OUTPUT_PATH, "-output", true, "output path." );
	}
	
	public synchronized static CmdLineHandler getInstance(){
		if (mInstance == null) {
			mInstance = new CmdLineHandler();
			Logger.i("New Instantce");
		}
		return mInstance;
	}
	
	public boolean setArgs(String[] args){
		dumpArgs(args);
		
		boolean bRet = true;
		// args length zero is for UI mode.
		if(args == null){
			bRet = false;
			return bRet;
		}
		
		mArgs = args;
		CommandLineParser parser = new DefaultParser();
		
		try {
			mCmdLine = parser.parse(mOptions, mArgs, true);
			if (mCmdLine.hasOption(Constants.ARG_INPUT_PATH) == false
					|| mCmdLine.hasOption(Constants.ARG_OUTPUT_PATH) == false){
				bRet = false;
			}
			
		}catch (ParseException e) {
			mArgs = null;
			bRet = false;
			Logger.e(e);
		}
		return bRet;
	}
	
	public boolean hasOption(String option){
		if(mCmdLine == null){
			return false;
		}
		return mCmdLine.hasOption(option);
	}
	
	
	public String getOtionValue(String option){
		if(mCmdLine == null){
			return null;
		}
		return mCmdLine.getOptionValue(option);
	}
	
	private void dumpArgs(String[] args) {
		if (args == null){
			Logger.i("args is empty");
			return;
		}
		
		StringBuffer argsBuffer = new StringBuffer();
		for(int i = 0; i < args.length; i++) {
			argsBuffer.append(args[i]).append(" ");
		}
		
		Logger.i("Get args: " + argsBuffer.toString());
	}
}
