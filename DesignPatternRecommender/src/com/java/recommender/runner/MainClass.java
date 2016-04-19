package com.java.recommender.runner;

import com.java.parser.diagram.classs.ClassDiagramAnalyzer;
import com.java.recommender.antipattern.detection.AntiPatternMatching;
import com.java.recommender.antipattern.detection.AF.AntiPatternMatchingAF;
import com.java.recommender.antipattern.detection.Builder.AntiPatternMatchingBuilder;
import com.java.recommender.antipattern.detection.FM.AntiPatternMatchingFM;
import com.java.recommender.antipattern.detection.Prototype.AntiPatternMatchingPrototype;
import com.java.recommender.antipattern.detection.Singleton.AntiPatternMatchingSingletone;
import com.java.recommender.antipattern.training.AntiPatternTrainer;
import com.java.recommender.structures.AntiPattern;
import com.java.recommender.structures.BehavioralMetric;
import com.java.recommender.structures.StructuralMetric;

public class MainClass {
	public static void main(String[] args) {
		System.setProperty("wordnet.database.dir",
				"F:\\Software Center\\WordNet-3.0\\dict\\");
		String antiPatternMetricPath = "F:\\Codes\\DesignPatternRecommender\\AntiPatternMetrics";
		XMLReader AFReader = new XMLReader(antiPatternMetricPath+"\\Abstract Factory");
		XMLReader FMReader = new XMLReader(antiPatternMetricPath+"\\Factory Method");
		AntiPattern[] antiPatterns = new AntiPattern[2];
		antiPatterns[0]=new AntiPattern();
		antiPatterns[1]=new AntiPattern();
		antiPatterns[0].identifier = "001";
		antiPatterns[0].designPatternId = 1;
		antiPatterns[1].identifier = "001";
		antiPatterns[1].designPatternId = 1;
		AntiPatternTrainer trainerAF = new AntiPatternTrainer(antiPatterns[0],
				AFReader.getClassXMLs(), AFReader.getSequenceXMLs());
		trainerAF.train();
		AntiPatternTrainer trainerFM = new AntiPatternTrainer(antiPatterns[1],
				FMReader.getClassXMLs(), FMReader.getSequenceXMLs());
		trainerFM.train();
		String systemPath = "F:\\Codes\\DesignPatternRecommender\\Projects\\FactoryMethod\\Product";
		XMLReader systemXmlReader = new XMLReader(systemPath);
		ClassDiagramAnalyzer classDiagramAnalyzer = new ClassDiagramAnalyzer();
		if (systemXmlReader.getClassXMLs().size() == 0) {
			System.out.println("No Class Diagram");
			return;
		}
		StructuralMetric systemStructure = new StructuralMetric(
				classDiagramAnalyzer.generateClassDiagram(systemXmlReader
						.getClassXMLs().get(0)));
		systemStructure.generateStructuralMetric();
		//systemStructure.print();
		BehavioralMetric behavioralMetric = new BehavioralMetric(
				systemXmlReader.getSequenceXMLs(),
				systemXmlReader.getActivityXMLs());
		AntiPatternMatching matching = new AntiPatternMatchingAF(antiPatterns[0],
				systemStructure, behavioralMetric);
		if (matching.match())
			System.out.println("Recommend Abstract Factory, score="
					+ matching.getScore());
		else
			System.out.println("No Recommendation-AF, score="
					+ matching.getScore());
		matching = new AntiPatternMatchingFM(antiPatterns[1], systemStructure,
				behavioralMetric);
		if (matching.match())
			System.out.println("Recommend Factory Method, score="
					+ matching.getScore());
		else
			System.out.println("No Recommendation-FM, score="
					+ matching.getScore());
		matching = new AntiPatternMatchingBuilder(antiPatterns[0], systemStructure,
				behavioralMetric);
		if (matching.match()) {
			System.out.println("Recommend Builder, score="
					+ matching.getScore());
			AntiPatternMatchingBuilder matchingBuilder = (AntiPatternMatchingBuilder) matching;
			for (int i = 0; i < matchingBuilder.builderClasses.size(); i++) {
				System.out.println("Recommend Builder to Class: "
						+ matchingBuilder.builderClasses.get(i));
			}
		} else
			System.out.println("No Recommendation-B, score="
					+ matching.getScore());
		matching = new AntiPatternMatchingPrototype(antiPatterns[0],
				systemStructure, behavioralMetric);
		if (matching.match()) {
			System.out.println("Recommend Prototype, score="
					+ matching.getScore());
			AntiPatternMatchingPrototype matchingPrototype = (AntiPatternMatchingPrototype) matching;
			for (int i = 0; i < matchingPrototype.prototypeClasses.size(); i++) {
				System.out.println("Recommend Prototype to Class: "
						+ matchingPrototype.prototypeClasses.get(i));
			}
		} else
			System.out.println("No Recommendation-P, score="
					+ matching.getScore());
		matching = new AntiPatternMatchingSingletone(antiPatterns[0],
				systemStructure, behavioralMetric);
		if (matching.match()) {
			System.out.println("Recommend Singleton, score="
					+ matching.getScore());
			AntiPatternMatchingSingletone matchingSingleton = (AntiPatternMatchingSingletone) matching;
			for (int i = 0; i < matchingSingleton.singletoneClasses.size(); i++) {
				System.out.println("Recommend Singletone to Class: "
						+ matchingSingleton.singletoneClasses.get(i));
			}
		} else
			System.out.println("No Recommendation-S, score="
					+ matching.getScore());
	}
}
