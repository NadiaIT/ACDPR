package com.java.parser.diagram.classs;

public enum CLASS_RELATION_TYPE {
	ASSOCIATION(2), GENERALIZATION(3), AGGREGATION(5);
	
	private final int id;
	CLASS_RELATION_TYPE(int numVal) {
        this.id = numVal;
    }

    public int getNumVal() {
        return id;
    }
}
