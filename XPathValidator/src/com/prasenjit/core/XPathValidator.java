package com.prasenjit.core;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;

public class XPathValidator {
	public XPathValidator() {
	}

	public static void main(String[] args) {
		System.out.println("Execution Start");

		String xPath = null;
		String fileLocation = null;
		FileInputStream fileInput = null;

		StringBuffer requestTemplate = new StringBuffer();
		String tmp = null;
		requestTemplate = new StringBuffer("");
		BufferedReader br = null;
		try {
			File file = new File("Input.properties");
			fileInput = new FileInputStream(file);
			Properties properties = new Properties();

			System.out.println("Input.properties is loading......");
			properties.load(fileInput);

			System.out.println("Input.properties loaded succesfully");

			xPath = properties.getProperty("XPATH");
			fileLocation = properties.getProperty("FILE_LOCATION");

			br = new BufferedReader(new java.io.FileReader(fileLocation));
			while ((tmp = br.readLine()) != null) {
				requestTemplate.append(tmp);
			}
		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException localIOException1) {
				}
				br = null;
			}
		}
		try {
			if ((xPath.isEmpty()) || (fileLocation.isEmpty())) {
				System.out.println("In Input.properties please fill the Parameters!");
			} else {
				ByteArrayInputStream bis = new ByteArrayInputStream(requestTemplate.toString().getBytes());
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder documentbuilder = factory.newDocumentBuilder();
				Document responseDoc = documentbuilder.parse(bis);

				XPath xpath = XPathFactory.newInstance().newXPath();
				String s = xpath.compile(xPath).evaluate(responseDoc);
				System.out.println("XPATH VALUE: " + s);
			}
		} catch (Exception e) {
			System.out.println("ERROR:" + e);
		}
	}

}
