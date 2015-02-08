package com.luzi82.homuvalue;

public class Slot<T> extends AbstractDynamic<T> implements Value.Listener<T> {
	private Value<T> v;
	private T def;

	public Slot(T t) {
		this.def = t;
	}

	public void set(Value<T> v) {
		if (this.v == v)
			return;
		if (dirty()) {
			changeV(v);
			return;
		}
		if (v == null) {
			if (get() != def) {
				changeV(v);
				markDirty();
			} else {
				changeV(v);
			}
			return;
		}
		if (v.dirty()) {
			changeV(v);
			markDirty();
			return;
		}
		T oldV = get();
		T newV = v.get();
		if (oldV != newV) {
			changeV(v);
			markDirty();
			return;
		}
		changeV(v);
	}

	private void changeV(Value<T> v) {
		if (this.v != null)
			this.v.removeListener(this);
		this.v = v;
		if (this.v != null)
			this.v.addListener(this);
	}

	@Override
	public T update() {
		if (v == null) {
			return def;
		}
		return v.get();
	}

	@Override
	public void onValueDirty(Value<T> v) {
		markDirty();
	}
}