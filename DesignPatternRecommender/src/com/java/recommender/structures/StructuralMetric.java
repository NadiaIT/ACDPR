package com.java.recommender.structures;

import com.java.parser.diagram.classs.ClassDiagram;
import com.java.parser.diagram.classs.ClassRelation;
import com.java.parser.diagram.classs.Classs;

public class StructuralMetric {

	public String[] classNames;
	public int[][] matrix;
	public int n;
	public ClassDiagram classDiagram;

	public StructuralMetric(ClassDiagram classDiagram) {
		this.classDiagram = classDiagram;
		n = classDiagram.classes.size();
		matrix = new int[n][n];
		classNames = new String[n];
	}

	public StructuralMetric(String[] classNames, int[][] matrix) {
		this.classNames = classNames;
		this.matrix = matrix;
	}

	public int[][] generateStructuralMetric() {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				matrix[i][j] = 1;
			}
		}
		for (int i = 0; i < classDiagram.classes.size(); i++) {
			Classs classs = classDiagram.classes.get(i);
			classNames[i] = classs.name;
		}
		for (int i = 0; i < classDiagram.classRelations.size(); i++) {
			ClassRelation relation = classDiagram.classRelations.get(i);
			int s = 0, d = 0;
			for (int j = 0; j < classDiagram.classes.size(); j++) {
				if (relation.sourceClassId
						.matches(classDiagram.classes.get(j).identifier)) {
					s = j;
					break;
				}
			}
			for (int j = 0; j < classDiagram.classes.size(); j++) {
				if (relation.destinationClassId.matches(classDiagram.classes
						.get(j).identifier)) {
					d = j;
					break;
				}
			}
			matrix[s][d] *= relation.relationType.getNumVal();
		}
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (matrix[i][j] == 1)
					matrix[i][j] = 0;
			}
		}
		return matrix;
	}

	public void print() {
		for (int i = 0; i < classNames.length; i++) {
			System.out.print(classNames[i] + " ");
		}
		System.out.println();
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println();
		}
	}
}
