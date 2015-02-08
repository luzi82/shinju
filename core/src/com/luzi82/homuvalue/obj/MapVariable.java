package com.luzi82.homuvalue.obj;

import java.util.Map;

import com.luzi82.homuvalue.Variable;

public abstract interface MapVariable<K, I, O> extends Variable<Map<K, O>>, Map<K, I> {

	public void addChangeListener(ChangeListener<K, I> aListener);

	public void removeChangeListener(ChangeListener<K, I> aListener);

	public static interface ChangeListener<K, I> {

		public void onAdd(K aK, I aI);

		public void onRemove(K aK, I aI);

	}

}
