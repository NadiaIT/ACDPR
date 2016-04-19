package com.java.recommender.antipattern.detection.Builder;

import java.util.ArrayList;

import com.java.parser.diagram.classs.Classs;
import com.java.parser.diagram.classs.Method;
import com.java.recommender.antipattern.detection.StructuralMatching;
import com.java.recommender.structures.AntiPattern;
import com.java.recommender.structures.StructuralMetric;

public class StructuralMatchingBuilder extends StructuralMatching {

	StructuralMetric metric;
	public ArrayList<ArrayList<Method>> constructors;

	public StructuralMatchingBuilder(int[][] system, AntiPattern antiPattern,
			StructuralMetric metric) {
		super(system, antiPattern);
		this.metric = metric;
	}

	@Override
	public int matchStructure() {
		int[] constructorsCount = getTelescopingConstructorCount();
		int max = -1;
		for (int i = 0; i < constructorsCount.length; i++) {
			if (constructorsCount[i] > max)
				max = constructorsCount[i];
		}
		return max > 1 ? max : 0;
	}

	private int[] getTelescopingConstructorCount() {
		ArrayList<Classs> classes = metric.classDiagram.classes;
		int[] constructorCount = new int[classes.size()];
		constructors = new ArrayList<ArrayList<Method>>();
		ArrayList<Method> methods = new ArrayList<Method>();
		for (int i = 0; i < classes.size(); i++) {
			methods = new ArrayList<Method>();
			int count = 0;
			for (int j = 0; j < classes.get(i).methods.size(); j++) {
				Method m = classes.get(i).methods.get(j);
				if (classes.get(i).name.toLowerCase().matches(
						m.name.toLowerCase())) {
					methods.add(m);
					count++;
				}
			}
			constructorCount[i] = count;
			constructors.add(methods);
		}
		return constructorCount;
	}

}
