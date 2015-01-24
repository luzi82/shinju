package com.luzi82.shinju.logic;

import com.luzi82.homuvalue.RemoteGroup;
import com.luzi82.homuvalue.Variable;

public class Hp {

	public static class Data {
		public Val.Data<Long> max = new Val.Data<Long>();

		public Val.Data<Long> value = new Val.Data<Long>();
	}

	public static class Var extends RemoteGroup<Data> {

		public final Variable<Long> iMax;

		public final Variable<Long> iValue;

		@SuppressWarnings("unchecked")
		public Var(Data aData) {
			super(aData);
			iMax = new Val.Var<Long>(iV.max);
			addChild(iMax);
			iValue = new Val.Var<Long>(iV.value);
			addChild(iValue);
		}

	}

}
