package com.prasenjit.execution;

import java.io.IOException;

import com.prasenjit.core.Generator;
import com.prasenjit.util.Loading;

public class Execution {

	public static void main(String[] args) throws IOException {
		Generator objGenerator = new Generator();
		Loading objLoading = new Loading();
		objLoading.readInputs(objGenerator);
		int resultCode=objGenerator.tlogGenerator();
		if(resultCode==1) {
			System.out.println("TLOG Generated Successfully.");
		}
		if(resultCode==0) {
			System.out.println("TLOG Not Generated!! Some Exception Occur!!");
		}
	}

}
