package com.java.parser.diagram.sequence;

import java.util.ArrayList;

public class SequenceDiagram {
	public ArrayList<String> lifelines;
	public ArrayList<SeqMessage> messages;

	public SequenceDiagram() {
		lifelines = new ArrayList<String>();
		messages = new ArrayList<SeqMessage>();
	}
}
