package com.luzi82.shinju.logic;

public class Registry {

	public static void init() {
		Element.addElementModelFactory(new Hero.ModelFactory());
		Element.addElementModelFactory(new Witch.ModelFactory());

		Unit.Logic.addLogic(Hero.sLogic);
		Unit.Logic.addLogic(Witch.sLogic);

		Unit.Logic.addBlock(Hero.TYPE, Hero.TYPE);
		Unit.Logic.addBlock(Witch.TYPE, Witch.TYPE);
		Unit.Logic.addBlock(Hero.TYPE, Witch.TYPE);
	}

}
