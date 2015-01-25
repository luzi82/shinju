package com.luzi82.shinju;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.luzi82.shinju.logic.Element;

public class PlayScreenWorldGroup extends Group {

	ZoomMove iParentZoomMove;

	HashMap<Long, WorldElementManager> mPlayScreenWorldGroupHeroManagerMap = new HashMap<Long, WorldElementManager>();

	ShinjuCommon iCommon;

	public PlayScreenWorldGroup(ShinjuCommon common, ZoomMove aParentZoomMove) {
		iCommon = common;
		iParentZoomMove = aParentZoomMove;

		Image background = new Image(new Texture(Gdx.files.internal("img/sand.png")));
		background.setBounds(0 * ShinjuCommon.CELL_SIZE, 0 * ShinjuCommon.CELL_SIZE, 16 * ShinjuCommon.CELL_SIZE, 16 * ShinjuCommon.CELL_SIZE);
		// background.setTouchable(Touchable.disabled);
		addActor(background);

		for (Element.Model elementModel : common.mShinjuModel.mElementModelMap.values()) {
			WorldElementManager mgr = new WorldElementManager(common, elementModel, this);
			mPlayScreenWorldGroupHeroManagerMap.put(elementModel.iVar.id(), mgr);
		}

	}

	@Override
	public void act(float delta) {
		super.act(delta);
		for (WorldElementManager mgr : mPlayScreenWorldGroupHeroManagerMap.values()) {
			mgr.act();
		}
	}

}
