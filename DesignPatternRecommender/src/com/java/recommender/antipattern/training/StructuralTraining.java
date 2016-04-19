package com.java.recommender.antipattern.training;

import java.io.File;
import java.util.ArrayList;

import com.java.parser.diagram.classs.ClassDiagram;
import com.java.parser.diagram.classs.ClassDiagramAnalyzer;
import com.java.recommender.structures.StructuralMetric;

public class StructuralTraining {

	ArrayList<File> classXMLs;
	ArrayList<ClassDiagram> classDiagrams;
	ArrayList<StructuralMetric> structuralMetrics;

	public StructuralTraining(ArrayList<File> classXMLs) {
		this.classXMLs = classXMLs;
		classDiagrams = new ArrayList<ClassDiagram>();
		structuralMetrics = new ArrayList<StructuralMetric>();
	}

	public ArrayList<StructuralMetric> train() {
		ClassDiagramAnalyzer classDiagramAnalyzer = new ClassDiagramAnalyzer();
		for (int i = 0; i < classXMLs.size(); i++) {
			ClassDiagram classDiagram = classDiagramAnalyzer
					.generateClassDiagram(classXMLs.get(i));
			classDiagrams.add(classDiagram);
			StructuralMetric structuralMetric = new StructuralMetric(
					classDiagram);
			structuralMetric.generateStructuralMetric();
			structuralMetrics.add(structuralMetric);
			//structuralMetric.print();
		}
		return structuralMetrics;
	}
}
