package com.java.parser.diagram.sequence;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class SequenceDiagramAnalyzer {
	private SequenceDiagram sequenceDiagram;

	public SequenceDiagram generateSequenceDiagram(File sequenceXML) {
		sequenceDiagram = new SequenceDiagram();
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			if (sequenceXML != null) {
				Document doc = dBuilder.parse(sequenceXML);
				doc.getDocumentElement().normalize();
				Node parentNode = doc.getElementsByTagName("packagedElement")
						.item(1);
				identifySequenceElements(parentNode);
				identifySequenceMessages(parentNode);
			}
		} catch (Exception e) {
			System.err.println("Sequence Diagram Analyzing Failed");
			e.printStackTrace();
		}
		return sequenceDiagram;
	}

	private void identifySequenceElements(Node parentNode) {
		NodeList childs = parentNode.getChildNodes().item(1).getChildNodes();
		for (int i = 0; i < childs.getLength(); i++) {
			if (childs.item(i).getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) childs.item(i);
				if (eElement.getNodeName().matches("lifeline")) {
					sequenceDiagram.lifelines
							.add(eElement.getAttribute("name"));
				}
			}
		}
	}

	private void identifySequenceMessages(Node parentNode) {
		NodeList childs = parentNode.getChildNodes().item(1).getChildNodes();
		for (int i = 0; i < childs.getLength(); i++) {
			if (childs.item(i).getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) childs.item(i);
				if (eElement.getNodeName().matches("message")) {
					SeqMessage message = new SeqMessage();
					message.name = eElement.getAttribute("name");
					message.to = getMessageFromLifeline(
							eElement.getAttribute("receiveEvent").replaceAll(
									"[^a-zA-Z0-9]+", ""), childs);
					message.from = getMessageFromLifeline(
							eElement.getAttribute("sendEvent").replaceAll(
									"[^a-zA-Z0-9]+", ""), childs);
					sequenceDiagram.messages.add(message);
				}
			}
		}
	}

	private String getMessageFromLifeline(String eventId, NodeList nodes) {
		String lifeline = null;
		for (int i = 0; i < nodes.getLength(); i++) {
			if (nodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nodes.item(i);
				if (eElement.getNodeName().matches("fragment")) {
					if (eventId.matches(eElement.getAttribute("xmi:id")
							.replaceAll("[^a-zA-Z0-9]+", ""))) {
						lifeline = eElement.getAttribute("covered").replaceAll(
								"[^a-zA-Z0-9]+", "");
						for (int j = 0; j < nodes.getLength(); j++) {
							if (nodes.item(j).getNodeType() == Node.ELEMENT_NODE) {
								Element elm = (Element) nodes.item(j);
								if (elm.getNodeName().matches("lifeline")) {
									if (lifeline.matches(elm.getAttribute("xmi:id")
											.replaceAll("[^a-zA-Z0-9]+", ""))) {
										lifeline = elm.getAttribute("name").replaceAll(
												"[^a-zA-Z0-9]+", "");
										return lifeline;
									}
								}
							}
						}
					}
				}
			}
		}
		return null;
	}
}
