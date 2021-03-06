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
import com.luzi82.homuvalue.Constant;
import com.luzi82.homuvalue.Slot;
import com.luzi82.shinju.WorldElementManager.ElementManager;
import com.luzi82.shinju.WorldElementManager.ElementManagerFactory;
import com.luzi82.shinju.logic.Seed;

public class SeedManager extends ElementManager {

	WorldElementManager iElementManager;

	Slot<Map<String, Object>> mElementSlot;

	Image mImage;
	Image mToImage;

	ResourceBar mMpBar;

	Seed iModel;

	public SeedManager(WorldElementManager aElementManager) {
		this.iElementManager = aElementManager;

		iModel = iElementManager.iElementModel.seed.get();

		long size = iModel.getSize();

		mImage = new Image(new Texture(Gdx.files.internal("img/seed.png")));
		mImage.setSize(size, size);
		mImage.addListener(new AGL());
		iElementManager.iPlayScreenWorldGroup.mSeedLayer.addActor(mImage);

		mToImage = new Image(new Texture(Gdx.files.internal("img/seed.png")));
		mToImage.setSize(size, size);
		mToImage.setColor(1.0f, 1.0f, 1.0f, ShinjuCommon.PHI_1);
		mToImage.setVisible(false);
		iElementManager.iPlayScreenWorldGroup.mUiLayer.addActor(mToImage);

		mMpBar = new ResourceBar(iModel.mp, new Constant<Long>(100L), new Texture(Gdx.files.internal("img/mp.png")));
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
			long[] xy = iModel.getXY();
			mImage.setPosition(xy[0], xy[1]);
			mMpBar.setPosition(xy[0], xy[1]);
			mElementSlot.get();
		}
	}

	@Override
	public void dispose() {
		mImage.remove();
		mToImage.remove();
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
				Vector2 v = iElementManager.iPlayScreenWorldGroup.stageToLocalCoordinates(new Vector2(event.getStageX(), event.getStageY()));
				long newX = (long) Math.floor(v.x / ShinjuCommon.CELL_SIZE) * ShinjuCommon.CELL_SIZE;
				long newY = (long) Math.floor(v.y / ShinjuCommon.CELL_SIZE) * ShinjuCommon.CELL_SIZE;
				iModel.setXY(newX, newY);
			}
			mMoveActive.set(false);
			super.touchUp(event, x, y, pointer, button);
		}

		@Override
		public boolean longPress(Actor actor, float x, float y) {
			mMoveActive.set(true);

			return false;
		}

		@Override
		public void pan(InputEvent event, float x, float y, float deltaX, float deltaY) {
			if (!mMoveActive.get()) {
				return;
			}

			Vector2 v = iElementManager.iPlayScreenWorldGroup.stageToLocalCoordinates(new Vector2(event.getStageX(), event.getStageY()));
			long newX = (long) Math.floor(v.x / ShinjuCommon.CELL_SIZE) * ShinjuCommon.CELL_SIZE;
			long newY = (long) Math.floor(v.y / ShinjuCommon.CELL_SIZE) * ShinjuCommon.CELL_SIZE;
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
			super(Seed.TYPE);
		}

		@Override
		public ElementManager create(WorldElementManager aManager) {
			return new SeedManager(aManager);
		}

	}

}
