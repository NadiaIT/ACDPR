package com.java.parser.diagram.classs;

import java.util.ArrayList;

public class ClassDiagram {
	public ArrayList<Classs> classes;
	public ArrayList<ClassRelation> classRelations;

	public ClassDiagram() {
		classes = new ArrayList<Classs>();
		classRelations = new ArrayList<ClassRelation>();
	}
}
