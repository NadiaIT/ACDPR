package com.java.recommender.structures;

import java.util.ArrayList;

public class DesignPatternCategories {
	public int identifier;
	public String name;
	public ArrayList<DesignPattern> designPatterns;

	public DesignPatternCategories() {
		designPatterns = new ArrayList<DesignPattern>();
	}
}
