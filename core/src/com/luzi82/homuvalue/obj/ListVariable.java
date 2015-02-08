package com.luzi82.homuvalue.obj;

import java.util.List;

import com.luzi82.homuvalue.Variable;

public abstract interface ListVariable<I, O> extends Variable<List<O>>, List<I> {

	public void addChangeListener(ChangeListener<I> aListener);

	public void removeChangeListener(ChangeListener<I> aListener);

	public static interface ChangeListener<I> {

		public void onAdd(I aI);

		public void onRemove(I aI);

	}

}
