package chopPredCDS;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ChopPredCDS {
	
	File inputFile, outputFile;

	
//========================Constructor=========================
	public ChopPredCDS(File infile, File outfile) {
		this.inputFile = infile;
		this.outputFile = outfile;
	}
	
	public void process(){
		File tempFile = null;
		BufferedReader br, tbr;
		FileWriter fw, tfw;
		
		boolean isPredicted = false;
		
		try {
			//prepare IO Files
			br = new BufferedReader (new FileReader(this.inputFile));
			fw = new FileWriter(this.outputFile);
			
			//prepare temporary file
			tempFile = File.createTempFile("abc", ".txt");
			
			//read GenBank data by a line
			String thisLine;
			
			//until found "FEATURES", copy all line.
			thisLine = br.readLine();
			while( thisLine != null) {
				if(thisLine.matches("^FEATURES.*")) {
					break;
					
				}else {
					fw.write(thisLine+"\n");
					thisLine = br.readLine();
				}
			}
			
			//find "CDS"
			while( thisLine != null) {
				if(thisLine.matches("^ {5}CDS.*")) {
					tfw = new FileWriter(tempFile);
					
					tfw.write(thisLine + "\n");
					thisLine = br.readLine();
					
					while( thisLine != null ) {
						if(thisLine.matches(" {21}/protein_id=\"X.*")) {
							isPredicted = true;
							tfw.write(thisLine + "\n");
							thisLine = br.readLine();
						}else if(thisLine.matches(" {0,5}[a-zA-Z].*")) {
							//reach the end of one CDS zone
							tfw.close();
							
							if(isPredicted) {
								isPredicted = false;
							}else {
								tbr = new BufferedReader( new FileReader(tempFile));
								
								String str;
								while( (str = tbr.readLine()) != null) {
									fw.write(str + "\n");
								}
								
								tbr.close();
							}
							
							break;
							
						}else {
							tfw.write(thisLine + "\n");
							thisLine = br.readLine();
						}
					}
				}else {
					fw.write(thisLine+"\n");
					thisLine = br.readLine();
				}
			}
			
			
			
			
			br.close();
			fw.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if (tempFile != null && tempFile.exists()) {
				tempFile.delete();
			}
		}
	}
}
