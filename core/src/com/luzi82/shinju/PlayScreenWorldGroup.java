package com.luzi82.shinju;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class PlayScreenWorldGroup extends Group {

	public static float CELL_SIZE = 1f;

	GroupZoomGestureListener2 transformHandle;

	public PlayScreenWorldGroup(ShinjuCommon common) {
		transformHandle = new GroupZoomGestureListener2(this.getParent(), this);
		transformHandle.minX = -8 * CELL_SIZE;
		transformHandle.maxX = 8 * CELL_SIZE;
		transformHandle.minY = -8 * CELL_SIZE;
		transformHandle.maxY = 8 * CELL_SIZE;
		transformHandle.minZoom = ShinjuCommon.PHI_3;
		transformHandle.maxZoom = ShinjuCommon.PHI3;
		this.addListener(transformHandle);

		Image background = new Image(new Texture(Gdx.files.internal("img/sand.png")));
		background.setBounds(-8 * CELL_SIZE, -8 * CELL_SIZE, 16 * CELL_SIZE, 16 * CELL_SIZE);
		addActor(background);

		Image icon = new Image(new Texture(Gdx.files.internal("img/icon_madoka.png")));
		icon.setBounds(0, 0, CELL_SIZE, CELL_SIZE);
		UnitMoveGestureListener umgl=new UnitMoveGestureListener(transformHandle);
		umgl.parent=this;
		icon.addListener(umgl);
		addActor(icon);
	}

	@Override
	public void act(float delta) {
		transformHandle.act(delta);
		super.act(delta);
	}

	@Override
	protected void setParent(Group parent) {
		super.setParent(parent);
		// transformHandle.groupParent = parent;
	}

}
