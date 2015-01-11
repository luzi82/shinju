package com.luzi82.shinju;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

public class ShinjuGame extends Game {

	ShinjuCommon common;
	Screen currentScreen;

	@Override
	public void create() {
		common = new ShinjuCommon();
		setScreen(new PlayScreen(common));
	}

}
