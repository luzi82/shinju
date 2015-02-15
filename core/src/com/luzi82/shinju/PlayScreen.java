package com.luzi82.shinju;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
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

		this.common = common;

		// InputMultiplexer inputMultiplexer = new InputMultiplexer();
		// Gdx.input.setInputProcessor(inputMultiplexer);

		stage = new Stage(new ScreenViewport());
		// inputMultiplexer.addProcessor(stage);
		Gdx.input.setInputProcessor(stage);

		screenZoomGroup = new ZoomMove();
		stage.addActor(screenZoomGroup);

		worldGroup = new PlayScreenWorldGroup(common, screenZoomGroup);
		// worldGroup.setScale(ShinjuCommon.PHI_3);
		// worldGroup.setParent(screenZoomGroup);
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

		synchronized (this) {
			active = true;
		}
	}

	@Override
	public void render(float delta) {
		synchronized (this) {
			super.render(delta);

			long targetTurn = common.getTargetTurn();
			if (common.mShinjuData.turn.get() < targetTurn) {
				common.mShinjuData.act();
			}

			Gdx.graphics.getGL20().glClearColor(0.5f, 0.5f, 0.5f, 1);
			Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT);

			stage.act(delta);
			stage.draw();
		}
	}

	@Override
	public void resume() {
		super.resume();
		synchronized (this) {
			active = true;
			if (fastForwardThread == null) {
				fastForwardThread = new Thread(fastForwardRunnable);
				fastForwardThread.setPriority(Thread.MIN_PRIORITY);
				fastForwardThread.start();
			}
		}
	}

	@Override
	public void pause() {
		super.pause();
		synchronized (this) {
			active = false;
		}
	}

	boolean active;
	Thread fastForwardThread;
	Runnable fastForwardRunnable = new Runnable() {
		@Override
		public void run() {
			while (true) {
				synchronized (PlayScreen.this) {
					if (active && (common.mShinjuData.turn.get() < common.getTargetTurn())) {
						common.mShinjuData.act();
					} else {
						fastForwardThread = null;
						break;
					}
				}
			}
		}
	};

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		stage.getViewport().update(width, height, true);
		uiGroup.setSize(width, height);
		screenZoomGroup.setSize(width, height);
	}

	@Override
	public void dispose() {
		synchronized (this) {
			active = false;
		}
		stage.dispose();
		super.dispose();
	}

}
