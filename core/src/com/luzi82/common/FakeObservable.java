package com.luzi82.common;

import java.util.Observable;

public class FakeObservable extends Observable {

	@Override
	public synchronized void setChanged() {
		super.setChanged();
	}

}