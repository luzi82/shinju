package com.luzi82.common;

import java.util.Observable;

public class ValueObservable<T> extends Observable {

	T mValue;
	
	public ValueObservable(T v){
		mValue=v;
	}

	public T get() {
		return mValue;
	}

	public void set(T v) {
		if (mValue.equals(v))
			return;
		mValue = v;
		setChanged();
		notifyObservers(v);
	}

}
