package com.luzi82.homuvalue;

public class LocalVariable<T> extends AbstractVariable<T> {
	private T var;

	public LocalVariable() {
	}

	public LocalVariable(T t) {
		var = t;
	}

	@Override
	protected void variableSet(T t) {
		var = t;
	}

	@Override
	protected T variableGet() {
		return var;
	}

}