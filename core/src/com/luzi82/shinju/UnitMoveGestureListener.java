package com.luzi82.shinju;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;

public class UnitMoveGestureListener extends ActorGestureListener {

	boolean active = false;
	Vector2 oriChildPos;
	Vector2 oriTouchPos;

//	Actor parent;

	// GroupZoomGestureListener2 gl;
	Lock.Session lockSession;

	public UnitMoveGestureListener() {
		super(20, 0.1f, 0.15f, 0.15f);
		lockSession = new Lock.Session(null);
	}

	public void setMoveZoomLock(Lock aLock) {
		lockSession.unlock();
		lockSession = new Lock.Session(aLock);
	}

	@Override
	public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
		active = false;
		lockSession.unlock();
		oriChildPos = null;
		oriTouchPos = null;
		super.touchUp(event, x, y, pointer, button);
	}

	@Override
	public boolean longPress(Actor actor, float x, float y) {
		Gdx.app.debug(getClass().getSimpleName(), String.format("longPress x:%f,  y:%f", x, y));

//		if (parent == null) {
//			Gdx.app.debug(getClass().getSimpleName(), "ignore (parent)");
//			return false;
//		}

		active = true;
		lockSession.lock();
		oriTouchPos = actor.localToParentCoordinates(new Vector2(x, y));
		oriChildPos = new Vector2(actor.getX(), actor.getY());

		return false;
	}

	@Override
	public void pan(InputEvent event, float x, float y, float deltaX, float deltaY) {
		Gdx.app.debug(getClass().getSimpleName(), String.format("pan x:%f,  y:%f", x, y));

//		if (parent == null) {
//			Gdx.app.debug(getClass().getSimpleName(), "ignore (parent)");
//			return;
//		}
		if (!active) {
			Gdx.app.debug(getClass().getSimpleName(), "ignore (!active)");
			return;
		}

		Actor actor = event.getTarget();

		Vector2 v = actor.localToParentCoordinates(new Vector2(x, y));
		v = v.sub(oriTouchPos).add(oriChildPos);
		actor.setPosition(v.x, v.y);

		super.pan(event, x, y, deltaX, deltaY);
	}

}
