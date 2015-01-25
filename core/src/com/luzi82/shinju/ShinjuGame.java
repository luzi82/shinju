package com.luzi82.shinju;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.luzi82.shinju.logic.Hero;
import com.luzi82.shinju.logic.Element;
import com.luzi82.shinju.logic.Witch;
import com.luzi82.shinju.logic.World;

public class ShinjuGame extends Game {

	ShinjuCommon common;
	Screen currentScreen;

	@Override
	public void create() {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		common = new ShinjuCommon();

		common.mShinjuData = new World.Data();
		World.Var worldVar = new World.Var(common.mShinjuData);
		common.mShinjuModel = new World.Model(worldVar);

		Element.Data element;

		element = new Element.Data();
		element.type.value = Hero.TYPE;
		element.hero = new Hero.Data();
		element.hero.position.x.value = 4L * ShinjuCommon.CELL_SIZE;
		element.hero.position.y.value = 4L * ShinjuCommon.CELL_SIZE;
		common.mShinjuModel.addElement(element);
		
		element = new Element.Data();
		element.type.value = Hero.TYPE;
		element.hero = new Hero.Data();
		element.hero.position.x.value = 10L * ShinjuCommon.CELL_SIZE;
		element.hero.position.y.value = 10L * ShinjuCommon.CELL_SIZE;
		common.mShinjuModel.addElement(element);

		element = new Element.Data();
		element.type.value = Witch.TYPE;
		element.witch = new Witch.Data();
		element.witch.position.x.value = 6L * ShinjuCommon.CELL_SIZE;
		element.witch.position.y.value = 6L * ShinjuCommon.CELL_SIZE;
		common.mShinjuModel.addElement(element);

		setScreen(new PlayScreen(common));
	}
}
