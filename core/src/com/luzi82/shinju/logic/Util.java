package com.luzi82.shinju.logic;

public class Util {

	public static long len2(long[] a, long[] b) {
		long x = a[0] - b[0];
		long y = a[1] - b[1];
		return x * x + y * y;
	}

	public static long[] sqrt(long x) {
		long min = 0;
		long max = x;

		while (true) {
			if (max == min + 1) {
				return new long[] { min, max };
			}
			long mid = (min + max) >> 1;
			long mid2 = mid * mid;
			if (mid2 == x) {
				return new long[] { mid, mid };
			} else if (mid2 < x) {
				min = mid;
			} else {
				max = mid;
			}
		}
	}

}
