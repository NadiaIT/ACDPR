package com.java.recommender.antipattern.training;

import java.io.File;
import java.util.ArrayList;

import com.java.recommender.structures.AntiPattern;

public class AntiPatternTrainer {

	ArrayList<File> classXMLs, activityXMLs;
	AntiPattern antiPattern;

	public AntiPatternTrainer(AntiPattern antiPattern,
			ArrayList<File> classXMLs, ArrayList<File> activityXMLs) {
		this.classXMLs = classXMLs;
		this.activityXMLs = activityXMLs;
		this.antiPattern = antiPattern;
	}

	public AntiPattern train() {
		StructuralTraining structuralTraining = new StructuralTraining(
				classXMLs);
		antiPattern.structuralMetric = structuralTraining.train();
		return antiPattern;
	}

	public void loadTrained() {
	}
}
