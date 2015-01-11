package com.luzi82.shinju;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class PlayScreenUiStage extends Stage {

	public Label label;
	public ScreenViewport viewport;

	public PlayScreenUiStage(ShinjuCommon common) {
		viewport = new ScreenViewport();
		setViewport(viewport);

		LabelStyle ls = new LabelStyle(common.font, Color.BLACK);
		label = new Label("HelloWorld", ls);
		addActor(label);
	}

	public void resize(int width, int height) {
		getViewport().update(width, height, true);
		label.setPosition(0, height, Align.topLeft);
	}

}
