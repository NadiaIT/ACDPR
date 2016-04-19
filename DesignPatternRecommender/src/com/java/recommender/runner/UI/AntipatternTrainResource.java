package com.java.recommender.runner.UI;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

import com.java.recommender.structures.AntiPattern;
import com.java.recommender.structures.StructuralMetric;

public class AntipatternTrainResource {
	File resource;
	private Scanner scanner;

	public AntipatternTrainResource() {
		resource = new File(this.getClass().getResource("antipattern.txt")
				.getFile());
	}

	public void addToResource(AntiPattern antiPattern) {
		System.out.println(resource.getAbsolutePath().replaceAll("\\\\", "//"));
		File file = new File(resource.getAbsolutePath().replaceAll("\\\\", "//"));
		try {
			PrintWriter append = new PrintWriter(new BufferedWriter(
					new FileWriter(file, true)));
			for (int i = 0; i < antiPattern.structuralMetric.size(); i++) {
				StructuralMetric sMetric = antiPattern.structuralMetric.get(i);
				append.println();
				append.print("Classes: ");
				for (int j = 0; j < sMetric.classNames.length; j++) {
					append.print(sMetric.classNames[j] + " ");
				}
				append.println();
				for (int j = 0; j < sMetric.matrix.length; j++) {
					for (int k = 0; k < sMetric.matrix.length; k++) {
						append.print(sMetric.matrix[j][k] + " ");
					}
					append.println();
				}
				append.println("#");
			}
			append.close();
		} catch (IOException e) {
			System.out.println("Unable to write");
			e.printStackTrace();
		}
	}

	public AntiPattern readResource() {
		AntiPattern antiPattern = new AntiPattern();
		antiPattern.identifier = "001";
		antiPattern.designPatternId = 1;
		try {
			scanner = new Scanner(resource);
			int n = 0;
			int[][] matrix = new int[n][n];
			String[] classNames = new String[n];
			String str;
			while (scanner.hasNext()) {
				str = scanner.nextLine();
				if (str.contains("#")) {
					for (int i = 0; i < n; i++) {
						System.out.print(classNames[i] + " ");
					}
					System.out.println();
					for (int i = 0; i < n; i++) {
						for (int j = 0; j < classNames.length; j++) {
							System.out.print(matrix[i][j] + " ");
						}
						System.out.println();
					}
					antiPattern.structuralMetric.add(new StructuralMetric(
							classNames, matrix));
				} else if (str.contains("Classes:")) {
					str = str.replace("Classes: ", "");
					classNames = str.split("\\s+");
					n = classNames.length;
					matrix = new int[n][n];
				} else {
					String[] firstRow = str.split("\\s+");
					for (int i = 0; i < firstRow.length; i++) {
						matrix[0][i] = Integer.valueOf(firstRow[i]);
					}
					for (int i = 1; i < n; ++i) {
						for (int j = 0; j < n; ++j) {
							if (scanner.hasNextInt()) {
								matrix[i][j] = scanner.nextInt();
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Resource not found");
		}
		return antiPattern;
	}

	public void resetResource() {
		File resetRes = new File(this.getClass().getResource("antipatternReset.txt")
				.getFile());
		InputStream input = null;
		OutputStream output = null;
		try {
			input = new FileInputStream(resetRes);
			output = new FileOutputStream(resource);
			byte[] buf = new byte[1024];
			int bytesRead;
			while ((bytesRead = input.read(buf)) > 0) {
				output.write(buf, 0, bytesRead);
			}
			input.close();
			output.close();
		}
		catch(Exception e) {
			System.out.println("Cannot reset");
		}
	}
	// public static void main(String[] args) {
	// AntipatternTrainResource antipatternTrainResource = new
	// AntipatternTrainResource();
	// antipatternTrainResource.readResource();
	// }
}
