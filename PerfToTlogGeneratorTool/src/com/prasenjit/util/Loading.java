package com.prasenjit.util;
import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import com.prasenjit.core.Generator;

public class Loading {
	public void readInputs(Generator objGenerator)
	{
		 FileInputStream fileInput = null;
		 try{
			 File file = new File("Input.properties");
			 fileInput = new FileInputStream(file);
			 Properties properties = new Properties();
			 System.out.println("Input.properties is loading......");
			 properties.load(fileInput);
			 System.out.println("Input.properties loaded succesfully");
			 
			 objGenerator.setTlogTemplate(properties.getProperty("TLOG_TEMPLATE"));
			 objGenerator.setDynamicCount(properties.getProperty("NO_OF_DYNAMIC_VALUES", "0"));
			 objGenerator.setScriptOutputPath(properties.getProperty("SCRIPT_OP_PATH"));
			 objGenerator.setTlogHeader(properties.getProperty("TLOG_HEADER",""));
			 		 }
		 catch(Exception e)
		 {
			 System.out.println("ERROR during loading the Input.properties!!!! "+e);
		 }
		 finally{
			 
		 }
	}

}
