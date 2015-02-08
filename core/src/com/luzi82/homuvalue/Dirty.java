package com.luzi82.homuvalue;

import com.luzi82.common.WeakList;

public class Dirty<T> extends WeakList<Value.Listener<T>> {

	private boolean dirty;
	private final Value<T> iParent;

	public Dirty(Value<T> aParent) {
		iParent = aParent;
		dirty = true;
	}

	public void set(boolean v) {
		if (dirty == v)
			return;
		dirty = v;
		if (!dirty)
			return;
		clearNull();
		for (Value.Listener<T> l : this) {
			l.onValueDirty(iParent);
		}
	}

	public boolean get() {
		return dirty;
	}

}
