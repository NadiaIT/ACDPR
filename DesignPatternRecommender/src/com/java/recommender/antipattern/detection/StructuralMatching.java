package com.java.recommender.antipattern.detection;

import com.java.recommender.structures.AntiPattern;

public class StructuralMatching {

	AntiPattern antiPattern;
	public int[][] system;

	public StructuralMatching(int[][] system, AntiPattern antiPattern) {
		this.antiPattern = antiPattern;
		this.system = system;
	}

	public int matchStructure() {
		MatrixMatch match = new MatrixMatch();
		int matchCount = 0;
		for (int i = 0; i < antiPattern.structuralMetric.size(); i++) {
			int[][] matrix = antiPattern.structuralMetric.get(i).matrix;
			if (system.length < matrix.length) {
				continue;
			}
			boolean no = match.naiveMatch(matrix, system);
		//	if (no)
		//		System.out.println("Matched with " + i);
			matchCount += no ? 1 : 0;
		}
		return matchCount;
	}
}
