package com.luzi82.homuvalue.obj;

import com.luzi82.common.Factory;


public class ObjectListVariable<O> extends AbstractListVariable<O, O> {

	@Override
	protected O toO(O i) {
		return i;
	}

	@Override
	protected O toI(O o) {
		return o;
	}

	public static <O> Factory<ObjectListVariable<O>> createFactory(Class<O> aC) {
		return new Factory<ObjectListVariable<O>>() {
			@Override
			public ObjectListVariable<O> create() {
				return new ObjectListVariable<O>();
			}
		};
	}

}
