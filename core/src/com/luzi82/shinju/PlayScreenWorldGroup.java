package com.luzi82.shinju;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class PlayScreenWorldGroup extends Group {

	public static float CELL_SIZE = 1f;

	ZoomMove iParentZoomMove;

	// UnitMoveGestureListener mIconUnitMoveGestureListener;

	// GroupZoomGestureListener2 transformHandle;

	HashMap<Long, PlayScreenWorldGroupHeroManager> mPlayScreenWorldGroupHeroManagerMap = new HashMap<Long, PlayScreenWorldGroupHeroManager>();

	ShinjuCommon iCommon;

	public PlayScreenWorldGroup(ShinjuCommon common, ZoomMove aParentZoomMove) {
		iCommon = common;
		iParentZoomMove = aParentZoomMove;
		// transformHandle = new GroupZoomGestureListener2(this.getParent(),
		// this);
		// transformHandle.minX = -8 * CELL_SIZE;
		// transformHandle.maxX = 8 * CELL_SIZE;
		// transformHandle.minY = -8 * CELL_SIZE;
		// transformHandle.maxY = 8 * CELL_SIZE;
		// transformHandle.minZoom = ShinjuCommon.PHI_3;
		// transformHandle.maxZoom = ShinjuCommon.PHI3;
		// this.addListener(transformHandle);

		Image background = new Image(new Texture(Gdx.files.internal("img/sand.png")));
		background.setBounds(0 * CELL_SIZE, 0 * CELL_SIZE, 8 * CELL_SIZE, 8 * CELL_SIZE);
		// background.setTouchable(Touchable.disabled);
		addActor(background);

		// Image icon = new Image(new
		// Texture(Gdx.files.internal("img/icon_madoka.png")));
		// icon.setBounds(0, 0, CELL_SIZE, CELL_SIZE);
		// mIconUnitMoveGestureListener = new UnitMoveGestureListener();
		// // umgl.parent = this;
		// icon.addListener(mIconUnitMoveGestureListener);
		// addActor(icon);

		for (Long heroId : common.mShinjuData.mHeroMap.keySet()) {
			// Image icon = new Image(new
			// Texture(Gdx.files.internal("img/icon_madoka.png")));
			// icon.setSize(CELL_SIZE, CELL_SIZE);
			// icon.setPosition(hero.mX * CELL_SIZE, hero.mY * CELL_SIZE);
			// addActor(icon);
			//
			// HeroMoveGestureListener unitMoveGestureListener = new
			// HeroMoveGestureListener(common, hero.mHeroId);
			// icon.addListener(unitMoveGestureListener);
			// mUnitMoveGestureListenerMap.put(hero.mHeroId,
			// unitMoveGestureListener);
			PlayScreenWorldGroupHeroManager mgr = new PlayScreenWorldGroupHeroManager(iCommon, heroId, this);
			mPlayScreenWorldGroupHeroManagerMap.put(heroId, mgr);

		}
	}

//	@Override
//	public void act(float delta) {
//		// transformHandle.act(delta);
//		super.act(delta);
//	}

	// @Override
	// protected void setParent(Group parent) {
	// super.setParent(parent);
	// iParentZoomMove = null;
	// if (parent instanceof ZoomMove) {
	// iParentZoomMove = (ZoomMove) parent;
	// }
	// Lock lock = null;
	// if (iParentZoomMove != null) {
	// lock = iParentZoomMove.mStopLock;
	// }
	// // mIconUnitMoveGestureListener.setMoveZoomLock(lock);
	// for (HeroMoveGestureListener listener :
	// mUnitMoveGestureListenerMap.values()) {
	// listener.setMoveZoomLock(lock);
	// }
	// }

}
