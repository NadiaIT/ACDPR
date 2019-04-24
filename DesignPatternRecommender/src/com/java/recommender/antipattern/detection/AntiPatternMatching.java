package com.java.recommender.antipattern.detection;

import com.java.recommender.structures.AntiPattern;
import com.java.recommender.structures.BehavioralMetric;
import com.java.recommender.structures.StructuralMetric;

public abstract class AntiPatternMatching {

	public int[][] system;
	public AntiPattern antiPattern;
	public StructuralMatching structuralMatching;
	public BehavioralMatching behavioralMatching;
	public SemanticMatching semanticMatching;
	public StructuralMetric structuralMetric;
	public BehavioralMetric behavioralMetric;
	public double[] scores;
	public double[] weights;

	public AntiPatternMatching(AntiPattern antiPattern,
			StructuralMetric structuralMetric, BehavioralMetric behavioralMetric,double[] weights) {
		this.system = structuralMetric.matrix;
		this.antiPattern = antiPattern;
		this.structuralMetric = structuralMetric;
		this.behavioralMetric = behavioralMetric;
		scores = new double[3];
		this.weights = weights;
	}

	public abstract boolean match();

	public double getScore() {
		return ((weights[0] * scores[0]) + (weights[1] * scores[1]) + (weights[2] * scores[2]))
				/ (weights[0] + weights[1] + weights[2]);
	}
}
