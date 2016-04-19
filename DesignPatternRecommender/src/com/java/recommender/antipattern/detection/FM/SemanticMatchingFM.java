package com.java.recommender.antipattern.detection.FM;

import java.util.ArrayList;

import com.java.parser.diagram.sequence.SequenceDiagram;
import com.java.recommender.antipattern.detection.SemanticMatching;
import com.java.recommender.structures.BehavioralMetric;

public class SemanticMatchingFM extends SemanticMatching {

	BehavioralMetric behavioralMetric;
	int[][] system;
	int[][] productType;
	int[][] sequnece;
	String[] classNames;
	int n;

	public SemanticMatchingFM(int[][] system, String[] classNames,
			BehavioralMetric behavioralMetric) {
		this.behavioralMetric = behavioralMetric;
		this.system = system;
		this.classNames = classNames;
		n = system.length;
		productType = new int[n][n];
		int size = behavioralMetric.sequenceDiagrams.size();
		sequnece = new int[size][size];
	}

	public int matchSemantic() {
		generateProductTypeMatrix();
		// System.out.println("Product Type Mat");
		// printProductTypeMatrix();
		int size = sequnece.length;
		for (int i = 0; i < size; i++) {
			for (int j = i + 1; j < size; j++) {
				compareTwoSequences(behavioralMetric.sequenceDiagrams.get(i),
						behavioralMetric.sequenceDiagrams.get(j), i, j);
			}
		}
		int maxMatch = 0;
		for (int i = 0; i < sequnece.length; i++) {
			for (int j = 0; j < sequnece.length; j++) {
				if (maxMatch < sequnece[i][j])
					maxMatch = sequnece[i][j];
			}
		}
		// System.out.println("max=" + maxMatch);
		if (maxMatch == 1)
			return maxMatch;
		else {
			conductNamingMatch(size);
		}
		for (int i = 0; i < sequnece.length; i++) {
			for (int j = 0; j < sequnece.length; j++) {
				sequnece[i][j] = 0;
				sequnece[j][i] = 0;
			}
		}
		maxMatch = 0;
		for (int i = 0; i < sequnece.length; i++) {
			for (int j = 0; j < sequnece.length; j++) {
				if (maxMatch < sequnece[i][j])
					maxMatch = sequnece[i][j];
			}
		}
		return maxMatch == 1 ? maxMatch : 0;
	}

	private void conductNamingMatch(int size) {
		for (int i = 0; i < size; i++) {
			for (int j = i + 1; j < size; j++) {
				compareTwoSequencesByName(
						behavioralMetric.sequenceDiagrams.get(i),
						behavioralMetric.sequenceDiagrams.get(j), i, j);
			}
		}
	}

	private void compareTwoSequences(SequenceDiagram s1, SequenceDiagram s2,
			int pos1, int pos2) {

		ArrayList<String> newSeq1 = new ArrayList<String>();
		newSeq1.addAll(s1.lifelines);
		newSeq1.removeAll(s2.lifelines);
		ArrayList<String> newSeq2 = new ArrayList<String>();
		newSeq2.addAll(s2.lifelines);
		newSeq2.removeAll(s1.lifelines);
		for (int i = 0; i < newSeq1.size(); i++) {
			for (int j = 0; j < newSeq2.size(); j++) {
				if (newSeq1.get(i).matches(newSeq2.get(j))) {
					continue;
				} else {
					int s = -1, d = -1;
					for (int k = 0; k < classNames.length; k++) {
						if (newSeq1.get(i).toLowerCase()
								.matches(classNames[k].toLowerCase())) {
							s = k;
						}
						if (newSeq2.get(j).toLowerCase()
								.matches(classNames[k].toLowerCase())) {
							d = k;
						}
						if (s != -1 && d != -1) {
							break;
						}
					}
					if (s != -1 && d != -1) {
						sequnece[pos1][pos2] += productType[s][d];
						sequnece[pos2][pos1] += productType[s][d];
					}
				}
			}
		}
	}

	private void compareTwoSequencesByName(SequenceDiagram s1,
			SequenceDiagram s2, int pos1, int pos2) {
		ArrayList<String> newSeq1 = new ArrayList<String>();
		newSeq1.addAll(s1.lifelines);
		newSeq1.removeAll(s2.lifelines);
		ArrayList<String> newSeq2 = new ArrayList<String>();
		newSeq2.addAll(s2.lifelines);
		newSeq2.removeAll(s1.lifelines);
		for (int i = 0; i < newSeq1.size(); i++) {
			for (int j = 0; j < newSeq2.size(); j++) {
				if (newSeq1.get(i).matches(newSeq2.get(j))) {
					continue;
				} else {
					int s = -1, d = -1;
					for (int k = 0; k < classNames.length; k++) {
						if (newSeq1.get(i).matches(classNames[k])) {
							s = k;
						}
						if (newSeq2.get(j).matches(classNames[k])) {
							d = k;
						}
						if (s != -1 && d != -1) {
							break;
						}
					}
					if (s != -1 && d != -1) {
						int score = checkNameSimilarityScore(classNames[s],
								classNames[d]);
						if (score > 0) {
							sequnece[pos1][pos2] += 1;
							sequnece[pos2][pos1] += 1;
						}
					}
				}
			}
		}
	}

	private int checkNameSimilarityScore(String string, String string2) {
		String[] split = string.split("(?=\\p{Upper})");
		String[] split2 = string2.split("(?=\\p{Upper})");
		int score = 0;
		for (int i = 0; i < split.length; i++) {
			for (int j = 0; j < split2.length; j++) {
				if (split[i].matches(split2[j]))
					score++;
			}
		}
		return score;
	}

	private void generateProductTypeMatrix() {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (system[i][j] == 3) {
					ArrayList<Integer> sameSuperaClass = new ArrayList<Integer>();
					for (int x = 0; x < n; x++) {
						if (system[x][j] == 3) {
							sameSuperaClass.add(x);
						}
					}
					AddToSameType(sameSuperaClass);
				}
			}
		}
	}

	private void AddToSameType(ArrayList<Integer> sameSuperaClass) {
		for (int i = 0; i < sameSuperaClass.size(); i++) {
			for (int j = 0; j < sameSuperaClass.size(); j++) {
				if (i == j)
					continue;
				productType[sameSuperaClass.get(i)][sameSuperaClass.get(j)] = 1;
			}
		}
	}

	public void printProductTypeMatrix() {
		for (int i = 0; i < productType.length; i++) {
			for (int j = 0; j < productType.length; j++) {
				System.out.print(productType[i][j] + "\t");
			}
			System.out.println();
		}
	}

	public void printSequenceMatrix() {

		for (int i = 0; i < sequnece.length; i++) {
			for (int j = 0; j < sequnece.length; j++) {
				System.out.print(sequnece[i][j] + "\t");
			}
			System.out.println();
		}
	}
}
