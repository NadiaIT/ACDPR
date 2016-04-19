package com.java.recommender.runner.UI;

import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import com.java.recommender.structures.AntiPattern;

@SuppressWarnings("serial")
public class Frame extends JFrame {

	public JFrame frame;
	JTextField selectedPath;
	JButton train;
	JTextField selectedProjectPath;
	JButton recommend;
	AntiPattern antiPattern;

	public Frame() {
		antiPattern = new AntiPattern();
		antiPattern.identifier = "001";
		antiPattern.designPatternId = 1;
		Nimbus nimbus = new Nimbus();
		nimbus.looks();
		frame = this;
		frame.setTitle("Design Pattern Recommneder");
		initComponent();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 300);
	}

	public void initComponent() {
		JTabbedPane pane = new JTabbedPane();
		JPanel antipatternAnalysis = new TrainerTab();
		JPanel designPatternRecommender = new RecommenderTab();
		pane.add(antipatternAnalysis);
		pane.add(designPatternRecommender);
		frame.add(pane);
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				Frame f = new Frame();
				f.setVisible(true);
			}
		});
	}
}
