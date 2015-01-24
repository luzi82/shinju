package com.luzi82.shinju;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.luzi82.shinju.logic.Hero;
import com.luzi82.shinju.logic.Unit;
import com.luzi82.shinju.logic.World;

public class ShinjuGame extends Game {

	ShinjuCommon common;
	Screen currentScreen;

	@Override
	public void create() {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		common = new ShinjuCommon();

		common.mShinjuData = new World.Data();
		common.mShinjuVar = new World.Var(common.mShinjuData);

		Unit.Data unit;
		
		unit = new Unit.Data();
		unit.hero = new Hero.Data();
		unit.hero.position.x.value = 5L * ShinjuCommon.CELL_SIZE;
		unit.hero.position.y.value = 5L * ShinjuCommon.CELL_SIZE;
		common.mShinjuVar.addUnit(unit);

		unit = new Unit.Data();
		unit.hero = new Hero.Data();
		unit.hero.position.x.value = 2L * ShinjuCommon.CELL_SIZE;
		unit.hero.position.y.value = 2L * ShinjuCommon.CELL_SIZE;
		common.mShinjuVar.addUnit(unit);

		setScreen(new PlayScreen(common));
	}
}
