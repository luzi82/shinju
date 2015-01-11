package com.luzi82.shinju;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class PlayScreenWorldGroup extends Group {

	public static float CELL_SIZE = 1f;

	public PlayScreenWorldGroup(ShinjuCommon common) {
		Image background = new Image(new Texture(Gdx.files.internal("img/sand.png")));
		background.setBounds(-8 * CELL_SIZE, -8 * CELL_SIZE, 16 * CELL_SIZE, 16 * CELL_SIZE);
		addActor(background);
	}

}
