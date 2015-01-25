package com.luzi82.shinju.logic;

import com.luzi82.shinju.logic.Element.TypeModel;

public class Effect {

	public static abstract class Logic<M extends Model> extends Element.TypeLogic<M> {

		protected Logic(String aType) {
			super(aType);
		}

	}

	public static abstract class Model extends TypeModel {

		protected Model(World.Model aWorldModel) {
			super(aWorldModel);
		}

	}

}
