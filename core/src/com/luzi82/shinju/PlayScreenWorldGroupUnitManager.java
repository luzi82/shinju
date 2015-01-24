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
import com.luzi82.common.ValueObservable;
import com.luzi82.homuvalue.Value;
import com.luzi82.homuvalue.Value.Listener;
import com.luzi82.shinju.logic.Unit;

public class PlayScreenWorldGroupUnitManager {

	ShinjuCommon iCommon;
	// long mHeroId;
	Unit.Var iUnit;
	PlayScreenWorldGroup iPlayScreenWorldGroup;

	Image mImage;
	// HeroMoveGestureListener mHeroMoveGestureListener;
	// Listener<Hero.>
	Listener<Unit.Data> mUnitDataListener;

	Image mToImage;

	boolean mUnitDirty;

	public PlayScreenWorldGroupUnitManager(ShinjuCommon aCommon, Unit.Var aUnit, PlayScreenWorldGroup aPlayScreenWorldGroup) {
		iCommon = aCommon;
		iUnit = aUnit;
		iPlayScreenWorldGroup = aPlayScreenWorldGroup;

		mImage = new Image(new Texture(Gdx.files.internal("img/icon_madoka.png")));
		mImage.setSize(ShinjuCommon.CELL_SIZE, ShinjuCommon.CELL_SIZE);
		mImage.addListener(new AGL());
		iPlayScreenWorldGroup.addActor(mImage);

		mToImage = new Image(new Texture(Gdx.files.internal("img/icon_madoka.png")));
		mToImage.setSize(ShinjuCommon.CELL_SIZE, ShinjuCommon.CELL_SIZE);
		mToImage.setColor(1.0f, 1.0f, 1.0f, ShinjuCommon.PHI_1);
		mToImage.setVisible(false);
		iPlayScreenWorldGroup.addActor(mToImage);

		// iCommon.mShinjuLogic.getHeroObservable(mHeroId).addObserver(new
		// HeroObserver());
		mUnitDataListener = new Listener<Unit.Data>() {
			@Override
			public void onValueDirty(Value<Unit.Data> v) {
				Gdx.app.debug(getClass().getSimpleName(), "DoCIZWYX value dirty");
				mUnitDirty = true;
			}
		};
		iUnit.addListener(mUnitDataListener);
		mUnitDirty = true;

		mMoveLockSession = new Lock.Session(iPlayScreenWorldGroup.iParentZoomMove.mStopLock);

		mMoveActive = new ValueObservable<Boolean>(false);
		mMoveActive.addObserver(new MoveObserver());
	}

	public void act() {
		if (mUnitDirty) {
			mImage.setPosition(iUnit.iHero.iPosition.iX.get(), iUnit.iHero.iPosition.iY.get());
			mUnitDirty = false;
			iUnit.get();
		}
	}

	// boolean mMoveActive = false;
	ValueObservable<Boolean> mMoveActive;
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
				long newX = (long) Math.floor(v.x / ShinjuCommon.CELL_SIZE) * ShinjuCommon.CELL_SIZE;
				long newY = (long) Math.floor(v.y / ShinjuCommon.CELL_SIZE) * ShinjuCommon.CELL_SIZE;
				// iCommon.mShinjuLogic.moveUnit(mHeroId, newX, newY);
				iUnit.iHero.iPosition.iX.set(newX);
				iUnit.iHero.iPosition.iY.set(newY);
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
			int newX = (int) Math.floor(v.x / ShinjuCommon.CELL_SIZE) * ShinjuCommon.CELL_SIZE;
			int newY = (int) Math.floor(v.y / ShinjuCommon.CELL_SIZE) * ShinjuCommon.CELL_SIZE;
			// iCommon.mShinjuLogic.moveHero(mHeroId, newX, newY);
			mToImage.setPosition(newX, newY);
			// v = v.sub(oriTouchPos).add(oriChildPos);
			// actor.setPosition(v.x, v.y);

			super.pan(event, x, y, deltaX, deltaY);
		}

	}

	// public class HeroObserver implements Observer {
	//
	// @Override
	// public void update(Observable o, Object arg) {
	// Gdx.app.debug(getClass().getSimpleName(), "update");
	// Hero hero = getHero();
	// mImage.setX(hero.position.x);
	// mImage.setY(hero.position.y);
	// }
	//
	// }

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
