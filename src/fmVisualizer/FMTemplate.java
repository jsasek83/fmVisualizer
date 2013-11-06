package fmVisualizer;

import java.util.List;

import fmVisualizer.FMGraph.NodeRelationship;

public class FMTemplate {

	public static String createDotString(String name, List<NodeRelationship> graphList) {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("digraph ").append(name).append(" {").append("\n");
		
		for(NodeRelationship relationship : graphList){
			sb.append(relationship.toString()).append("\n");
		}
		
		sb.append(" }");
		
		return sb.toString();
	}

}
