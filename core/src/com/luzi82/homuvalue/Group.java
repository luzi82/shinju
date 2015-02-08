package com.luzi82.homuvalue;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Set;

@SuppressWarnings("rawtypes")
public abstract class Group<V> extends AbstractDynamic<V> implements Value.Listener {

	protected Set<Value> childSet = Collections.newSetFromMap(new IdentityHashMap<Value, Boolean>());

	@SuppressWarnings({ "unchecked" })
	protected void addChild(Value v) {
		childSet.add(v);
		v.addListener(this);
		markDirty();
	}

	protected void removeChild(Value v) {
		childSet.remove(v);
		v.removeListener(this);
		markDirty();
	}

	@Override
	public void onValueDirty(Value v) {
		markDirty();
	}

}
