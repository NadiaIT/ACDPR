package com.java.recommender.antipattern.detection.AF;

import com.java.recommender.antipattern.detection.AntiPatternMatching;
import com.java.recommender.antipattern.detection.StructuralMatching;
import com.java.recommender.structures.AntiPattern;
import com.java.recommender.structures.BehavioralMetric;
import com.java.recommender.structures.StructuralMetric;

public class AntiPatternMatchingAF extends AntiPatternMatching {

	public AntiPatternMatchingAF(AntiPattern antiPattern,
			StructuralMetric structuralMetric, BehavioralMetric behavioralMetric) {
		super(antiPattern, structuralMetric, behavioralMetric);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean match() {
		structuralMatching = new StructuralMatching(system, antiPattern);
		int structureMatchCount = structuralMatching.matchStructure();
		if (structureMatchCount == 0) {
			scores[0] = 0;
			// return false;
		} else
			scores[0] = 1;
		System.out.println("AF str=" + scores[0]);
		behavioralMatching = new BehavioralMatchingAF(behavioralMetric);
		int behaviorMatchCount = behavioralMatching.matchBehavior();
		// System.out.println(behaviorMatchCount);
		if (behaviorMatchCount == 0) {
			scores[1] = 0;
			// return false;
		} else
			scores[1] = 1;
		System.out.println("AF beh=" + scores[1]);
		semanticMatching = new SemanticMatchingAF(system,
				structuralMetric.classNames, behavioralMetric);
		int semanticMatchCount = semanticMatching.matchSemantic();
		if (semanticMatchCount == 0) {
			scores[2] = 0;
			// return false;
		} else
			scores[2] = 1;
		System.out.println("AF sem=" + scores[2]);
		return true;
	}

}
