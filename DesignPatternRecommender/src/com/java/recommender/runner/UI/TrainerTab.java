package com.java.recommender.runner.UI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.java.recommender.antipattern.training.AntiPatternTrainer;
import com.java.recommender.runner.XMLReader;
import com.java.recommender.structures.AntiPattern;

@SuppressWarnings("serial")
public class TrainerTab extends JPanel {

	JTextField selectedPath;
	JButton train;
	JPanel trainerTab;
	String trainPath;

	public TrainerTab() {
		trainerTab = this;
		initComponent();
		trainPath = "";
	}

	public void initComponent() {
		trainerTab.setName("Anti-pattern Analysis");
		selectedPath = new JTextField();
		selectedPath.setText("No File Path Chosen");
		selectedPath.setEnabled(false);
		JButton antipatterndirectorychoser = new JButton(
				"Select Train Data Path");
		antipatterndirectorychoser.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new java.io.File(
						"F:\\Codes\\DesignPatternRecommender"));
				chooser.setDialogTitle("Antipatterns structures");
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				chooser.setAcceptAllFileFilterUsed(false);

				if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					train.setEnabled(true);
					trainPath = chooser.getSelectedFile().toString();
					selectedPath.setText(trainPath);
				} else {
					System.out.println("No Selection ");
				}
			}
		});
		train = new JButton("Train");
		train.setEnabled(false);
		train.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				XMLReader xmlReader = new XMLReader(trainPath);
				AntiPattern antiPattern = new AntiPattern();
				AntiPatternTrainer trainer = new AntiPatternTrainer(
						antiPattern, xmlReader.getClassXMLs(), xmlReader
								.getSequenceXMLs());
				antiPattern = trainer.train();
				if (antiPattern.structuralMetric.size() > 0) {
					AntipatternTrainResource resource = new AntipatternTrainResource();
					resource.addToResource(antiPattern);
					System.out.println("Done");
					JOptionPane.showMessageDialog(null,
							"Added new anti-pattern successfully");
				} else {
					JOptionPane.showMessageDialog(null,
							"No anti-pattern structure found");
				}
			}
		});
		JButton reset = new JButton("Reset Trainings");
		reset.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				AntipatternTrainResource antipatternTrainResource = new AntipatternTrainResource();
				antipatternTrainResource.resetResource();
				System.out.println("Reset");
				JOptionPane.showMessageDialog(null, "Rollbacked....");
			}
		});
		trainerTab.add(selectedPath);
		trainerTab.add(antipatterndirectorychoser);
		trainerTab.add(train);
		trainerTab.add(reset);
	}
}
