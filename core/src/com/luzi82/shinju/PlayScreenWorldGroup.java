package com.luzi82.shinju;

import java.util.HashMap;
import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.luzi82.homuvalue.obj.MapVariable;
import com.luzi82.shinju.logic.Element;

public class PlayScreenWorldGroup extends Group {

	ZoomMove iParentZoomMove;

	HashMap<Long, WorldElementManager> mPlayScreenWorldGroupHeroManagerMap = new HashMap<Long, WorldElementManager>();

	ShinjuCommon iCommon;

	public Group mWitchLayer;

	public Group mSeedLayer;

	public Group mHeroLayer;

	public Group mEffectLayer;

	public Group mUiLayer;

	public PlayScreenWorldGroup(ShinjuCommon common, ZoomMove aParentZoomMove) {
		iCommon = common;
		iParentZoomMove = aParentZoomMove;

		Image background = new Image(new Texture(Gdx.files.internal("img/sand.png")));
		background.setBounds(0 * ShinjuCommon.CELL_SIZE, 0 * ShinjuCommon.CELL_SIZE, 16 * ShinjuCommon.CELL_SIZE, 16 * ShinjuCommon.CELL_SIZE);
		// background.setTouchable(Touchable.disabled);
		addActor(background);

		mWitchLayer = new Group();
		addActor(mWitchLayer);

		mSeedLayer = new Group();
		addActor(mSeedLayer);

		mHeroLayer = new Group();
		addActor(mHeroLayer);

		mEffectLayer = new Group();
		addActor(mEffectLayer);

		mUiLayer = new Group();
		addActor(mUiLayer);

		for (Element elementModel : common.mShinjuData.element_map.get().values()) {
			WorldElementManager mgr = new WorldElementManager(common, elementModel, this);
			mPlayScreenWorldGroupHeroManagerMap.put(elementModel.id.get(), mgr);
		}

		common.mShinjuData.element_map.get().addChangeListener(mElementMapListener);

	}

	public MapVariable.ChangeListener<Long, Element> mElementMapListener = new MapVariable.ChangeListener<Long, Element>() {

		@Override
		public void onRemove(Long aK, Element aI) {
//			Gdx.app.debug(getClass().getName(), "c4pyBad0 onRemove");
			mElementRemove.add(aK);
		}

		@Override
		public void onAdd(Long aK, Element aI) {
//			Gdx.app.debug(getClass().getName(), "yK9FF6e5 onAdd");
			mElementAdd.add(aK);
		}

	};

	LinkedList<Long> mElementAdd = new LinkedList<Long>();
	LinkedList<Long> mElementRemove = new LinkedList<Long>();

	@Override
	public void act(float delta) {
		super.act(delta);
		float turn = iCommon.getTargetTurnF();

		mElementAdd.removeAll(mElementRemove);

		for (Long i : mElementAdd) {
			Element e = iCommon.mShinjuData.element_map.get().get(i);
			WorldElementManager mgr = new WorldElementManager(iCommon, e, this);
			mPlayScreenWorldGroupHeroManagerMap.put(e.id.get(), mgr);
		}
		mElementAdd.clear();

		for (Long i : mElementRemove) {
			WorldElementManager mgr = mPlayScreenWorldGroupHeroManagerMap.remove(i);
			if (mgr != null) {
				mgr.dispose();
			}
		}
		mElementRemove.clear();

		for (WorldElementManager mgr : mPlayScreenWorldGroupHeroManagerMap.values()) {
			mgr.act(turn);
		}
	}

}
