package com.java.recommender.antipattern.detection.Builder;

import java.util.ArrayList;

import com.java.recommender.antipattern.detection.AntiPatternMatching;
import com.java.recommender.structures.AntiPattern;
import com.java.recommender.structures.BehavioralMetric;
import com.java.recommender.structures.StructuralMetric;

public class AntiPatternMatchingBuilder extends AntiPatternMatching {

	public ArrayList<String> builderClasses;

	public AntiPatternMatchingBuilder(AntiPattern antiPattern,
			StructuralMetric structuralMetric, BehavioralMetric behavioralMetric, double[] weights) {
		super(antiPattern, structuralMetric, behavioralMetric, weights);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean match() {

		structuralMatching = new StructuralMatchingBuilder(system, antiPattern,
				structuralMetric);
		int structureMatchCount = structuralMatching.matchStructure();
		if (structureMatchCount == 0) {
			scores[0] = 0;
			// return false;
		} else
			scores[0] = 1;
		System.out.println("B str=" + scores[0]);
		StructuralMatchingBuilder smb = (StructuralMatchingBuilder) structuralMatching;
		behavioralMatching = new BehavioralMatchingBuilder(smb.constructors);
		int behaviorMatchCount = behavioralMatching.matchBehavior();
		builderClasses = ((BehavioralMatchingBuilder) behavioralMatching)
				.getBuilderClasses();
		if (behaviorMatchCount == 0) {
			scores[1] = 0;
			// return false;
		} else
			scores[1] = 1;
		System.out.println("B beh=" + scores[1]);
		weights[2] = 0;
		if (scores[0]==1 && scores [1]==1) {
			return true;
		}
		else return false;
	}

}
