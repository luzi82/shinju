package com.luzi82.shinju.logic;

import com.luzi82.homuvalue.obj.ObjectVariable;

public class Position {

	public static class Var extends ObjectVariable {

		public final ObjectField<Long> x;

		public final ObjectField<Long> y;

		public Var() {
			x = new ObjectField<Long>("x");
			addField(x);
			y = new ObjectField<Long>("y");
			addField(y);
		}

		public long[] getXY() {
			return new long[] { x.get(), y.get() };
		}

	}

}
