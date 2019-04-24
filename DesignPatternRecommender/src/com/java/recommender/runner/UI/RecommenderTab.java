package com.java.recommender.runner.UI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

import com.java.parser.diagram.classs.ClassDiagramAnalyzer;
import com.java.recommender.antipattern.detection.AntiPatternMatching;
import com.java.recommender.antipattern.detection.AF.AntiPatternMatchingAF;
import com.java.recommender.runner.XMLReader;
import com.java.recommender.structures.AntiPattern;
import com.java.recommender.structures.BehavioralMetric;
import com.java.recommender.structures.StructuralMetric;

@SuppressWarnings("serial")
public class RecommenderTab extends JPanel {
	JTextField selectedProjectPath;
	JButton recommend;
	JPanel recommenderTab;
	String projectPath;
	JLabel suggession;
	JLabel loader;

	public RecommenderTab() {
		recommenderTab = this;
		initComponent();
		projectPath = "";
	}

	public void initComponent() {
		loader = new JLabel("Loading... ", JLabel.CENTER);
		loader.setVisible(false);
		selectedProjectPath = new JTextField();
		selectedProjectPath.setText("No Project Path Chosen");
		selectedProjectPath.setEnabled(false);
		JButton projectChoser = new JButton("Select Project Path");

		projectChoser.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				suggession.setText("");
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new java.io.File(
						"F:\\Codes\\DesignPatternRecommender"));
				chooser.setDialogTitle("Antipatterns structures");
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				chooser.setAcceptAllFileFilterUsed(false);

				if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					recommend.setEnabled(true);
					projectPath = chooser.getSelectedFile().toString();
					selectedProjectPath.setText(projectPath);
				} else {
					System.out.println("No Selection ");
				}
			}
		});
		recommend = new JButton("Recommend");
		recommend.setEnabled(false);
		recommend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				suggession.setText("");
				loader.setVisible(true);

				new SwingWorker<Void, String>() {
					@Override
					protected Void doInBackground() throws Exception {
						// Worken hard or hardly worken...
						XMLReader systemXmlReader = new XMLReader(projectPath);
						ClassDiagramAnalyzer classDiagramAnalyzer = new ClassDiagramAnalyzer();
						if (systemXmlReader.getClassXMLs().size() <= 0) {
							suggession.setText("Choose a valid project");
							JOptionPane.showMessageDialog(null,
									"Choose a valid project");
							return null;
						}
						StructuralMetric systemStructure = new StructuralMetric(
								classDiagramAnalyzer
										.generateClassDiagram(systemXmlReader
												.getClassXMLs().get(0)));
						systemStructure.generateStructuralMetric();
						systemStructure.print();
						BehavioralMetric behavioralMetric = new BehavioralMetric(
								systemXmlReader.getSequenceXMLs(),
								systemXmlReader.getActivityXMLs());
						AntipatternTrainResource antipatternTrainResource = new AntipatternTrainResource();
						AntiPattern antiPattern = antipatternTrainResource
								.readResource();
						double[] weights = new double[3];
						weights[0]=1;
						weights[0]=2;
						weights[0]=4;
						AntiPatternMatching matching = new AntiPatternMatchingAF(
								antiPattern, systemStructure, behavioralMetric, weights);
						if (matching.match()) {
							suggession
									.setText("Abstract Factory is Recommended");
							JOptionPane.showMessageDialog(null,
									"Abstract Factory is Recommended");
						} else {
							suggession.setText("No Recommendation");
							JOptionPane.showMessageDialog(null,
									"No Recommendation");
						}
						return null;
					}

					@Override
					protected void done() {
						loader.setVisible(false);
					}
				}.execute();
			}
		});
		suggession = new JLabel();
		recommenderTab.setName("Design Pattern Recommender");
		recommenderTab.add(selectedProjectPath);
		recommenderTab.add(projectChoser);
		recommenderTab.add(recommend);
		recommenderTab.add(suggession);
		recommenderTab.add(loader);
	}

}
