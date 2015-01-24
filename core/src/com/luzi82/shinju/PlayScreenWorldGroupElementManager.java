package com.luzi82.shinju;

import java.util.Observable;
import java.util.Observer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.luzi82.common.ValueObservable;
import com.luzi82.homuvalue.Value;
import com.luzi82.homuvalue.Value.Listener;
import com.luzi82.shinju.logic.Hero;
import com.luzi82.shinju.logic.Position;
import com.luzi82.shinju.logic.Element;
import com.luzi82.shinju.logic.Element.ElementModel;
import com.luzi82.shinju.logic.Witch;

public class PlayScreenWorldGroupElementManager {

	ShinjuCommon iCommon;
	Element.Model iElement;
	PlayScreenWorldGroup iPlayScreenWorldGroup;

	Image mImage;
	Listener<Element.Data> mElementDataListener;

	Image mToImage;

	boolean mElementDirty;

	public PlayScreenWorldGroupElementManager(ShinjuCommon aCommon, Element.Model aElement, PlayScreenWorldGroup aPlayScreenWorldGroup) {
		iCommon = aCommon;
		iElement = aElement;
		iPlayScreenWorldGroup = aPlayScreenWorldGroup;

		// mImage = new Image(new
		// Texture(Gdx.files.internal("img/icon_madoka.png")));
		mImage = new Image();
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
		mElementDataListener = new Listener<Element.Data>() {
			@Override
			public void onValueDirty(Value<Element.Data> v) {
				mElementDirty = true;
			}
		};
		iElement.iVar.addListener(mElementDataListener);
		mElementDirty = true;

		mMoveLockSession = new Lock.Session(iPlayScreenWorldGroup.iParentZoomMove.mStopLock);

		mMoveActive = new ValueObservable<Boolean>(false);
		mMoveActive.addObserver(new MoveObserver());
	}

	public void act() {
		if (mElementDirty) {
			Position.Var positionVar = getPositionVar();
			if (positionVar != null) {
				String imgName = null;
				String type = iElement.iVar.iType.get();
				if (type.equals(Hero.TYPE)) {
					imgName = "img/icon_madoka.png";
				} else if (type.equals(Witch.TYPE)) {
					imgName = "img/icon_madoka_inv.png";
				}
				Texture texture = new Texture(Gdx.files.internal(imgName));
				TextureRegion textureRegion = new TextureRegion(texture);
				TextureRegionDrawable textureRegionDrawable = new TextureRegionDrawable(textureRegion);
				mImage.setDrawable(textureRegionDrawable);
				mImage.setPosition(positionVar.iX.get(), positionVar.iY.get());
			}
			mElementDirty = false;
			iElement.iVar.get();
		}
	}

	protected Position.Var getPositionVar() {
		ElementModel elementModel = iElement.mElementModel;
		if (elementModel instanceof Position.Container) {
			Position.Container pc = (Position.Container) elementModel;
			return pc.getPosition();
		}
		return null;
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
				Position.Var positionVar = getPositionVar();
				if (positionVar != null) {
					Vector2 v = iPlayScreenWorldGroup.stageToLocalCoordinates(new Vector2(event.getStageX(), event.getStageY()));
					long newX = (long) Math.floor(v.x / ShinjuCommon.CELL_SIZE) * ShinjuCommon.CELL_SIZE;
					long newY = (long) Math.floor(v.y / ShinjuCommon.CELL_SIZE) * ShinjuCommon.CELL_SIZE;
					positionVar.iX.set(newX);
					positionVar.iY.set(newY);
				}
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
