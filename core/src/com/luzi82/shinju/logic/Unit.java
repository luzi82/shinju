package com.luzi82.shinju.logic;

import com.luzi82.shinju.logic.Element.ElementModel;

public class Unit {
	
	public static abstract class Logic<M extends Model> {
		
		public abstract long getX(M aModel);
		
		public abstract long getY(M aModel);
		
		public abstract long getSize(M aModel);
		
	}

	public static abstract class Model extends ElementModel {

//		public abstract Position.Var position();
//		
//		public abstract long size();

	}

}
