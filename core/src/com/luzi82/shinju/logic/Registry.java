package com.luzi82.shinju.logic;

public class Registry {

	public static void init() {
		Element.addElementModelFactory(new Hero.ModelFactory());
		Element.addElementModelFactory(new Witch.ModelFactory());
		Element.addElementModelFactory(new BulletSimple.ModelFactory());

		Element.TypeLogic.addLogic(Hero.sLogic);
		Element.TypeLogic.addLogic(Witch.sLogic);
		Element.TypeLogic.addLogic(BulletSimple.sLogic);

		Unit.Logic.addBlock(Hero.TYPE, Hero.TYPE);
		Unit.Logic.addBlock(Witch.TYPE, Witch.TYPE);
		Unit.Logic.addBlock(Hero.TYPE, Witch.TYPE);
	}

}
