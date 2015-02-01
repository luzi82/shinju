package com.luzi82.shinju.logic;

import com.luzi82.homuvalue.obj.ObjectVariable;

public class Hp extends ObjectVariable {

	public final ObjectField<Long> value;

	public final ObjectField<Long> max;

	public Hp() {
		value = new ObjectField<Long>("value");
		addField(value);
		max = new ObjectField<Long>("max");
		addField(max);
	}

}
