package com.luzi82.shinju;

import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.luzi82.common.ValueObservable;
import com.luzi82.homuvalue.Slot;
import com.luzi82.shinju.WorldElementManager.ElementManager;
import com.luzi82.shinju.WorldElementManager.ElementManagerFactory;
import com.luzi82.shinju.logic.Hero;

public class HeroManager extends ElementManager {

	WorldElementManager iElementManager;

	Slot<Map<String, Object>> mElementSlot;

	Image mImage;
	Image mToImage;

	ResourceBar mHpBar;
	ResourceBar mMpBar;

	// boolean mElementDirty;

	Hero iModel;

	public HeroManager(WorldElementManager aElementManager) {
		this.iElementManager = aElementManager;

		iModel = iElementManager.iElementModel.hero.get();

		long size = iModel.getSize();

		mImage = new Image(new Texture(Gdx.files.internal("img/icon_madoka.png")));
		mImage.setSize(size, size);
		// mImage.setZIndex(ShinjuCommon.HERO_Z);
		mImage.addListener(new AGL());
		iElementManager.iPlayScreenWorldGroup.mHeroLayer.addActor(mImage);

		mToImage = new Image(new Texture(Gdx.files.internal("img/icon_madoka.png")));
		mToImage.setSize(size, size);
		mToImage.setColor(1.0f, 1.0f, 1.0f, ShinjuCommon.PHI_1);
		// mToImage.setZIndex(ShinjuCommon.HAND_Z);
		mToImage.setVisible(false);
		iElementManager.iPlayScreenWorldGroup.mUiLayer.addActor(mToImage);

		mHpBar = new ResourceBar(iModel.hp.get().value, iModel.hp.get().max, new Texture(Gdx.files.internal("img/hp.png")));
		mHpBar.setScale(size, size * ShinjuCommon.BAR_SIZE);
		iElementManager.iPlayScreenWorldGroup.mUiLayer.addActor(mHpBar);

		mMpBar = new ResourceBar(iModel.mp.get().value, iModel.mp.get().max, new Texture(Gdx.files.internal("img/mp.png")));
		mMpBar.setScale(size, size * ShinjuCommon.BAR_SIZE);
		iElementManager.iPlayScreenWorldGroup.mUiLayer.addActor(mMpBar);

		mElementSlot = new Slot<Map<String, Object>>(null);
		mElementSlot.set(iModel.position.get());

		mMoveLockSession = new Lock.Session(iElementManager.iPlayScreenWorldGroup.iParentZoomMove.mStopLock);

		mMoveActive = new ValueObservable<Boolean>(false);
		mMoveActive.addObserver(new MoveObserver());
	}

	public void act(float turn) {
		if (mElementSlot.dirty()) {
			long size = iModel.getSize();
			float barSize = size * ShinjuCommon.BAR_SIZE;
			long[] xy = iModel.getXY();
			mImage.setPosition(xy[0], xy[1]);
			mHpBar.setPosition(xy[0], xy[1]);
			mMpBar.setPosition(xy[0], xy[1] + barSize);
			mElementSlot.get();
		}
	}

	@Override
	public void dispose() {
		mImage.remove();
		mToImage.remove();
		mHpBar.remove();
		mMpBar.remove();
	}

	ValueObservable<Boolean> mMoveActive;
	Lock.Session mMoveLockSession;

	public class AGL extends ActorGestureListener {

		public AGL() {
			super(20, 0.1f, 0.15f, 0.15f);
		}

		@Override
		public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
			if (mMoveActive.get()) {
				// Position positionVar = iModel.position();
				Vector2 v = iElementManager.iPlayScreenWorldGroup.stageToLocalCoordinates(new Vector2(event.getStageX(), event.getStageY()));
				long newX = (long) Math.floor(v.x / ShinjuCommon.HERO_SIZE) * ShinjuCommon.HERO_SIZE;
				long newY = (long) Math.floor(v.y / ShinjuCommon.HERO_SIZE) * ShinjuCommon.HERO_SIZE;
				iModel.setXY(newX, newY);
				// positionVar.iX.set(newX);
				// positionVar.iY.set(newY);
			}
			mMoveActive.set(false);
			super.touchUp(event, x, y, pointer, button);
		}

		@Override
		public boolean longPress(Actor actor, float x, float y) {
			// Gdx.app.debug(getClass().getSimpleName(),
			// String.format("longPress x:%f,  y:%f", x, y));

			mMoveActive.set(true);

			return false;
		}

		@Override
		public void pan(InputEvent event, float x, float y, float deltaX, float deltaY) {
			// Gdx.app.debug(getClass().getSimpleName(),
			// String.format("pan x:%f,  y:%f", x, y));

			if (!mMoveActive.get()) {
				// Gdx.app.debug(getClass().getSimpleName(),
				// "ignore (!active)");
				return;
			}

			Vector2 v = iElementManager.iPlayScreenWorldGroup.stageToLocalCoordinates(new Vector2(event.getStageX(), event.getStageY()));
			long newX = (long) Math.floor(v.x / ShinjuCommon.HERO_SIZE) * ShinjuCommon.HERO_SIZE;
			long newY = (long) Math.floor(v.y / ShinjuCommon.HERO_SIZE) * ShinjuCommon.HERO_SIZE;
			mToImage.setPosition(newX, newY);

			super.pan(event, x, y, deltaX, deltaY);
		}

	}

	public class MoveObserver implements Observer {
		@Override
		public void update(Observable o, Object arg) {
			if (mMoveActive.get()) {
				mToImage.setPosition(mImage.getX(), mImage.getY());
				mToImage.setVisible(true);
				mImage.setColor(1.0f, 1.0f, 1.0f, ShinjuCommon.PHI_2);
				mMoveLockSession.lock();
			} else {
				mToImage.setVisible(false);
				mImage.setColor(1.0f, 1.0f, 1.0f, 1.0f);
				mMoveLockSession.unlock();
			}
		}
	}

	public static class Factory extends ElementManagerFactory {

		public Factory() {
			super(Hero.TYPE);
		}

		@Override
		public ElementManager create(WorldElementManager aManager) {
			return new HeroManager(aManager);
		}

	}

}
