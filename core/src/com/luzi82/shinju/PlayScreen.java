package com.luzi82.shinju;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class PlayScreen extends ScreenAdapter {

	ShinjuCommon common;
	Stage stage;
	PlayScreenUiGroup uiGroup;
	PlayScreenWorldGroup worldGroup;

	// PlayScreenWorldStage worldStage;

	public PlayScreen(ShinjuCommon common) {
		// InputMultiplexer inputMultiplexer = new InputMultiplexer();
		// Gdx.input.setInputProcessor(inputMultiplexer);
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);

		uiGroup = new PlayScreenUiGroup(common);
		uiGroup.setZIndex(200);
		stage.addActor(uiGroup);

		worldGroup = new PlayScreenWorldGroup(common);
		worldGroup.setZIndex(100);
		stage.addActor(worldGroup);

		// uiStage = new PlayScreenUiGroup(common);
		// inputMultiplexer.addProcessor(uiStage);

		// worldStage = new PlayScreenWorldStage(common);
		// inputMultiplexer.addProcessor(worldStage);
	}

	@Override
	public void render(float delta) {
		super.render(delta);

		Gdx.graphics.getGL20().glClearColor(0.5f, 0.5f, 0.5f, 1);
		Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		stage.act();
		stage.draw();

		// uiStage.act(delta);
		// uiStage.draw();

		// worldStage.act(delta);
		// worldStage.draw();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		stage.getViewport().update(width, height, true);
		uiGroup.setSize(width, height);
		// uiStage.resize(width, height);
		// worldStage.resize(width, height);
	}

	@Override
	public void dispose() {
		stage.dispose();
		// uiStage.dispose();
		// worldStage.dispose();
		super.dispose();
	}

}
