package com.luzi82.shinju;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

public class PlayScreenUiGroup extends Group {

	public Label label;

	// public ScreenViewport viewport;

	public PlayScreenUiGroup(ShinjuCommon common) {
		// viewport = new ScreenViewport()
		// setViewport(viewport);

		LabelStyle ls = new LabelStyle(common.font, Color.BLACK);
		label = new Label("HelloWorld", ls);
		label.setTouchable(Touchable.disabled);
		addActor(label);
	}

	protected void sizeChanged() {
		// getViewport().update(width, height, true);
		label.setPosition(0, getHeight(), Align.topLeft);
	}

}
