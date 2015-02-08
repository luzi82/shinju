package com.luzi82.homuvalue.obj;

import java.util.HashMap;
import java.util.Map;

import com.luzi82.common.Factory;
import com.luzi82.homuvalue.AbstractVariable;
import com.luzi82.homuvalue.Value;
import com.luzi82.homuvalue.Variable;

public class ObjectVariable extends AbstractVariable<Map<String, Object>> {

	protected HashMap<String, Field> mFieldMap = new HashMap<String, Field>();

	@SuppressWarnings("unchecked")
	protected void addField(Field aField) {
		if (mFieldMap.containsKey(aField.getName())) {
			throw new IllegalArgumentException();
		}
		mFieldMap.put(aField.getName(), aField);
		aField.addListener(mListener);
	}

	@SuppressWarnings("rawtypes")
	private Listener mListener = new Listener() {
		@Override
		public void onValueDirty(Value v) {
			// System.out.println("ObjectVariable onValueDirty start dirty: " +
			// dirty());
			ObjectVariable.this.markDirty();
			// System.out.println("ObjectVariable onValueDirty end dirty: " +
			// dirty());
		}
	};

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected void variableSet(Map<String, Object> t) {
		for (Field f : mFieldMap.values()) {
			String k = f.getName();
			f.input(t.get(k));
		}
	}

	@Override
	protected Map<String, Object> variableGet() {
		// System.out.println("ObjectVariable variableGet");
		HashMap<String, Object> ret = new HashMap<String, Object>();
		for (Field f : mFieldMap.values()) {
			// System.out.println(f.getName());
			ret.put(f.getName(), f.output());
		}
		return ret;
	}

	public abstract static class Field<I, O> extends AbstractVariable<I> {

		protected final String mName;

		protected I mObj;

		public Field(String aName) {
			mName = aName;
		}

		public String getName() {
			return mName;
		}

		@Override
		protected void variableSet(I t) {
			mObj = t;
		}

		@Override
		protected I variableGet() {
			return mObj;
		}

		public abstract void input(O o);

		public abstract O output();

	}

	public static class ObjectField<O> extends Field<O, O> {

		public ObjectField(String aName) {
			super(aName);
		}

		@Override
		public void input(O o) {
			set(o);
		}

		@Override
		public O output() {
			return get();
		}

	}

	public static class VarField<I extends Variable<O>, O> extends Field<I, O> {

		private final Factory<I> mConstructor;

		public VarField(String aName, Factory<I> aFactory) {
			super(aName);
			mConstructor = aFactory;
		}

		public VarField(String aName, Class<I> aClass) {
			this(aName, new Factory.C<I>(aClass));
		}

		@Override
		public void input(O o) {
			if (mObj != null) {
				mObj.removeListener(mListener);
			}
			try {
				mObj = mConstructor.create();
			} catch (Exception e) {
				throw new Error(e);
			}
			if (mObj != null) {
				mObj.addListener(mListener);
			}
		}

		@Override
		public O output() {
			// System.out.println("field " + getName() + " output start");
			I i = get();
			if (i == null) {
				return null;
			}
			// System.out.println("field " + getName() + " output 0");
			return i.get();
		}

		private Listener<O> mListener = new Listener<O>() {
			@Override
			public void onValueDirty(Value<O> v) {
				// System.out.println("field " + VarField.this.getName() +
				// " dirty");
				VarField.this.markDirty();
			}
		};

		@Override
		protected void variableSet(I t) {
			if (mObj != null) {
				mObj.removeListener(mListener);
			}
			super.variableSet(t);
			if (mObj != null) {
				mObj.addListener(mListener);
			}
		}

	}

}
