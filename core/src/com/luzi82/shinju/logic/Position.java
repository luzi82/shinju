package com.luzi82.shinju.logic;

import com.luzi82.homuvalue.obj.ObjectVariable;

public class Position extends ObjectVariable {

	public final ObjectField<Long> x = new ObjectField<Long>("x", this);

	public final ObjectField<Long> y = new ObjectField<Long>("y", this);

	public long[] getXY() {
		return new long[] { x.get(), y.get() };
	}

}
