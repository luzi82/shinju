package com.luzi82.homuvalue;

public abstract class Function<T> extends Group<T> {
	public boolean isConst = true;

	@SuppressWarnings({ "rawtypes" })
	protected void addChild(Value v) {
		super.addChild(v);
		if (isConst && !v.isConstant()) {
			isConst = false;
		}
	}

	public Value<T> optimize() {
		if (isConst) {
			return new Constant<T>(get());
		} else {
			return this;
		}
	}
}