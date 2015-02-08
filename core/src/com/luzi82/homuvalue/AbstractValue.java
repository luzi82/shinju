package com.luzi82.homuvalue;


public abstract class AbstractValue<T> implements Value<T> {

	public final boolean isConstant;

	public AbstractValue(boolean isConstant) {
		this.isConstant = isConstant;
	}
	
	public boolean isConstant(){
		return isConstant;
	}

}
