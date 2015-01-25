package com.luzi82.shinju.logic;

import com.luzi82.shinju.logic.Element.ElementModel;

public class Unit {

	public static abstract class Model extends ElementModel {

		public abstract Position.Var position();
		
		public abstract long size();

	}

}
