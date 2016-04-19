package com.java.parser.diagram.activity;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ActivityDiagramAnalyzer {
	private ActivityDiagram activityDiagram;

	public ActivityDiagram generateActivityDiagram(File activityXML) {
		activityDiagram = new ActivityDiagram();
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			if (activityXML != null) {
				Document doc = dBuilder.parse(activityXML);
				doc.getDocumentElement().normalize();
				Node parentNode = doc.getElementsByTagName("packagedElement")
						.item(1);
				identifyActivityNodes(parentNode);
				identifyActivityEdges(parentNode);
			}
		} catch (Exception e) {
			System.err.println("Activity Diagram Analyzing Failed");
			e.printStackTrace();
		}
		return activityDiagram;
	}

	private void identifyActivityNodes(Node parentNode) {
		NodeList childs = parentNode.getChildNodes();
		for (int i = 0; i < childs.getLength(); i++) {
			if (childs.item(i).getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) childs.item(i);
				if (eElement.getNodeName().matches("node")) {
					ActivityNode activityNode = new ActivityNode();
					activityNode.name = eElement.getAttribute("name");
					activityNode.type = eElement.getAttribute("xmi:type")
							.replace("uml:", "");
					activityNode.id = eElement.getAttribute("xmi:id")
							.replaceAll("[^a-zA-Z0-9]+", "");
					activityDiagram.nodes.add(activityNode);
				}
			}
		}
	}

	private void identifyActivityEdges(Node parentNode) {
		NodeList childs = parentNode.getChildNodes();
		for (int i = 0; i < childs.getLength(); i++) {
			if (childs.item(i).getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) childs.item(i);
				if (eElement.getNodeName().matches("edge")) {
					ActivityEdge activityEdge = new ActivityEdge();
					activityEdge.id = eElement.getAttribute("xmi:id")
							.replaceAll("[^a-zA-Z0-9]+", "");
					activityEdge.sourceNode = eElement.getAttribute("source")
							.replaceAll("[^a-zA-Z0-9]+", "");
					activityEdge.targetNode = eElement.getAttribute("target")
							.replaceAll("[^a-zA-Z0-9]+", "");
					activityDiagram.edges.add(activityEdge);
				}
			}
		}
	}

}
