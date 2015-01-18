package com.luzi82.shinju;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.luzi82.shinju.logic.ShinjuData;
import com.luzi82.shinju.logic.ShinjuDataHero;
import com.luzi82.shinju.logic.ShinjuLogic;

public class ShinjuGame extends Game {

	ShinjuCommon common;
	Screen currentScreen;

	@Override
	public void create() {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		common = new ShinjuCommon();

		common.mShinjuData = new ShinjuData();
		common.mShinjuLogic = new ShinjuLogic(common.mShinjuData);

		ShinjuDataHero hero;
		hero = new ShinjuDataHero();
		hero.mHeroId = 0;
		hero.mX = 2;
		hero.mY = 2;
		common.mShinjuLogic.addHero(hero);
		hero = new ShinjuDataHero();
		hero.mHeroId = 1;
		hero.mX = 5;
		hero.mY = 5;
		common.mShinjuLogic.addHero(hero);

		setScreen(new PlayScreen(common));
	}

}
