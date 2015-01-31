package com.luzi82.shinju;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.luzi82.shinju.logic.Element;
import com.luzi82.shinju.logic.Hero;
import com.luzi82.shinju.logic.Registry;
import com.luzi82.shinju.logic.Witch;
import com.luzi82.shinju.logic.World;

public class ShinjuGame extends Game {

	ShinjuCommon common;
	Screen currentScreen;

	@Override
	public void create() {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		Registry.init();
		common = new ShinjuCommon();

		common.mShinjuData = new World.Var();
		common.mShinjuModel = new World.Model(common.mShinjuData);

		Element.Var element;

		element = new Element.Var();
		element.type.set(Hero.TYPE);
		element.hero.set(new Hero.Var());
		element.hero.get().position.get().x.set(4L * ShinjuCommon.CELL_SIZE);
		element.hero.get().position.get().y.set(4L * ShinjuCommon.CELL_SIZE);
		common.mShinjuModel.addElement(element);

		element = new Element.Var();
		element.type.set(Hero.TYPE);
		element.hero.set(new Hero.Var());
		element.hero.get().position.get().x.set(10L * ShinjuCommon.CELL_SIZE);
		element.hero.get().position.get().y.set(10L * ShinjuCommon.CELL_SIZE);
		common.mShinjuModel.addElement(element);

		element = new Element.Var();
		element.type.set(Witch.TYPE);
		element.witch.set(new Witch.Var());
		element.witch.get().position.get().x.set(6L * ShinjuCommon.CELL_SIZE);
		element.witch.get().position.get().y.set(6L * ShinjuCommon.CELL_SIZE);
		common.mShinjuModel.addElement(element);

		setScreen(new PlayScreen(common));
	}
}
