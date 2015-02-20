package com.luzi82.shinju.logic;

import com.luzi82.homuvalue.obj.ObjectVariable;

public class Rand extends ObjectVariable {

	public final ObjectField<Long> seed = new ObjectField<Long>("seed", this);

	public int nextInt() {
		long oldseed, nextseed;
		oldseed = seed.get();
		nextseed = (oldseed * multiplier + addend) & mask;
		seed.set(nextseed);
		return (int) (nextseed >>> (48 - 32));
	}

	private final static long multiplier = 0x5DEECE66DL;
	private final static long addend = 0xBL;
	private final static long mask = (1L << 48) - 1;

}
