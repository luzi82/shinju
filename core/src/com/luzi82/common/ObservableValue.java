package com.luzi82.common;

import java.util.Observable;

public class ObservableValue<T> extends Observable {

	T mValue;
	
	public ObservableValue(T v){
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
