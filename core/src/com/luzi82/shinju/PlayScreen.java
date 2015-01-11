package com.luzi82.shinju;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;

public class PlayScreen extends ScreenAdapter {

	PlayScreenUiStage uiStage;
	ShinjuCommon common;

	public PlayScreen(ShinjuCommon common) {
		InputMultiplexer inputMultiplexer = new InputMultiplexer();
		Gdx.input.setInputProcessor(inputMultiplexer);

		uiStage = new PlayScreenUiStage(common);
		inputMultiplexer.addProcessor(uiStage);
	}

	@Override
	public void render(float delta) {
		super.render(delta);

		Gdx.graphics.getGL20().glClearColor(0.5f, 0.5f, 0.5f, 1);
		Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		uiStage.act(delta);
		uiStage.draw();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		uiStage.resize(width, height);
	}

	@Override
	public void dispose() {
		uiStage.dispose();
		super.dispose();
	}

}
