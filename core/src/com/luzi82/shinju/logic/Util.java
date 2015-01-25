package com.luzi82.shinju.logic;

public class Util {

	public static long len2(long[] a, long[] b) {
		long x = a[0] - b[0];
		long y = a[1] - b[1];
		return x * x + y * y;
	}

}
