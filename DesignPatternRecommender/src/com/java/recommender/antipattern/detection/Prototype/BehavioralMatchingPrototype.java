package com.java.recommender.antipattern.detection.Prototype;

import java.util.ArrayList;

import com.java.parser.diagram.sequence.SequenceDiagram;
import com.java.recommender.antipattern.detection.BehavioralMatching;
import com.java.recommender.structures.BehavioralMetric;

public class BehavioralMatchingPrototype extends BehavioralMatching {

	BehavioralMetric behavioralMetric;
	StructuralMatchingPrototype structure;
	ArrayList<String> prototypeNodes;

	public BehavioralMatchingPrototype(StructuralMatchingPrototype structure,
			BehavioralMetric metric) {
		behavioralMetric = metric;
		this.structure = structure;
		prototypeNodes = new ArrayList<String>();
	}

	@Override
	public int matchBehavior() {
		int n = structure.classDiagram.classes.size();
		for (int i = 0; i < structure.prototypeClasses.size(); i++) {
			boolean hasChild = false;
			ArrayList<String> childClasses = new ArrayList<String>();
			for (int j = 0; j < n; j++) {
				if (structure.prototypeClasses.get(i).matches(
						structure.classDiagram.classes.get(j).name)) {
					for (int k = 0; k < n; k++) {
						if (structure.system[k][j] != 0
								&& structure.system[k][j] % 3 == 0) {
							childClasses.add(structure.classDiagram.classes
									.get(k).name);
							hasChild = true;
						}
					}
					break;
				}
			}
			if (!hasChild) {
				prototypeNodes.add(structure.prototypeClasses.get(i));
			} else {
				behavioralMetric.generateBehavioralMetricFromSequence();
				for (int s = 0; s < behavioralMetric.sequenceDiagrams.size(); s++) {
					SequenceDiagram diagram = behavioralMetric.sequenceDiagrams
							.get(s);
					ArrayList<String> connectedChild = new ArrayList<String>();
					boolean hasCallClass = false;
					for (int l = 0; l < diagram.lifelines.size(); l++) {
						if (structure.callClasses.get(i).matches(
								diagram.lifelines.get(l))) {
							hasCallClass = true;
						}
					}
					if (!hasCallClass)
						continue;
					for (int c = 0; c < childClasses.size(); c++) {
						for (int l = 0; l < diagram.lifelines.size(); l++) {
							if (childClasses.get(c).matches(
									diagram.lifelines.get(l))) {
								boolean hasConnection = false;
								for (int j = 0; j < diagram.messages.size(); j++) {
									if (diagram.messages.get(j).from
											.matches(structure.callClasses
													.get(i))
											&& diagram.messages.get(j).to
													.matches(diagram.lifelines
															.get(l))) {
										hasConnection = true;
										break;
									}
								}
								if (hasConnection) {
									connectedChild.add(childClasses.get(c));
									break;
								}
							}
						}
					}
					if (connectedChild.size()==1) {
						prototypeNodes.add(connectedChild.get(0));
						break;
					}
				}
			}
		}

		return structure.prototypeClasses.size();
	}

}
