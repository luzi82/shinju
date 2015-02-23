package com.luzi82.shinju;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class PlayScreenUiGroup extends Group {

	public final ShinjuCommon iCommon;

	public Label label;
	public Label turn;
	public Image mHeroButton;

	// public ScreenViewport viewport;

	public PlayScreenUiGroup(ShinjuCommon common) {
		iCommon = common;

		mHeroButton = new Image(new Texture(Gdx.files.internal("img/icon_madoka.png")));
		mHeroButton.addListener(mHeroButtonClickListener);
		addActor(mHeroButton);

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
		label.setPosition(0, getHeight(), Align.topLeft);
		turn.setPosition(getWidth(), getHeight(), Align.topRight);

		int buttonGap = (int) Math.floor(Gdx.graphics.getPpcX() * (Math.sqrt(ShinjuCommon.PHI) - 1) / 2);
		int buttonSize = (int) Math.floor(Gdx.graphics.getPpcX());

		mHeroButton.setSize(buttonSize, buttonSize);
		mHeroButton.setPosition(buttonGap, getHeight() - buttonGap, Align.topLeft);
	}

	public ClickListener mHeroButtonClickListener = new ClickListener() {
		@Override
		public void clicked(InputEvent event, float x, float y) {
			Gdx.app.debug("AalGBxGj", "hero clicked");
		}
	};

}
