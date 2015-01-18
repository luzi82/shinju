package com.luzi82.shinju;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class PlayScreen extends ScreenAdapter {

	ShinjuCommon common;
	Stage stage;
	PlayScreenUiGroup uiGroup;
	PlayScreenWorldGroup worldGroup;
	ZoomMove screenZoomGroup;

	// PlayScreenWorldStage worldStage;

	public PlayScreen(ShinjuCommon common) {
		Gdx.app.debug("PlayScreen", "init");

		// InputMultiplexer inputMultiplexer = new InputMultiplexer();
		// Gdx.input.setInputProcessor(inputMultiplexer);

		stage = new Stage(new ScreenViewport());
		// inputMultiplexer.addProcessor(stage);
		Gdx.input.setInputProcessor(stage);

		screenZoomGroup = new ZoomMove();
		stage.addActor(screenZoomGroup);

		worldGroup = new PlayScreenWorldGroup(common);
//		worldGroup.setScale(ShinjuCommon.PHI_3);
		worldGroup.setParent(screenZoomGroup);
		screenZoomGroup.setActor(worldGroup);

		uiGroup = new PlayScreenUiGroup(common);
		uiGroup.setTouchable(Touchable.childrenOnly);
		stage.addActor(uiGroup);

		// GroupZoomGestureListener2 gzgl = new
		// GroupZoomGestureListener2(screenZoomGroup, worldGroup);
		// inputMultiplexer.addProcessor(1, new GestureDetector(gzgl));
		// screenZoomGroup.addListener(gzgl);

		// uiStage = new PlayScreenUiGroup(common);
		// inputMultiplexer.addProcessor(uiStage);

		// worldStage = new PlayScreenWorldStage(common);
		// inputMultiplexer.addProcessor(worldStage);
	}

	@Override
	public void render(float delta) {
		super.render(delta);

		Gdx.graphics.getGL20().glClearColor(0.5f, 0.5f, 0.5f, 1);
		Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.act(delta);
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
		screenZoomGroup.setSize(width, height);
		// worldZoomGroup.setPosition(0, 0);
		// worldZoomGroup.setSize(width, height);
		// worldZoomGroup.setBounds(0, 0, width, height);
//		screenZoomGroup.setPosition(((float) width) / 2, ((float) height) / 2);
//		screenZoomGroup.setScale(Math.min(width, height));
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
