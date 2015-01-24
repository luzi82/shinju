package com.luzi82.shinju.logic;

import com.luzi82.homuvalue.Variable;

public class Val {

	public static class Data<T> {
		public T value;
	}

	public static class Var<T> extends Variable<T> {

		public final Val.Data<T> iData;

		public Var(Val.Data<T> aData) {
			this.iData = aData;
		}

		@Override
		protected void variableSet(T t) {
			iData.value = t;
		}

		@Override
		protected T variableGet() {
			return iData.value;
		}

	}

}
