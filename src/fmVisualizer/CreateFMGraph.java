package fmVisualizer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CreateFMGraph {
	
	public static final String FEATURE_NAME = "/Users/hammyx83/Stores/WebContent/CostcoGLOBALSAS/ShoppingArea/CheckoutImprovementsSection/ShippingDisplay.jsp";
	public static final String DOT_FILE_NAME = "/Users/hammyx83/fmGraph.dot";
	
	public static void processCimport(String line, File parentFile) throws FileNotFoundException{
		
		int urlStartIndex = line.indexOf("url=\"");
		int urlEndIndex = line.indexOf("\"", urlStartIndex + 5);
		String fileName = line.substring(urlStartIndex + 5,urlEndIndex);
		
		System.out.print(parentFile.getName() + " -> ");
		
		fileName = fileName.replace("${jspStoreDir}", "/Users/hammyx83/Stores/WebContent/CostcoGLOBALSAS/");
		
		//System.out.println(fileName);
		
		searchFile(fileName);
		
	}
	
	
	public static void processStaticInclude(String line, File parentFile) throws FileNotFoundException{
		
		String path = parentFile.getAbsolutePath();
		path = path.replace(parentFile.getName(), "");
		
		int urlStartIndex = line.indexOf("file=\"");
		int urlEndIndex = line.indexOf("\"", urlStartIndex + 6);
		String fileName = line.substring(urlStartIndex + 6,urlEndIndex);
		
		if(!fileName.startsWith("/")){
			
			System.out.print(parentFile.getName() + " -> ");			
			fileName = path + fileName;
			//System.out.println(fileName);
			searchFile(fileName);
		}
		
	}
	
	public static void searchFile(String fileName) throws FileNotFoundException{
		
		File featureFile = new File(fileName);
		Scanner featureScanner = new Scanner(featureFile);
		
		System.out.println(featureFile.getName() + ";");
		
		while(featureScanner.hasNextLine()){
			
			String nextLine = featureScanner.nextLine();
			
			if(nextLine.contains("<c:import")){
				//System.out.println("C:import: " + nextLine);
				processCimport(nextLine, featureFile);
			}else if(nextLine.contains("<%@ include")){
				//System.out.println("static:include: " + nextLine);
				processStaticInclude(nextLine, featureFile);
			}
;			
		}
		
	}
	
	
	public static void main(String[] args) throws Exception {
		
		searchFile(FEATURE_NAME);
		
	}

}
