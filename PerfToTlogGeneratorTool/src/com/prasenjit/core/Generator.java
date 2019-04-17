package com.prasenjit.core;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Generator {
	private String tlogTemplate = null;
	private String tlogHeader = null;
	private static final String NEW_LINE_SEPARATOR = "\n";
	private int dynamicCount = 0;
	private static String sOpPath = null;
	private String reportCSV = "TLOG_BILLING_" + new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss'.tmp'").format(new Date());
	private FileWriter fileWriter = null;
	private String[] token = null;
	public static int lineNo = 0;
	
	public void setTlogHeader(String tLogHeader) {
		this.tlogHeader=tLogHeader;
	}

	public void setTlogTemplate(String tlogTemplate) {
		this.tlogTemplate = tlogTemplate;
	}

	public void setDynamicCount(String dynamicCount) {
		this.dynamicCount = Integer.parseInt(dynamicCount);
	}

	public void setScriptOutputPath(String sOpPath) {
		this.sOpPath = sOpPath;
	}

//	public static int countLines(String str) {
//	    if(str == null || str.isEmpty())
//	    {
//	        return 0;
//	    }
//	    int lines = 1;
//	    int pos = 0;
//	    while ((pos = str.indexOf("\n", pos) + 1) != 0) {
//	        lines++;
//	    }
//	    return lines;
//	}

	String readFile() throws IOException {
		String filename = sOpPath;
		System.out.println(sOpPath);
		BufferedReader br = new BufferedReader(new FileReader(filename));
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append(";");
				line = br.readLine();
				lineNo++;
			}
//			System.out.println("sb.length: " + sb.length());
//			System.out.println("sb.toString: " + sb.toString());
			return sb.toString();
		} finally {
			br.close();
		}
	}

	public int tlogGenerator() throws IOException {
		try {
			int globalCount = 0;
			fileWriter = new FileWriter(reportCSV);
			token = (new Generator().readFile()).split(";");
//			System.out.println(token + "\n");
//			System.out.println(token[0]);
//			System.out.println(token[15]);
//			System.out.println("LINENO:" + lineNo);
			for (int j = 0; j < lineNo; j++) {
				if (globalCount == 0) {
					String ptlogTemplate = tlogTemplate;
					for (int i = 0; i < dynamicCount; i++) {
						String replacer = "$";
//						System.out.println("Token i=" + token[i]);
//						System.out.println(replacer + i);
						ptlogTemplate = ptlogTemplate.replace(replacer + i, token[i]);
//						System.out.println("i=" + i);
						if (i == dynamicCount-1) {
							fileWriter.append("@Generated Through Perf To Tlog Generator Tool");
							fileWriter.append(NEW_LINE_SEPARATOR);
							fileWriter.append("VB 1.8.0");
							fileWriter.append(NEW_LINE_SEPARATOR);
							fileWriter.append(tlogHeader);
							fileWriter.append(NEW_LINE_SEPARATOR);
							fileWriter.append(ptlogTemplate);
							fileWriter.append(NEW_LINE_SEPARATOR);
						}
						globalCount = i;
//						System.out.println("globalCount2:"+globalCount);
					}
				} else {
					String ptlogTemplate = tlogTemplate;
					for (int i = 0; i < dynamicCount; i++) {

						String replacer = "$";
//						System.out.println("Token i=" + token[i]);
//						System.out.println(replacer + i);
//						System.out.println("index:"+(globalCount));
						ptlogTemplate = ptlogTemplate.replace(replacer + i, token[globalCount+1]);
//						System.out.println("i=" + i);
						if (i == dynamicCount-1) {
							fileWriter.append(ptlogTemplate);
							fileWriter.append(NEW_LINE_SEPARATOR);
						}
						globalCount++;
//						System.out.println("globalCount1:"+globalCount);
					}
				}
			}
		} catch (IOException e) {
			System.out.println(e);
			return 0;
		} finally {
			fileWriter.flush();
			fileWriter.close();
		}
		return 1;
	}

}
