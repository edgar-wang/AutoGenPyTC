/**
 * 
 */
package com.htc.autotest;

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
		
		try{
			// check arguments, dump arguments.
			CmdLineHandler cmdHandler = CmdLineHandler.getInstance(); 
			if(cmdHandler.setArgs(args) == false){
				throw new Exception("Invalid arguments.");
			}
			
		}catch(Exception e){
			Logger.e("Exception occured!");
			Logger.e(e);
		}finally{
		}
	}

}
