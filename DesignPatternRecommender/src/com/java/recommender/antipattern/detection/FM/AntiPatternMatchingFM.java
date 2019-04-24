package com.java.recommender.antipattern.detection.FM;

import com.java.recommender.antipattern.detection.AntiPatternMatching;
import com.java.recommender.antipattern.detection.StructuralMatching;
import com.java.recommender.antipattern.detection.AF.BehavioralMatchingAF;
import com.java.recommender.structures.AntiPattern;
import com.java.recommender.structures.BehavioralMetric;
import com.java.recommender.structures.StructuralMetric;

public class AntiPatternMatchingFM extends AntiPatternMatching {

	public AntiPatternMatchingFM(AntiPattern antiPattern,
			StructuralMetric structuralMetric, BehavioralMetric behavioralMetric, double[] weights) {
		super(antiPattern, structuralMetric, behavioralMetric, weights);
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
		System.out.println("FM str=" + scores[0]);
		behavioralMatching = new BehavioralMatchingAF(behavioralMetric);
		int behaviorMatchCount = behavioralMatching.matchBehavior();
		if (behaviorMatchCount == 0) {
			scores[1] = 0;
			// return false;
		} else
			scores[1] = 1;
		System.out.println("FM beh=" + scores[1]);
		semanticMatching = new SemanticMatchingFM(system,
				structuralMetric.classNames, behavioralMetric);
		int semanticMatchCount = semanticMatching.matchSemantic();
		if (semanticMatchCount == 0) {
			scores[2] = 0;
			// return false;
		} else
			scores[2] = 1;
		System.out.println("FM sem=" + scores[2]);
		if (scores[0]==1 && scores [1]==1 && scores[2]==1) {
			return true;
		}
		else return false;
	}

}
