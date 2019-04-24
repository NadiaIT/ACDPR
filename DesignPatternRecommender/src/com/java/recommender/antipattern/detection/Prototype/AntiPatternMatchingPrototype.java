package com.java.recommender.antipattern.detection.Prototype;

import java.util.ArrayList;

import com.java.recommender.antipattern.detection.AntiPatternMatching;
import com.java.recommender.structures.AntiPattern;
import com.java.recommender.structures.BehavioralMetric;
import com.java.recommender.structures.StructuralMetric;

public class AntiPatternMatchingPrototype extends AntiPatternMatching {

	public ArrayList<String> prototypeClasses;

	public AntiPatternMatchingPrototype(AntiPattern antiPattern,
			StructuralMetric structuralMetric, BehavioralMetric behavioralMetric, double[] weights) {
		super(antiPattern, structuralMetric, behavioralMetric, weights);
	}

	@Override
	public boolean match() {
		structuralMatching = new StructuralMatchingPrototype(system,
				antiPattern, structuralMetric.classDiagram);
		int structureMatchCount = structuralMatching.matchStructure();
		if (structureMatchCount == 0) {
			scores[0] = 0;
			// return false;
		} else
			scores[0] = 1;
		System.out.println("P str=" + scores[0]);
		behavioralMatching = new BehavioralMatchingPrototype(
				((StructuralMatchingPrototype) structuralMatching),
				behavioralMetric);
		int behaviorMatchCount = behavioralMatching.matchBehavior();
		if (behaviorMatchCount == 0) {
			scores[1] = 0;
			// return false;
		} else
			scores[1] = 1;
		System.out.println("P beh=" + scores[1]);
		semanticMatching = new SemanticMatchingPrototype(
				(BehavioralMatchingPrototype) behavioralMatching,
				structuralMetric, behavioralMetric);
		int semanticMatchCount = semanticMatching.matchSemantic();
		SemanticMatchingPrototype semanticMatchingPrototype = (SemanticMatchingPrototype) semanticMatching;
		prototypeClasses = semanticMatchingPrototype.getPrototypeClasses();
		// if (semanticMatchCount == 0)
		// return false;
		weights[2] = 0;
		if (scores[0]==1 && scores [1]==1) {
			return true;
		}
		else return false;
	}
}
