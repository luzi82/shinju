package com.luzi82.homuvalue.obj;

import java.util.Map;

import com.luzi82.common.Factory;
import com.luzi82.homuvalue.Value;
import com.luzi82.homuvalue.Variable;

public class VariableMapVariable<K, I extends Variable<O>, O> extends AbstractMapVariable<K, I, O> {

	private final Factory<I> mConstructor;

	public VariableMapVariable(Factory<I> c) {
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

	@Override
	protected void innerClear() {
		Map<K, I> tmp = (Map<K, I>) mMap.clone();
		super.innerClear();
		for (I i : tmp.values()) {
			i.removeListener(mListener);
		}
	}

	@Override
	protected void innerPut(K key, I value) {
		value.addListener(mListener);
		super.innerPut(key, value);
	}

	@Override
	protected I innerRemove(Object key) {
		I ret = super.innerRemove(key);
		if (ret != null) {
			ret.removeListener(mListener);
		}
		return ret;
	}

	public static <K, I extends Variable<O>, O> Factory<VariableMapVariable<K, I, O>> createFactory(Class<K> aKClass, Factory<I> iF) {
		return new F<K, I, O>(iF);
	}

	public static class F<K, I extends Variable<O>, O> implements Factory<VariableMapVariable<K, I, O>> {

		protected final Factory<I> iF;

		public F(Factory<I> aF) {
			iF = aF;
		}

		@Override
		public VariableMapVariable<K, I, O> create() {
			return new VariableMapVariable<K, I, O>(iF);
		}

	}

}
