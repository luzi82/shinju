package com.luzi82.homuvalue;


public interface Value<T> {

	public T get();

	public void addListener(Listener<T> listener);

	public void removeListener(Listener<T> intListener);

	public boolean isConstant();
	
	public boolean dirty();

	public static interface Listener<T> {

		public void onValueDirty(Value<T> v);

	}

}
