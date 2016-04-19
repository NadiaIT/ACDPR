package com.java.recommender.structures;

import java.io.File;
import java.util.ArrayList;

import com.java.parser.diagram.activity.ActivityDiagram;
import com.java.parser.diagram.activity.ActivityDiagramAnalyzer;
import com.java.parser.diagram.sequence.SequenceDiagram;
import com.java.parser.diagram.sequence.SequenceDiagramAnalyzer;

public class BehavioralMetric {
	public ArrayList<SequenceDiagram> sequenceDiagrams;
	ArrayList<File> sequenceXMLs;
	public ArrayList<ActivityDiagram> activityDiagrams;
	ArrayList<File> activityXMLs;

	public BehavioralMetric(ArrayList<File> sequenceXMLs,
			ArrayList<File> activityXMLs) {
		sequenceDiagrams = new ArrayList<SequenceDiagram>();
		this.sequenceXMLs = sequenceXMLs;
		activityDiagrams = new ArrayList<ActivityDiagram>();
		this.activityXMLs = activityXMLs;
	}

	public void generateBehavioralMetricFromSequence() {
		sequenceDiagrams = new ArrayList<SequenceDiagram>();
		SequenceDiagramAnalyzer sequenceDiagramAnalyzer = new SequenceDiagramAnalyzer();
		for (int i = 0; i < sequenceXMLs.size(); i++) {
			sequenceDiagrams.add(sequenceDiagramAnalyzer
					.generateSequenceDiagram(sequenceXMLs.get(i)));
		}
	}

	public void generateBehavioralMetricFromActivity() {
		activityDiagrams = new ArrayList<ActivityDiagram>();
		ActivityDiagramAnalyzer activityDiagramAnalyzer = new ActivityDiagramAnalyzer();
		for (int i = 0; i < activityXMLs.size(); i++) {
			activityDiagrams.add(activityDiagramAnalyzer
					.generateActivityDiagram((activityXMLs.get(i))));
		}
	}
}
