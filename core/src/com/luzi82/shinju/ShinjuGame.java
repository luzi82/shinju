package com.luzi82.shinju;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.luzi82.shinju.logic.Element;
import com.luzi82.shinju.logic.Hero;
import com.luzi82.shinju.logic.Unit;
import com.luzi82.shinju.logic.Witch;
import com.luzi82.shinju.logic.World;

public class ShinjuGame extends Game {

	ShinjuCommon common;
	Screen currentScreen;

	@Override
	public void create() {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		Unit.initBlock();
		common = new ShinjuCommon();

		common.mShinjuData = new World();

		Element element;

		element = new Element(common.mShinjuData);
		element.type.set(Hero.TYPE);
		element.hero.set(new Hero(common.mShinjuData));
		element.hero.get().position.get().x.set(4L * ShinjuCommon.CELL_SIZE);
		element.hero.get().position.get().y.set(4L * ShinjuCommon.CELL_SIZE);
		common.mShinjuData.addElement(element);

		element = new Element(common.mShinjuData);
		element.type.set(Hero.TYPE);
		element.hero.set(new Hero(common.mShinjuData));
		element.hero.get().position.get().x.set(10L * ShinjuCommon.CELL_SIZE);
		element.hero.get().position.get().y.set(10L * ShinjuCommon.CELL_SIZE);
		common.mShinjuData.addElement(element);

		element = new Element(common.mShinjuData);
		element.type.set(Witch.TYPE);
		element.witch.set(new Witch(common.mShinjuData));
		element.witch.get().position.get().x.set(6L * ShinjuCommon.CELL_SIZE);
		element.witch.get().position.get().y.set(6L * ShinjuCommon.CELL_SIZE);
		common.mShinjuData.addElement(element);

		setScreen(new PlayScreen(common));
	}
}
