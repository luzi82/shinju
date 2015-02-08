package com.luzi82.homuvalue.obj;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

import com.luzi82.common.Factory;
import com.luzi82.homuvalue.AbstractVariable;
import com.luzi82.homuvalue.Value;
import com.luzi82.homuvalue.Variable;

public class VariableListVariable<I extends Variable<O>, O> extends AbstractListVariable<I, O> {

	private final Factory<I> mConstructor;

	public VariableListVariable(Factory<I> c) {
		this.mConstructor = c;
	}

	private Listener<O> mListener = new Listener<O>() {
		@Override
		public void onValueDirty(Value<O> v) {
			mDirty.set(true);
		}
	};

	@Override
	protected O toO(I i) {
		return i.get();
	}

	@Override
	protected I toI(O o) {
		I ret = mConstructor.create();
		ret.set(o);
		return ret;
	}

	protected void beforeAdd(I e) {
		e.addListener(mListener);
		super.beforeAdd(e);
	}

	protected void afterRemove(I e) {
		super.afterRemove(e);
		e.removeListener(mListener);
	}

	public static class F<I extends Variable<O>, O> implements Factory<VariableListVariable<I, O>> {

		protected final Factory<I> iF;

		public F(Factory<I> aF) {
			iF = aF;
		}

		@Override
		public VariableListVariable<I, O> create() {
			return new VariableListVariable<I, O>(iF);
		}

	}

}
