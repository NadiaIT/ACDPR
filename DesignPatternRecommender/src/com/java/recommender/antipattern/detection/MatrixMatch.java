package com.java.recommender.antipattern.detection;

public class MatrixMatch {

	int[][] antiPattern, system;
	int pN, sN;

	public boolean naiveMatch(int[][] antiPattern, int[][] system) {

		this.antiPattern = antiPattern;
		this.system = system;
		pN = antiPattern.length;
		sN = system.length;
		int[] permuteIndex = new int[sN];
		for (int i = 0; i < sN; i++) {
			permuteIndex[i] = i;
		}
		do {
			int i;
			for (i = 0; i < pN; i++) {
				int j;
				for (j = 0; j < pN; j++) {
					if (antiPattern[i][j] != system[permuteIndex[i]][permuteIndex[j]]) {
						break;
					}
				}
				if (j != pN) {
					break;
				}
			}
			if (i == pN) {
				return true;
			}
		} while ((permuteIndex = nextPermutation(permuteIndex)) != null);
		return false;
	}

	private int[] nextPermutation(final int[] c) {
		int first = getFirst(c);
		if (first == -1)
			return null;
		int toSwap = c.length - 1;
		while (c[first] >= (c[toSwap]))
			--toSwap;
		swap(c, first++, toSwap);
		toSwap = c.length - 1;
		while (first < toSwap)
			swap(c, first++, toSwap--);
		return c;
	}

	private int getFirst(final int[] c) {
		for (int i = c.length - 2; i >= 0; --i)
			if (c[i] < c[i + 1])
				return i;
		return -1;
	}

	private void swap(final int[] c, final int i, final int j) {
		final int tmp = c[i];
		c[i] = c[j];
		c[j] = tmp;
	}

}
