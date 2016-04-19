package com.java.recommender.antipattern.detection.Builder;

import java.util.ArrayList;
import java.util.LinkedHashSet;

import com.java.parser.diagram.classs.Method;
import com.java.parser.diagram.classs.Variable;
import com.java.recommender.antipattern.detection.BehavioralMatching;

public class BehavioralMatchingBuilder extends BehavioralMatching {

	ArrayList<ArrayList<Method>> constructors;
	ArrayList<String> builderClasses;

	public BehavioralMatchingBuilder(ArrayList<ArrayList<Method>> constructors) {
		this.constructors = constructors;
		builderClasses = new ArrayList<String>();
	}

	public int matchBehavior() {
		int maxInClass = 0;
		int leastTelescope = 50;
		for (int i = 0; i < constructors.size(); i++) {
			for (int j = 0; j < constructors.get(i).size(); j++) {
				for (int k = 0; k < constructors.get(i).size(); k++) {
					if (j == k)
						continue;
					ArrayList<Variable> v1 = constructors.get(i).get(j).parameters;
					ArrayList<Variable> v2 = constructors.get(i).get(k).parameters;
					ArrayList<String> param1 = new ArrayList<String>();
					ArrayList<String> param2 = new ArrayList<String>();
					for (int x = 0; x < v1.size(); x++) {
						param1.add(v1.get(x).name);
						// System.out.println(v1.get(x).name);
					}
					// System.out.println("-------");
					for (int x = 0; x < v2.size(); x++) {
						param2.add(v2.get(x).name);
						// System.out.println(v2.get(x).name);
					}
					param1.retainAll(param2);
					// System.out.println(param1.size());
					if (param1.size() < leastTelescope && param1.size() != 0)
						leastTelescope = param1.size();
					if (param1.size() > 1) {
						builderClasses.add(constructors.get(i).get(j).name);
					}
				}
			}
			if (leastTelescope > maxInClass && leastTelescope != 50)
				maxInClass = leastTelescope;
			builderClasses = new ArrayList<String>(new LinkedHashSet<String>(
					builderClasses));
		}
		// System.out.println("max " + maxInClass);
		return maxInClass == 50 ? 0 : maxInClass;
	}

	public ArrayList<String> getBuilderClasses() {
		return builderClasses;
	}
}
