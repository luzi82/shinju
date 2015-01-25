package com.luzi82.shinju.logic;

import com.luzi82.homuvalue.RemoteGroup;
import com.luzi82.homuvalue.Variable;

public class Position {

	public static class Data {
		public Val.Data<Long> x = new Val.Data<Long>();

		public Val.Data<Long> y = new Val.Data<Long>();
	}

	public static class Var extends RemoteGroup<Data> {

		public final Variable<Long> iX;

		public final Variable<Long> iY;

		public Var(Data aData) {
			super(aData);
			iX = new Val.Var<Long>(iV.x);
			addChild(iX);
			iY = new Val.Var<Long>(iV.y);
			addChild(iY);
		}

	}

}
