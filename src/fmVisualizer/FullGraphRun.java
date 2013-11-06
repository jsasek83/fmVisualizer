package fmVisualizer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import fmVisualizer.FMGraph.NodeRelationship;

public class FullGraphRun {
	
	
	public static void main(String[] args) throws IOException {
		
		List<NodeRelationship> graphList = FMGraph.createGraphList("/Users/hammyx83/Stores/WebContent/CostcoGLOBALSAS/ShoppingArea/CheckoutImprovementsSection/ShippingDisplay.jsp","/Users/hammyx83/Stores/WebContent/CostcoGLOBALSAS/");
		
		String dotString = FMTemplate.createDotString("test1", graphList);
		
		System.out.println(dotString);
		
		BufferedWriter out = new BufferedWriter(new FileWriter("test.dot"));
		out.write(dotString);
		out.close();
		
		
	}

}
