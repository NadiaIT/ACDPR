package com.java.recommender.antipattern.detection.Prototype;

import java.util.ArrayList;

import com.java.parser.diagram.classs.ClassDiagram;
import com.java.parser.diagram.classs.Classs;
import com.java.recommender.antipattern.detection.StructuralMatching;
import com.java.recommender.structures.AntiPattern;

public class StructuralMatchingPrototype extends StructuralMatching {

	AntiPattern antiPattern;
	ClassDiagram classDiagram;
	ArrayList<String> prototypeClasses;
	ArrayList<String> callClasses;

	public StructuralMatchingPrototype(int[][] system, AntiPattern antiPattern,
			ClassDiagram classDiagram) {
		super(system, antiPattern);
		this.classDiagram = classDiagram;
		prototypeClasses = new ArrayList<String>();
		callClasses = new ArrayList<String>();
	}

	public int matchStructure() {
		int matchCount = 0;
		int n = classDiagram.classes.size();
		for (int i = 0; i < n; i++) {
			Classs classs = classDiagram.classes.get(i);
			int[] classVarCount = new int[n];
			for (int j = 0; j < classs.variables.size(); j++) {
				for (int k = 0; k < n; k++) {
					if (classs.variables.get(j).type != null
							&& classs.variables.get(j).type
									.matches(classDiagram.classes.get(k).name)) {
						classVarCount[k]++;
					}
				}
			}
			for (int j = 0; j < classVarCount.length; j++) {
				if (classVarCount[j] > 1) {
					callClasses.add(classDiagram.classes.get(i).name);
					prototypeClasses.add(classDiagram.classes.get(j).name);
				}
				if (matchCount < classVarCount[j]) {
					matchCount = classVarCount[j];
				}
			}
		}
		return matchCount > 1 ? matchCount : 0;

	}
}
