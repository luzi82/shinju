package com.luzi82.shinju.logic;

import com.luzi82.homuvalue.obj.ObjectVariable;

public class Position extends ObjectVariable {

	public final ObjectField<Long> x;

	public final ObjectField<Long> y;

	public Position() {
		x = new ObjectField<Long>("x");
		addField(x);
		y = new ObjectField<Long>("y");
		addField(y);
	}

	public long[] getXY() {
		return new long[] { x.get(), y.get() };
	}

}
