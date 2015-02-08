package com.luzi82.homuvalue;

public abstract class AbstractVariable<T> extends AbstractDynamic<T> implements Variable<T> {

	public void set(T t) {
		// System.out.println("AbstractVariable set start dirty:"+dirty());
		if (variableGet() == t)
			return;
		variableSet(t);
		markDirty();
		// System.out.println("AbstractVariable set end dirty:"+dirty());
	}

	@Override
	public T update() {
		return variableGet();
	}

	protected abstract void variableSet(T t);

	protected abstract T variableGet();

}