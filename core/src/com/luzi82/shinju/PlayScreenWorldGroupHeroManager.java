package com.luzi82.shinju;

import java.util.Observable;
import java.util.Observer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.luzi82.common.ObservableValue;
import com.luzi82.shinju.logic.ShinjuDataHero;

public class PlayScreenWorldGroupHeroManager {

	ShinjuCommon iCommon;
	long mHeroId;
	PlayScreenWorldGroup iPlayScreenWorldGroup;

	Image mImage;
//	HeroMoveGestureListener mHeroMoveGestureListener;

	Image mToImage;

	public PlayScreenWorldGroupHeroManager(ShinjuCommon aCommon, long aHeroId, PlayScreenWorldGroup aPlayScreenWorldGroup) {
		iCommon = aCommon;
		mHeroId = aHeroId;
		iPlayScreenWorldGroup = aPlayScreenWorldGroup;

		ShinjuDataHero hero = getHero();

		mImage = new Image(new Texture(Gdx.files.internal("img/icon_madoka.png")));
		mImage.setSize(PlayScreenWorldGroup.CELL_SIZE, PlayScreenWorldGroup.CELL_SIZE);
		mImage.setPosition(hero.mX * PlayScreenWorldGroup.CELL_SIZE, hero.mY * PlayScreenWorldGroup.CELL_SIZE);
		mImage.addListener(new AGL());
		iPlayScreenWorldGroup.addActor(mImage);

		mToImage = new Image(new Texture(Gdx.files.internal("img/icon_madoka.png")));
		mToImage.setSize(PlayScreenWorldGroup.CELL_SIZE, PlayScreenWorldGroup.CELL_SIZE);
		mToImage.setColor(1.0f, 1.0f, 1.0f, ShinjuCommon.PHI_1);
		mToImage.setVisible(false);
		iPlayScreenWorldGroup.addActor(mToImage);

		iCommon.mShinjuLogic.getHeroObservable(mHeroId).addObserver(new HeroObserver());

		mMoveLockSession = new Lock.Session(iPlayScreenWorldGroup.iParentZoomMove.mStopLock);

		mMoveActive = new ObservableValue<Boolean>(false);
		mMoveActive.addObserver(new MoveObserver());
	}

	protected ShinjuDataHero getHero() {
		return iCommon.mShinjuData.mHeroMap.get(mHeroId);
	}

	// boolean mMoveActive = false;
	ObservableValue<Boolean> mMoveActive;
	Lock.Session mMoveLockSession;

	public class AGL extends ActorGestureListener {

		// boolean active = false;

		public AGL() {
			super(20, 0.1f, 0.15f, 0.15f);
		}

		// public void setMoveZoomLock(Lock aLock) {
		// lockSession.unlock();
		// lockSession = new Lock.Session(aLock);
		// }

		@Override
		public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
			if (mMoveActive.get()) {
				Vector2 v = iPlayScreenWorldGroup.stageToLocalCoordinates(new Vector2(event.getStageX(), event.getStageY()));
				int newX = (int) Math.floor(v.x / PlayScreenWorldGroup.CELL_SIZE);
				int newY = (int) Math.floor(v.y / PlayScreenWorldGroup.CELL_SIZE);
				iCommon.mShinjuLogic.moveHero(mHeroId, newX, newY);
			}
			// mMoveActive = false;
			mMoveActive.set(false);
			// lockSession.unlock();
			// oriChildPos = null;
			// oriTouchPos = null;
			super.touchUp(event, x, y, pointer, button);
		}

		@Override
		public boolean longPress(Actor actor, float x, float y) {
			Gdx.app.debug(getClass().getSimpleName(), String.format("longPress x:%f,  y:%f", x, y));

			// if (parent == null) {
			// Gdx.app.debug(getClass().getSimpleName(), "ignore (parent)");
			// return false;
			// }

			mMoveActive.set(true);
			// mMoveActive = true;
			// lockSession.lock();
			// oriTouchPos = actor.localToParentCoordinates(new Vector2(x, y));
			// oriChildPos = new Vector2(actor.getX(), actor.getY());

			return false;
		}

		@Override
		public void pan(InputEvent event, float x, float y, float deltaX, float deltaY) {
			Gdx.app.debug(getClass().getSimpleName(), String.format("pan x:%f,  y:%f", x, y));

			// if (parent == null) {
			// Gdx.app.debug(getClass().getSimpleName(), "ignore (parent)");
			// return;
			// }
			if (!mMoveActive.get()) {
				Gdx.app.debug(getClass().getSimpleName(), "ignore (!active)");
				return;
			}

			// Actor actor = event.getTarget();

			// Vector2 v = actor.localToParentCoordinates(new Vector2(x, y));
			Vector2 v = iPlayScreenWorldGroup.stageToLocalCoordinates(new Vector2(event.getStageX(), event.getStageY()));
			int newX = (int) Math.floor(v.x / PlayScreenWorldGroup.CELL_SIZE);
			int newY = (int) Math.floor(v.y / PlayScreenWorldGroup.CELL_SIZE);
			// iCommon.mShinjuLogic.moveHero(mHeroId, newX, newY);
			mToImage.setPosition(newX * PlayScreenWorldGroup.CELL_SIZE, newY * PlayScreenWorldGroup.CELL_SIZE);
			// v = v.sub(oriTouchPos).add(oriChildPos);
			// actor.setPosition(v.x, v.y);

			super.pan(event, x, y, deltaX, deltaY);
		}

	}

	public class HeroObserver implements Observer {

		@Override
		public void update(Observable o, Object arg) {
			Gdx.app.debug(getClass().getSimpleName(), "update");
			ShinjuDataHero hero = getHero();
			mImage.setX(hero.mX * PlayScreenWorldGroup.CELL_SIZE);
			mImage.setY(hero.mY * PlayScreenWorldGroup.CELL_SIZE);
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

}
