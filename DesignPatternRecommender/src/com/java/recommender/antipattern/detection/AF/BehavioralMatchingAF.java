package com.java.recommender.antipattern.detection.AF;

import com.java.recommender.antipattern.detection.BehavioralMatching;
import com.java.recommender.structures.BehavioralMetric;

public class BehavioralMatchingAF extends BehavioralMatching {

	BehavioralMetric behavioralMetric;

	public BehavioralMatchingAF(BehavioralMetric metric) {
		behavioralMetric = metric;
	}

	@Override
	public int matchBehavior() {
		behavioralMetric.generateBehavioralMetricFromSequence();
		return behavioralMetric.sequenceDiagrams.size() >= 2 ? 1 : 0;
	}

}
