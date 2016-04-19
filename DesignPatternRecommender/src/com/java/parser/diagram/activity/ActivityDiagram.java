package com.java.parser.diagram.activity;

import java.util.ArrayList;

public class ActivityDiagram {
	public ArrayList<ActivityNode> nodes;
	public ArrayList<ActivityEdge> edges;

	public ActivityDiagram() {
		nodes = new ArrayList<ActivityNode>();
		edges = new ArrayList<ActivityEdge>();
	}
}
