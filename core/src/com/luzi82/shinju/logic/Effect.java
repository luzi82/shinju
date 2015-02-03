package com.luzi82.shinju.logic;

public abstract class Effect extends Element.Type {

	protected Effect(String aType, Element aElement) {
		super(aType, aElement);
	}

	@Override
	public void act_0_unit() {
		// do nothing
	}

}
