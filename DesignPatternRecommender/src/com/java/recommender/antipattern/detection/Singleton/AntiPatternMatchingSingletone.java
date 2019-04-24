package com.java.recommender.antipattern.detection.Singleton;

import java.util.ArrayList;

import com.java.parser.diagram.activity.ActivityDiagramAnalyzer;
import com.java.recommender.antipattern.detection.AntiPatternMatching;
import com.java.recommender.structures.AntiPattern;
import com.java.recommender.structures.BehavioralMetric;
import com.java.recommender.structures.StructuralMetric;

public class AntiPatternMatchingSingletone extends AntiPatternMatching {

	ActivityDiagramAnalyzer activityDiagramAnalyzer;
	public ArrayList<String> singletoneClasses;

	public AntiPatternMatchingSingletone(AntiPattern antiPattern,
			StructuralMetric structuralMetric, BehavioralMetric behavioralMetric, double[] weights) {
		super(antiPattern, structuralMetric, behavioralMetric, weights);
		activityDiagramAnalyzer = new ActivityDiagramAnalyzer();
	}

	@Override
	public boolean match() {
		// structuralMatching = new StructuralMatching(system, antiPattern);
		// int structureMatchCount = structuralMatching.matchStructure();
		// if (structureMatchCount == 0)
		// return false;
		weights[0] = 0;
		behavioralMatching = new BehavioralMatchingSingletone(behavioralMetric);
		int behaviorMatchCount = behavioralMatching.matchBehavior();
		if (behaviorMatchCount == 0) {
			scores[1] = 0;
			// return false;
		} else
			scores[1] = 1;
		System.out.println("S beh=" + scores[1]);
		semanticMatching = new SemanticMatchingSingletone(
				(BehavioralMatchingSingletone) behavioralMatching,
				structuralMetric, behavioralMetric);
		int semanticMatchCount = semanticMatching.matchSemantic();
		SemanticMatchingSingletone semanticMatchingPrototype = (SemanticMatchingSingletone) semanticMatching;
		singletoneClasses = semanticMatchingPrototype.getSingletoneClasses();
		if (semanticMatchCount == 0) {
			scores[2] = 0;
			// return false;
		} else
			scores[2] = 1;
		System.out.println("S sem=" + scores[2]);
		if (scores [1]==1 && scores[2]==1) {
			return true;
		}
		else return false;
	}

}
