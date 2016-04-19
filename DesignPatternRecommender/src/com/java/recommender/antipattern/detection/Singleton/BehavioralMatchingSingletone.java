package com.java.recommender.antipattern.detection.Singleton;

import java.util.ArrayList;
import java.util.LinkedHashSet;

import com.java.parser.diagram.activity.ActivityDiagram;
import com.java.parser.diagram.activity.ActivityEdge;
import com.java.parser.diagram.activity.ActivityNode;
import com.java.recommender.antipattern.detection.BehavioralMatching;
import com.java.recommender.structures.BehavioralMetric;

import edu.smu.tspell.wordnet.Synset;
import edu.smu.tspell.wordnet.WordNetDatabase;

public class BehavioralMatchingSingletone extends BehavioralMatching {

	BehavioralMetric behavioralMetric;
	public ArrayList<String> singletoneNodes;

	public BehavioralMatchingSingletone(BehavioralMetric metric) {
		behavioralMetric = metric;
		singletoneNodes = new ArrayList<String>();
	}

	@Override
	public int matchBehavior() {
		int maxSigletone = 0;
		behavioralMetric.generateBehavioralMetricFromActivity();
		ArrayList<String> synsets = useWordNetToFindSimilarWords();
		for (int i = 0; i < behavioralMetric.activityDiagrams.size(); i++) {
			ActivityDiagram diagram = behavioralMetric.activityDiagrams.get(i);
			ArrayList<ActivityNode> instantiationNodes = new ArrayList<ActivityNode>();
			for (int j = 0; j < diagram.nodes.size(); j++) {
				for (int s = 0; s < synsets.size(); s++) {
					ActivityNode node = diagram.nodes.get(j);
					if (node.name.toLowerCase().contains(synsets.get(s))) {
						instantiationNodes.add(node);
						break;
					}
				}
			}
			ArrayList<ActivityNode> decisionNodes = new ArrayList<ActivityNode>();
			for (int j = 0; j < instantiationNodes.size(); j++) {
				for (int e = 0; e < diagram.edges.size(); e++) {
					ActivityEdge edge = diagram.edges.get(e);
					if (instantiationNodes.get(j).id.matches(edge.targetNode)) {
						for (int n = 0; n < diagram.nodes.size(); n++) {
							ActivityNode node = diagram.nodes.get(n);
							if (edge.sourceNode.matches(node.id)) {
								if (node.type.matches("DecisionNode")) {
									decisionNodes.add(node);
									break;
								}
							}
						}
					}
				}
			}

			for (int j = 0; j < decisionNodes.size(); j++) {
				ActivityNode decisionNode = decisionNodes.get(j);
				int edgeNo = 0;
				ArrayList<ActivityEdge> decisionEdges = new ArrayList<ActivityEdge>();
				for (int e = 0; e < diagram.edges.size(); e++) {
					ActivityEdge edge = diagram.edges.get(e);
					if (edge.sourceNode.matches(decisionNode.id)) {
						edgeNo++;
						decisionEdges.add(edge);
					}
				}
				if (edgeNo == 2) {
					String nodeId1 = decisionEdges.get(0).targetNode;
					String nodeId2 = decisionEdges.get(1).targetNode;
					for (int k = 0; k < diagram.edges.size(); k++) {
						ActivityEdge activityEdge = diagram.edges.get(k);
						if ((activityEdge.sourceNode.matches(nodeId1) && activityEdge.targetNode
								.matches(nodeId2))
								|| (activityEdge.sourceNode.matches(nodeId2) && activityEdge.targetNode
										.matches(nodeId1))) {
							singletoneNodes.add(nodeId1 + "," + nodeId2);
							break;
						}
					}
				}
			}
			if (singletoneNodes.size() > maxSigletone)
				maxSigletone = singletoneNodes.size();
		}
		return maxSigletone;
	}

	private ArrayList<String> useWordNetToFindSimilarWords() {
		ArrayList<String> allSynset = new ArrayList<>();

		WordNetDatabase database = WordNetDatabase.getFileInstance();
		Synset[] synsets = database.getSynsets("instantiate");
		for (int i = 0; i < synsets.length; i++) {
			for (int j = 0; j < synsets[i].getWordForms().length; j++) {
				allSynset.add(synsets[i].getWordForms()[j]);
			}
		}
		synsets = database.getSynsets("create");
		for (int i = 0; i < synsets.length; i++) {
			for (int j = 0; j < synsets[i].getWordForms().length; j++) {
				allSynset.add(synsets[i].getWordForms()[j]);
			}
		}
		synsets = database.getSynsets("initiate");
		for (int i = 0; i < synsets.length; i++) {
			for (int j = 0; j < synsets[i].getWordForms().length; j++) {
				allSynset.add(synsets[i].getWordForms()[j]);
			}
		}
		synsets = database.getSynsets("load");
		for (int i = 0; i < synsets.length; i++) {
			for (int j = 0; j < synsets[i].getWordForms().length; j++) {
				allSynset.add(synsets[i].getWordForms()[j]);
			}
		}
		allSynset = new ArrayList<String>(new LinkedHashSet<String>(allSynset));
		return allSynset;
	}
}
