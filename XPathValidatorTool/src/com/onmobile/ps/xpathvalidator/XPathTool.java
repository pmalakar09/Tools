package com.onmobile.ps.xpathvalidator;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;


public class XPathTool {
	static Logger log = Logger.getLogger(XPathTool.class);
	public static void main(String[] args) {		
		System.out.println("Execution Start");
		
		String xPath=null;
		String fileLocation=null;
		FileInputStream fileInput = null;
				
		StringBuffer requestTemplate = new StringBuffer();
		String tmp = null;
		requestTemplate = new StringBuffer("");
		BufferedReader br = null;
		try {
			
			 File file = new File("Input.properties");
			 fileInput = new FileInputStream(file);
			 Properties properties = new Properties();
			 log.info("Input.properties is getting loaded......");
			 System.out.println("Input.properties is getting loaded......");
			 properties.load(fileInput);
			 log.info("Input.properties loaded succesfully");
			 System.out.println("Input.properties loaded succesfully");
			 
			 xPath=properties.getProperty("XPATH");
			 fileLocation=properties.getProperty("FILE_LOCATION");
			br  = new BufferedReader(new FileReader(fileLocation));
			while((tmp = br.readLine())!=null){
				requestTemplate.append(tmp);
			}	
		}catch(Throwable e){
			e.printStackTrace();
		}finally{
			if(br!=null) {
				try {
					br.close();
				} catch (IOException e) {
				}
				br=null;
			}
		}

		try {
			if((xPath.isEmpty()) || (fileLocation.isEmpty()))
				{
				System.out.println("In Input.properties please fill the Parameters!");
			    }
			else {
				ByteArrayInputStream bis = new ByteArrayInputStream(requestTemplate.toString().getBytes());
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder documentbuilder = factory.newDocumentBuilder();
				Document responseDoc = documentbuilder.parse(bis);
	
				javax.xml.xpath.XPath xpath  = XPathFactory.newInstance().newXPath();
				String s = ((javax.xml.xpath.XPath) xpath).compile(xPath).evaluate(responseDoc);
	            System.out.println("XPATH VALUE: "+s);
				
			}
			}
		catch(Exception e){
             System.out.println("ERROR:"+e);       
            }

			}
}