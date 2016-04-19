package com.java.recommender.structures;

import java.util.ArrayList;

public class AntiPattern {
	public String identifier;
	public int designPatternId;
	public ArrayList<StructuralMetric> structuralMetric;
	public ArrayList<BehavioralMetric> behavioralMetric;
	public ArrayList<SemanticMetric> semanticMetric;

	public AntiPattern() {
		structuralMetric = new ArrayList<StructuralMetric>();
		behavioralMetric = new ArrayList<BehavioralMetric>();
		semanticMetric = new ArrayList<SemanticMetric>();
	}
}
