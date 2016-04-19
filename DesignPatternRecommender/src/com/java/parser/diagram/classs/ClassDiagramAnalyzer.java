package com.java.parser.diagram.classs;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ClassDiagramAnalyzer {

	private ClassDiagram classDiagram;

	public ClassDiagram generateClassDiagram(File classXML) {
		classDiagram = new ClassDiagram();
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			if (classXML != null) {
				Document doc = dBuilder.parse(classXML);
				doc.getDocumentElement().normalize();
				Node parentNode = doc.getElementsByTagName("packagedElement")
						.item(0);
				identifyClassElements(parentNode);
				for (int i = 0; i < classDiagram.classes.size(); i++) {
					for (int j = 0; j < classDiagram.classes.get(i).variables
							.size(); j++) {
						classDiagram.classes.get(i).variables.get(j).type = getVariableType(classDiagram.classes
								.get(i).variables.get(j).type);
					}
				}
			}
		} catch (Exception e) {
			System.err.println("Class Diagram Analyzing Failed");
			e.printStackTrace();
		}
		return classDiagram;
	}

	private void identifyClassElements(Node parentNode) {
		NodeList childs = parentNode.getChildNodes();
		for (int i = 0; i < childs.getLength(); i++) {
			if (childs.item(i).getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) childs.item(i);
				if (eElement.getAttribute("xmi:type").matches("uml:Class")
						|| eElement.getAttribute("xmi:type").matches(
								"uml:Interface")) {

					Classs classs = new Classs();
					classs.identifier = eElement.getAttribute("xmi:id")
							.replaceAll("[^a-zA-Z0-9]+", "");
					if (eElement.getAttribute("xmi:type").matches(
							"uml:Interface")) {
						classs.isInterface = true;
					} else {
						classs.isInterface = false;
					}
					classs.name = eElement.getAttribute("name");
					classs.isAbstract = eElement.getAttribute("isAbstract") == "true" ? true
							: false;
					classs.variables = getVariables(eElement, "ownedAttribute");
					classs.methods = getMethods(eElement);
					classDiagram.classes.add(classs);
					identifyClassConnections(eElement);
				}
			}
		}
	}

	private void identifyClassConnections(Node parentNode) {
		NodeList childs = parentNode.getChildNodes();
		for (int i = 0; i < childs.getLength(); i++) {
			if (childs.item(i).getNodeType() == Node.ELEMENT_NODE) {
				if (childs.item(i).getNodeName() == "ownedMember") {
					Element eElement = (Element) childs.item(i);
					if (eElement.getAttribute("xmi:type").matches(
							"uml:Association")) {
						ClassRelation classRelation = new ClassRelation();
						classRelation.relationType = CLASS_RELATION_TYPE.ASSOCIATION;
						NodeList relation = eElement
								.getElementsByTagName("ownedEnd");
						if (relation.getLength() >= 2) {
							Element relationItem = (Element) relation.item(0);
							classRelation.sourceClassId = relationItem
									.getAttribute("type").replaceAll(
											"[^a-zA-Z0-9]+", "");
							relationItem = (Element) relation.item(1);
							classRelation.destinationClassId = relationItem
									.getAttribute("type").replaceAll(
											"[^a-zA-Z0-9]+", "");
						}
						classDiagram.classRelations.add(classRelation);
					}
				} else if (childs.item(i).getNodeName() == "generalization") {
					ClassRelation classRelation = new ClassRelation();
					classRelation.relationType = CLASS_RELATION_TYPE.GENERALIZATION;
					Element relationItem = (Element) childs.item(i);
					classRelation.sourceClassId = relationItem.getAttribute(
							"specific").replaceAll("[^a-zA-Z0-9]+", "");
					classRelation.destinationClassId = relationItem
							.getAttribute("general").replaceAll(
									"[^a-zA-Z0-9]+", "");
					classDiagram.classRelations.add(classRelation);

				} else if (childs.item(i).getNodeName() == "interfaceRealization") {
					ClassRelation classRelation = new ClassRelation();
					classRelation.relationType = CLASS_RELATION_TYPE.GENERALIZATION;
					Element relationItem = (Element) childs.item(i);
					classRelation.sourceClassId = relationItem.getAttribute(
							"contract").replaceAll("[^a-zA-Z0-9]+", "");
					classRelation.destinationClassId = relationItem
							.getAttribute("implementingClassifier".replaceAll(
									"[^a-zA-Z0-9]+", ""));
					classDiagram.classRelations.add(classRelation);
				}
			}
		}
	}

	private ArrayList<Variable> getVariables(Element parentNode, String tagName) {
		ArrayList<Variable> variables = new ArrayList<>();
		NodeList childs = parentNode.getChildNodes();
		for (int i = 0; i < childs.getLength(); i++) {
			if (childs.item(i).getNodeType() == Node.ELEMENT_NODE
					&& childs.item(i).getNodeName() == tagName) {
				Variable variable = new Variable();
				Element eElement = (Element) childs.item(i);
				variable.name = eElement.getAttribute("name");
				variable.type = eElement.getAttribute("type");
				variable.visibility = eElement.getAttribute("visibility");
				variables.add(variable);
			}
		}
		return variables;
	}

	private String getVariableType(String typeString) {
		if (typeString.contains("_id"))
			return typeString.replace("_id", "");
		String type = null;
		for (int i = 0; i < classDiagram.classes.size(); i++) {
			if (classDiagram.classes.get(i).identifier.matches(typeString
					.replaceAll("[^a-zA-Z0-9]+", ""))) {
				type = classDiagram.classes.get(i).name;
				break;
			}
		}
		return type;
	}

	private ArrayList<Method> getMethods(Element parentNode) {
		ArrayList<Method> methods = new ArrayList<>();
		NodeList childs = parentNode.getChildNodes();
		for (int i = 0; i < childs.getLength(); i++) {
			if (childs.item(i).getNodeType() == Node.ELEMENT_NODE
					&& childs.item(i).getNodeName() == "ownedOperation") {
				Method method = new Method();
				Element eElement = (Element) childs.item(i);
				method.name = eElement.getAttribute("name");
				method.visibility = eElement.getAttribute("visibility");
				method.parameters = getVariables(eElement, "ownedParameter");
				for (int j = 0; j < method.parameters.size(); j++) {
					if (method.parameters.get(j).name == "") {
						method.returnType = method.parameters.get(j).type;
						method.parameters.remove(j);
					}
				}
				methods.add(method);
			}
		}
		return methods;
	}

	public void printClassRelations() {
		for (int i = 0; i < classDiagram.classRelations.size(); i++) {
			ClassRelation relation = classDiagram.classRelations.get(i);
			for (int j = 0; j < classDiagram.classes.size(); j++) {
				Classs classs = classDiagram.classes.get(j);
				if (relation.destinationClassId.matches(classs.identifier)) {
					System.out.println(classs.name);
				}
			}
			for (int j = 0; j < classDiagram.classes.size(); j++) {
				Classs classs = classDiagram.classes.get(j);
				if (relation.sourceClassId.matches(classs.identifier)) {
					System.out.println(classs.name);

				}
			}
			System.out.println(classDiagram.classRelations.get(i).relationType);
		}
	}

	public void printClasses() {
		for (int j = 0; j < classDiagram.classes.size(); j++) {
			Classs classs = classDiagram.classes.get(j);
			System.out.println(classs.name);
		}
	}
}
