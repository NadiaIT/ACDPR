package com.java.recommender.antipattern.detection.Prototype;

import java.util.ArrayList;

import com.java.recommender.antipattern.detection.SemanticMatching;
import com.java.recommender.structures.BehavioralMetric;
import com.java.recommender.structures.StructuralMetric;

public class SemanticMatchingPrototype extends SemanticMatching {

	BehavioralMatchingPrototype behavioralMatching;
	StructuralMetric structuralMetric;
	BehavioralMetric behavioralMetric;
	ArrayList<String> prototypeClasses;

	public SemanticMatchingPrototype(
			BehavioralMatchingPrototype behavioralMatching,
			StructuralMetric structuralMetric, BehavioralMetric behavioralMetric) {
		this.behavioralMatching = behavioralMatching;
		this.structuralMetric = structuralMetric;
		this.behavioralMetric = behavioralMetric;
		prototypeClasses = new ArrayList<String>();
	}

	public int matchSemantic() {
		int semanticCount = 0;
		for (int i = 0; i < behavioralMatching.prototypeNodes.size(); i++) {
			for (int j = 0; j < structuralMetric.classNames.length; j++) {
				if (behavioralMatching.prototypeNodes.get(i).matches(
						structuralMetric.classNames[j])) {
					prototypeClasses.add(structuralMetric.classNames[j]);
					semanticCount++;
				}
			}
		}
		return semanticCount;
	}

	public ArrayList<String> getPrototypeClasses() {
		return prototypeClasses;
	}
}
