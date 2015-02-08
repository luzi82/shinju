package com.luzi82.homuvalue;


public class Constant<T> extends AbstractValue<T> {
	private final T value;

	public Constant(T t) {
		super(true);
		value = t;
	}

	@Override
	public final T get() {
		return value;
	}

	@Override
	public final void addListener(Value.Listener<T> listener) {
		// do nothing
	}

	@Override
	public void removeListener(Value.Listener<T> intListener) {
		// do nothing
	}

	@Override
	public boolean dirty() {
		return false;
	}

}