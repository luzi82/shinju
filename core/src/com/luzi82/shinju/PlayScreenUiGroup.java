package com.luzi82.shinju;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

public class PlayScreenUiGroup extends Group {

	public final ShinjuCommon iCommon;

	public Label label;
	public Label turn;

	// public ScreenViewport viewport;

	public PlayScreenUiGroup(ShinjuCommon common) {
		iCommon = common;

		LabelStyle ls = new LabelStyle(common.font, Color.BLACK);
		label = new Label("Prototype", ls);
		label.setTouchable(Touchable.disabled);
		addActor(label);

		turn = new Label("", ls);
		turn.setTouchable(Touchable.disabled);
		turn.setAlignment(Align.topRight, Align.right);
		addActor(turn);
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		turn.setText(String.format("%d/%d", iCommon.mShinjuData.turn.get(), iCommon.getTargetTurn()));
	}

	protected void sizeChanged() {
		// getViewport().update(width, height, true);
		label.setPosition(0, getHeight(), Align.topLeft);
		turn.setPosition(getWidth(), getHeight(), Align.topRight);
	}

}
