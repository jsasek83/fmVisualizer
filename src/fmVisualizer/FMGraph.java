package fmVisualizer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FMGraph {
	
	private List<NodeRelationship> relationships = new ArrayList<NodeRelationship>();
	private String jspStoreDir;
	
	public void processCimport(String line, File parentFile) throws FileNotFoundException{
		
		int urlStartIndex = line.indexOf("url=\"");
		int urlEndIndex = line.indexOf("\"", urlStartIndex + 5);
		String fileName = line.substring(urlStartIndex + 5,urlEndIndex);
		
		fileName = fileName.replace("${jspStoreDir}", this.jspStoreDir);
		File child = new File(fileName);
		this.relationships.add(new NodeRelationship(parentFile,child));
		this.searchFile(child);
		
	}
	
	
	public void processStaticInclude(String line, File parentFile) throws FileNotFoundException{
		
		String path = parentFile.getAbsolutePath();
		path = path.replace(parentFile.getName(), "");
		
		int urlStartIndex = line.indexOf("file=\"");
		int urlEndIndex = line.indexOf("\"", urlStartIndex + 6);
		String fileName = line.substring(urlStartIndex + 6,urlEndIndex);
		
		if(!fileName.startsWith("/")){
			
			fileName = path + fileName;
			//System.out.println(fileName);
			File child = new File(fileName);
			this.relationships.add(new NodeRelationship(parentFile,child));
			this.searchFile(child);
		}
		
	}
	
	public void searchFile(File featureFile) throws FileNotFoundException{
		
		Scanner featureScanner = new Scanner(featureFile);
		
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
	
	public static List<NodeRelationship> createGraphList(String featureName, String jspStoreDir) throws FileNotFoundException{
		
		FMGraph graph = new FMGraph();
		graph.jspStoreDir = jspStoreDir;
		File featureFile = new File(featureName);
		graph.searchFile(featureFile);
		return graph.relationships;
	}
	
	class NodeRelationship{
		
		public File parentFile; public File childFile;

		public NodeRelationship(File parentFile, File childFile) {
			this.parentFile = parentFile; this.childFile = childFile;
		}
		
		@Override
		public String toString() {
			return this.parentFile.getName() + "->" + this.childFile.getName() + ";";
		}
		
	}
	
	
	public static void main(String[] args) throws Exception {
		
		List<NodeRelationship> createGraphList = FMGraph.createGraphList("/Users/hammyx83/Stores/WebContent/CostcoGLOBALSAS/ShoppingArea/CheckoutImprovementsSection/ShippingDisplay.jsp","/Users/hammyx83/Stores/WebContent/CostcoGLOBALSAS/");
		System.out.println(createGraphList);
		
	}

}
