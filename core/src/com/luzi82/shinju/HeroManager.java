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
import com.luzi82.shinju.WorldElementManager.ElementManager;
import com.luzi82.shinju.WorldElementManager.ElementManagerFactory;
import com.luzi82.shinju.logic.Element;
import com.luzi82.shinju.logic.Hero;
import com.luzi82.shinju.logic.Hero.Model;
import com.luzi82.shinju.logic.Position;

public class HeroManager extends ElementManager {

	WorldElementManager iElementManager;

	Image mImage;
	Image mToImage;

	boolean mElementDirty;

	Hero.Model iModel;

	Listener<Element.Data> mElementDataListener = new Listener<Element.Data>() {
		@Override
		public void onValueDirty(Value<Element.Data> v) {
			// Gdx.app.debug(getClass().getSimpleName(),
			// "ByMryNyV onValueDirty");
			mElementDirty = true;
		}
	};

	public HeroManager(WorldElementManager aElementManager) {
		this.iElementManager = aElementManager;

		iModel = (Model) iElementManager.iElementModel.mElementModel;

		mImage = new Image(new Texture(Gdx.files.internal("img/icon_madoka.png")));
		mImage.setSize(iModel.size(), iModel.size());
		mImage.addListener(new AGL());
		iElementManager.iPlayScreenWorldGroup.addActor(mImage);

		mToImage = new Image(new Texture(Gdx.files.internal("img/icon_madoka.png")));
		mToImage.setSize(iModel.size(), iModel.size());
		mToImage.setColor(1.0f, 1.0f, 1.0f, ShinjuCommon.PHI_1);
		mToImage.setVisible(false);
		iElementManager.iPlayScreenWorldGroup.addActor(mToImage);

		iElementManager.iElementModel.iVar.addListener(mElementDataListener);
		mElementDirty = true;

		mMoveLockSession = new Lock.Session(iElementManager.iPlayScreenWorldGroup.iParentZoomMove.mStopLock);

		mMoveActive = new ValueObservable<Boolean>(false);
		mMoveActive.addObserver(new MoveObserver());
	}

	public void act() {
		if (mElementDirty) {
			Position.Var positionVar = iModel.position();
			mImage.setPosition(positionVar.iX.get(), positionVar.iY.get());
			iElementManager.iElementModel.iVar.get();
			mElementDirty = false;
		}
	}

	@Override
	public void dispose() {
		mImage.remove();
		mToImage.remove();
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
				Position.Var positionVar = iModel.position();
				Vector2 v = iElementManager.iPlayScreenWorldGroup.stageToLocalCoordinates(new Vector2(event.getStageX(), event.getStageY()));
				long newX = (long) Math.floor(v.x / ShinjuCommon.HERO_SIZE) * ShinjuCommon.HERO_SIZE;
				long newY = (long) Math.floor(v.y / ShinjuCommon.HERO_SIZE) * ShinjuCommon.HERO_SIZE;
				positionVar.iX.set(newX);
				positionVar.iY.set(newY);
			}
			mMoveActive.set(false);
			super.touchUp(event, x, y, pointer, button);
		}

		@Override
		public boolean longPress(Actor actor, float x, float y) {
			Gdx.app.debug(getClass().getSimpleName(), String.format("longPress x:%f,  y:%f", x, y));

			mMoveActive.set(true);

			return false;
		}

		@Override
		public void pan(InputEvent event, float x, float y, float deltaX, float deltaY) {
			Gdx.app.debug(getClass().getSimpleName(), String.format("pan x:%f,  y:%f", x, y));

			if (!mMoveActive.get()) {
				Gdx.app.debug(getClass().getSimpleName(), "ignore (!active)");
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
