package com.luzi82.shinju.logic;

import com.luzi82.homuvalue.obj.ObjectVariable;

public class Hp extends ObjectVariable {

	public final ObjectField<Long> value = new ObjectField<Long>("value", this);

	public final ObjectField<Long> max = new ObjectField<Long>("max", this);

}
