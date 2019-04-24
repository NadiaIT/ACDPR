package com.java.recommender.runner;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

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

		String antiPatternMetricPath = "C:\\Users\\Fazle\\Documents\\GitHub\\ACDPRAntiPatterns";
		String systemPath = "C:\\Users\\Fazle\\Documents\\GitHub\\ACDPR-dataset\\AbstractFactory\\Project1_CarOil";
		double[] weights = new double[3];
		weights[0] = 1;
		weights[1] = 2;
		weights[2] = 4;
		double threshold = 0.33;
		String wordNetPath = "E:\\Software Center\\WordNet-3.0\\dict\\";
		try {
			FileReader fileReader = new FileReader("configuration.config");
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			wordNetPath = bufferedReader.readLine().replace("WordNetPath###", "");
			System.out.println("Word Net Path="+wordNetPath);
			antiPatternMetricPath = bufferedReader.readLine().replace("AntiPatternPath###", "");
			System.out.println("Anti-patterns Path="+antiPatternMetricPath);
			systemPath = bufferedReader.readLine().replace("ProjectPath###", "");
			System.out.println("Project Path="+systemPath);
			weights[0] = Double.parseDouble(bufferedReader.readLine().replace("Alpha###", ""));
			System.out.println("alpha="+weights[0]);
			weights[1] = Double.parseDouble(bufferedReader.readLine().replace("Beta###", ""));
			System.out.println("beta="+weights[1]);
			weights[2] = Double.parseDouble(bufferedReader.readLine().replace("Gamma###", ""));
			System.out.println("gamma="+weights[2]);
			threshold = Double.parseDouble(bufferedReader.readLine().replace("Threshold###", ""));
			System.out.println("threshold="+threshold);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.setProperty("wordnet.database.dir", wordNetPath);
		XMLReader AFReader = new XMLReader(antiPatternMetricPath
				+ "\\Abstract Factory");
		XMLReader FMReader = new XMLReader(antiPatternMetricPath
				+ "\\Factory Method");
		AntiPattern[] antiPatterns = new AntiPattern[2];
		antiPatterns[0] = new AntiPattern();
		antiPatterns[1] = new AntiPattern();
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
		// systemStructure.print();
		BehavioralMetric behavioralMetric = new BehavioralMetric(
				systemXmlReader.getSequenceXMLs(),
				systemXmlReader.getActivityXMLs());
		AntiPatternMatching matching = new AntiPatternMatchingAF(
				antiPatterns[0], systemStructure, behavioralMetric, weights);
		if (matching.match())
			System.out.println("Recommend Abstract Factory, score="
					+ matching.getScore());
		else if (matching.getScore() >= threshold)
			System.out.println("Suggest Abstract Factory, score="
					+ matching.getScore());
		else
			System.out.println("No Recommendation-AF, score="
					+ matching.getScore());
		matching = new AntiPatternMatchingFM(antiPatterns[1], systemStructure,
				behavioralMetric, weights);
		if (matching.match())
			System.out.println("Recommend Factory Method, score="
					+ matching.getScore());
		else if (matching.getScore() >= threshold)
			System.out.println("Suggest Factory Method, score="
					+ matching.getScore());
		else
			System.out.println("No Recommendation-FM, score="
					+ matching.getScore());
		matching = new AntiPatternMatchingBuilder(antiPatterns[0],
				systemStructure, behavioralMetric, weights);
		if (matching.match()) {
			System.out.println("Recommend Builder, score="
					+ matching.getScore());
			AntiPatternMatchingBuilder matchingBuilder = (AntiPatternMatchingBuilder) matching;
			for (int i = 0; i < matchingBuilder.builderClasses.size(); i++) {
				System.out.println("Recommend Builder to Class: "
						+ matchingBuilder.builderClasses.get(i));
			}
		} else if (matching.getScore() >= threshold)
			System.out.println("Suggest Builder, score=" + matching.getScore());
		else
			System.out.println("No Recommendation-B, score="
					+ matching.getScore());
		matching = new AntiPatternMatchingPrototype(antiPatterns[0],
				systemStructure, behavioralMetric, weights);
		if (matching.match()) {
			System.out.println("Recommend Prototype, score="
					+ matching.getScore());
			AntiPatternMatchingPrototype matchingPrototype = (AntiPatternMatchingPrototype) matching;
			for (int i = 0; i < matchingPrototype.prototypeClasses.size(); i++) {
				System.out.println("Recommend Prototype to Class: "
						+ matchingPrototype.prototypeClasses.get(i));
			}
		} else if (matching.getScore() >= threshold)
			System.out.println("Suggest Prototype, score="
					+ matching.getScore());
		else
			System.out.println("No Recommendation-P, score="
					+ matching.getScore());
		matching = new AntiPatternMatchingSingletone(antiPatterns[0],
				systemStructure, behavioralMetric, weights);
		if (matching.match()) {
			System.out.println("Recommend Singleton, score="
					+ matching.getScore());
			AntiPatternMatchingSingletone matchingSingleton = (AntiPatternMatchingSingletone) matching;
			for (int i = 0; i < matchingSingleton.singletoneClasses.size(); i++) {
				System.out.println("Recommend Singletone to Class: "
						+ matchingSingleton.singletoneClasses.get(i));
			}
		} else if (matching.getScore() >= threshold)
			System.out.println("Suggest Singleton, score="
					+ matching.getScore());
		else
			System.out.println("No Recommendation-S, score="
					+ matching.getScore());
	}
}
