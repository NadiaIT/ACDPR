package com.java.recommender.runner;

import java.io.File;
import java.util.ArrayList;

public class XMLReader {

	ArrayList<File> classXMLs;
	ArrayList<File> sequenceXMLs;
	ArrayList<File> activityXMLs;

	public XMLReader(String path) {
		// System.out.println(path);
		classXMLs = new ArrayList<File>();
		sequenceXMLs = new ArrayList<File>();
		activityXMLs = new ArrayList<File>();
		try {
			File folder = new File(path);
			categorizeFilesFolderwise(folder.listFiles());
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	public ArrayList<File> getClassXMLs() {
		return classXMLs;
	}

	public ArrayList<File> getSequenceXMLs() {
		return sequenceXMLs;
	}

	public ArrayList<File> getActivityXMLs() {
		return activityXMLs;
	}

	public void categorizeFilesFolderwise(File[] listOfFiles) {
		if (listOfFiles != null) {
			for (File file : listOfFiles) {
				if (file.isDirectory()) {
					if (file.getName().contains("Class")) {
						getAllfiles(file.listFiles(), "Class");
					} else if (file.getName().contains("Sequence")) {
						getAllfiles(file.listFiles(), "Sequence");
					} else if (file.getName().contains("Activity")) {
						getAllfiles(file.listFiles(), "Activity");
					}
				}
			}
		}
	}

	public void getAllfiles(File[] listOfFiles, String xmlType) {
		for (File file : listOfFiles) {
			String fileName = file.getName();
			boolean isXML = fileName.substring(fileName.lastIndexOf(".") + 1,
					fileName.length()).equals("xml");
			if (file.isFile() && isXML) {
				if (xmlType.matches("Class")) {
					classXMLs.add(file);
				} else if (xmlType.matches("Sequence")) {
					sequenceXMLs.add(file);
				} else if (xmlType.matches("Activity")) {
					activityXMLs.add(file);
				}
			} else if (file.isDirectory()) {
				getAllfiles(file.listFiles(), xmlType);
			}
		}
	}
}
