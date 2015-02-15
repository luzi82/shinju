package com.luzi82.shinju.logic;

import com.luzi82.homuvalue.obj.ObjectVariable;

public class Mp extends ObjectVariable {

	public final ObjectField<Long> value;

	public final ObjectField<Long> max;
	
	public final ObjectField<Long> min;

	public Mp() {
		value = new ObjectField<Long>("value");
		addField(value);
		max = new ObjectField<Long>("max");
		addField(max);
		min = new ObjectField<Long>("min");
		addField(min);
	}

}
