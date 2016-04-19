package com.java.recommender.antipattern.detection.Singleton;

import java.util.ArrayList;

import com.java.parser.diagram.activity.ActivityDiagram;
import com.java.recommender.antipattern.detection.SemanticMatching;
import com.java.recommender.structures.BehavioralMetric;
import com.java.recommender.structures.StructuralMetric;

public class SemanticMatchingSingletone extends SemanticMatching {

	BehavioralMatchingSingletone behavioralMatching;
	StructuralMetric structuralMetric;
	BehavioralMetric behavioralMetric;
	ArrayList<String> singletoneClasses;

	public SemanticMatchingSingletone(
			BehavioralMatchingSingletone behavioralMatching,
			StructuralMetric structuralMetric, BehavioralMetric behavioralMetric) {
		this.behavioralMatching = behavioralMatching;
		this.structuralMetric = structuralMetric;
		this.behavioralMetric = behavioralMetric;
		singletoneClasses = new ArrayList<String>();
	}

	public int matchSemantic() {
		int semanticCount = 0;
		for (int i = 0; i < behavioralMatching.singletoneNodes.size(); i++) {
			String[] sigletoneIds = behavioralMatching.singletoneNodes.get(i)
					.split(",");
			String[] sigletones = new String[sigletoneIds.length];
			for (int s = 0; s < sigletoneIds.length; s++) {
				for (int b = 0; b < behavioralMetric.activityDiagrams.size(); b++) {
					ActivityDiagram activityDiagram = behavioralMetric.activityDiagrams
							.get(b);
					for (int k = 0; k < activityDiagram.nodes.size(); k++) {
						if (sigletoneIds[s].matches(activityDiagram.nodes
								.get(k).id)) {
							sigletones[s] = activityDiagram.nodes.get(k).name;
							break;
						}
					}
				}
			}
			for (int k = 0; k < sigletones.length; k++) {
				String matchedClass = getMostMatchedClass(sigletones[k]);
				if (matchedClass != null) {
					singletoneClasses.add(matchedClass);
					semanticCount++;
				}
			}
		}
		return semanticCount;
	}

	private String getMostMatchedClass(String node) {
		String matchedClass = null;
		for (int j = 0; j < structuralMetric.classNames.length; j++) {
			if (node.toLowerCase().replaceAll("[^a-zA-Z0-9]", "")
					.contains(structuralMetric.classNames[j].toLowerCase())) {
				if (matchedClass == null
						|| (matchedClass != null && matchedClass.length() < structuralMetric.classNames[j]
								.length())) {
					matchedClass = structuralMetric.classNames[j];
				}
			}
		}
		return matchedClass;
	}

	public ArrayList<String> getSingletoneClasses() {
		return singletoneClasses;
	}
}
