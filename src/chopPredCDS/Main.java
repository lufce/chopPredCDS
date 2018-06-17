package chopPredCDS;

import java.io.*;


public class Main {
	public static void main(String args[]) {
		String inputFilePath = "C:\\Users\\Shohei\\Desktop\\temp\\b\\11c.gb";
		String outputFilePath = "C:\\Users\\Shohei\\Desktop\\temp\\b\\t_11c.gb";
		
		File in, out;
		in = new File(inputFilePath);
		out = new File(outputFilePath);
		
		if(in.exists()) {
			System.out.println("exist");
		}
		
		try {
			ChopPredCDS cpc = new ChopPredCDS(in,out);
			cpc.process();
		}catch(NullPointerException e) {
			System.out.println(e);
		}
		
		System.out.println("end");
	}
}