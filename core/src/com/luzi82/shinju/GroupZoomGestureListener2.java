package com.luzi82.shinju;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;

public class GroupZoomGestureListener2 extends ActorGestureListener {

	// Group groupParent;
	final Group groupChild;

	enum State {
		NONE, PREMOVE, MOVE
	}

	State state = State.NONE;

	Vector2 oriChildPos0;
	Vector2 oriTouchPos0;
	Vector2 velocity;
	Float oriZoom;
	Float oriTouchDistance;

	// Float oriZoom;

	Float minX;
	Float maxX;
	Float minY;
	Float maxY;
	Float minZoom;
	Float maxZoom;

	Lock disableLock;

	public GroupZoomGestureListener2(Group groupParent, Group groupChild) {
		// this.groupParent = groupParent;
		this.groupChild = groupChild;
		disableLock = new Lock();
		disableLock.addListener(new Lock.Listener() {
			@Override
			public void onUnlock(Lock lock) {
				// do nothing
			}

			@Override
			public void onLock(Lock lock) {
				reset();
			}
		});
		reset();
	}

	@Override
	public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
		Gdx.app.debug("GroupZoomGestureListener2", String.format("touchDown x:%f, y:%f, pointer:%d, button:%d", x, y, pointer, button));
		if (disableLock.isLock())
			return;
		state = State.PREMOVE;
		velocity = null;
		// oriZoom = null;
		Group parent = groupChild.getParent();
		oriTouchPos0 = parent.stageToLocalCoordinates(new Vector2(event.getStageX(), event.getStageY()));
		oriChildPos0 = new Vector2(groupChild.getX(), groupChild.getY());
		super.touchDown(event, x, y, pointer, button);
	}

	@Override
	public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
		Gdx.app.debug("GroupZoomGestureListener2", String.format("touchUp x:%f, y:%f, pointer:%d, button:%d", x, y, pointer, button));
		if (!Gdx.input.isTouched()) {
			reset();
			return;
		}
		if (disableLock.isLock())
			return;
		super.touchUp(event, x, y, pointer, button);
	}

	// @Override
	// public void tap(InputEvent event, float x, float y, int count, int
	// button) {
	// Gdx.app.debug("GroupZoomGestureListener2",
	// String.format("tap x:%f, y:%f, count:%d, button:%d", x, y, count,
	// button));
	// // Gdx.app.debug("GroupZoomGestureListener2",
	// // String.format("tap2 x:%f, y:%f, count:%d, button:%d",
	// // event.getStageX(), event.getStageY(), count, button));
	// super.tap(event, x, y, count, button);
	// }

	@Override
	public void fling(InputEvent event, float velocityX, float velocityY, int button) {
		Gdx.app.debug("GroupZoomGestureListener2", String.format("fling velocityX:%f, velocityY:%f, button:%d", velocityX, velocityY, button));
		if (disableLock.isLock())
			return;
		if (state == State.NONE)
			return;
		// oriZoom = null;
		Vector2 v = new Vector2(velocityX, velocityY);
		// Actor actor = event.getTarget();
		// v = actor.localToAscendantCoordinates(groupChild.getParent(), v);
		v = groupChild.getParent().stageToLocalCoordinates(v).sub(groupChild.getParent().stageToLocalCoordinates(new Vector2()));
		velocity = v;
		Gdx.app.debug("GroupZoomGestureListener2", String.format("fling v.x:%f, v.y:%f", v.x, v.y));
		super.fling(event, velocityX, velocityY, button);
	}

//	@Override
//	public void pan(InputEvent event, float x, float y, float deltaX, float deltaY) {
//		Gdx.app.debug("GroupZoomGestureListener2", String.format("pan x:%f, y:%f, deltaX:%f, deltaY:%f", x, y, deltaX, deltaY));
//		if (disableLock.isLock())
//			return;
//		velocity = null;
//		// oriZoom = null;
//
//		Group parent = groupChild.getParent();
//		Vector2 touchPos = parent.stageToLocalCoordinates(new Vector2(event.getStageX(), event.getStageY()));
//		groupChild.setPosition(oriChildPos0.x + touchPos.x - oriTouchPos0.x, oriChildPos0.y + touchPos.y - oriTouchPos0.y);
//
//		super.pan(event, x, y, deltaX, deltaY);
//	}

	// @Override
	// public void zoom(InputEvent event, float initialDistance, float distance)
	// {
	// Gdx.app.debug("GroupZoomGestureListener2",
	// String.format("zoom initialDistance:%f, distance:%f", initialDistance,
	// distance));
	// velocity = null;
	//
	// if (oriZoom == null) {
	// oriZoom = groupChild.getScaleX();
	// }
	//
	// }

	@Override
	public void pinch(InputEvent event, Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
		Gdx.app.debug("GroupZoomGestureListener2", String.format("pinch initialPointer1:%s, initialPointer2:%s, pointer1:%s, pointer2:%s", initialPointer1.toString(), initialPointer2.toString(), pointer1.toString(), pointer2.toString()));
		if (disableLock.isLock())
			return;

		Actor target = getTouchDownTarget();
		Actor parent = groupChild.getParent();
		// Gdx.app.debug("GroupZoomGestureListener2",String.format("target.class %s",target.getClass().getSimpleName()));
		pointer1 = target.localToAscendantCoordinates(parent, pointer1);
		pointer2 = target.localToAscendantCoordinates(parent, pointer2);

		Gdx.app.debug("GroupZoomGestureListener2", String.format("pointer1 %s, pointer2 %s", pointer1.toString(), pointer2.toString()));

		if (oriZoom == null) {
			initialPointer1 = target.localToAscendantCoordinates(parent, initialPointer1);
			initialPointer2 = target.localToAscendantCoordinates(parent, initialPointer2);
			Gdx.app.debug("GroupZoomGestureListener2", String.format("initialPointer1 %s, initialPointer2 %s", initialPointer1.toString(), initialPointer2.toString()));

			oriZoom = groupChild.getScaleX();
			oriTouchDistance = initialPointer1.cpy().sub(initialPointer2).len();
			oriTouchPos0 = initialPointer1.cpy().add(initialPointer2).scl(0.5f);
			Gdx.app.debug("GroupZoomGestureListener2", String.format("oriTouchPos0 %s", oriTouchPos0.toString()));
			oriChildPos0 = new Vector2(groupChild.getX(), groupChild.getY());
			Gdx.app.debug("GroupZoomGestureListener2", String.format("oriTouchPos0 %s, oriChildPos0 %s", oriTouchPos0.toString(), oriChildPos0.toString()));
		}

		float newTouchDistance = pointer1.cpy().sub(pointer2).len();
//		 float newTouchDistance = oriTouchDistance;
		Vector2 newMid = pointer1.cpy().add(pointer2).scl(0.5f);
		Vector2 newChildPos = oriChildPos0.cpy().sub(oriTouchPos0).scl(newTouchDistance).scl(1f / oriTouchDistance);
		// Vector2 newChildPos =
		// oriChildPos0.sub(oriTouchPos0).scl(newTouchDistance).scl(1f /
		// oriTouchDistance);
		newChildPos = newChildPos.cpy().add(newMid);

		groupChild.setX(newChildPos.x);
		groupChild.setY(newChildPos.y);
		groupChild.setScale(oriZoom * newTouchDistance / oriTouchDistance);

		super.pinch(event, initialPointer1, initialPointer2, pointer1, pointer2);
	}

	public void act(float delta) {
		if (disableLock.isLock())
			return;
		if (velocity != null) {
			// Gdx.app.debug("GroupZoomGestureListener2",
			// String.format("act delta:%f, x:%f, y:%f", delta, velocity.x,
			// velocity.y));
			groupChild.moveBy(velocity.x * delta, velocity.y * delta);
			// groupChild.moveBy(0,0);
			velocity.x *= 0.98;
			velocity.y *= 0.98;
		}
		if (minX != null && groupChild.getX() < minX * groupChild.getScaleX()) {
			groupChild.setX((minX * groupChild.getScaleX() + groupChild.getX()) / 2);
		}
		if (maxX != null && groupChild.getX() > maxX * groupChild.getScaleX()) {
			groupChild.setX((maxX * groupChild.getScaleX() + groupChild.getX()) / 2);
		}
		if (minY != null && groupChild.getY() < minY * groupChild.getScaleY()) {
			groupChild.setY((minY * groupChild.getScaleY() + groupChild.getY()) / 2);
		}
		if (maxY != null && groupChild.getY() > maxY * groupChild.getScaleY()) {
			groupChild.setY((maxY * groupChild.getScaleY() + groupChild.getY()) / 2);
		}
	}

	public void reset() {
		oriChildPos0 = null;
		oriTouchPos0 = null;
		velocity = null;
		state = State.NONE;
		oriZoom = null;
		// oriZoom = null;
	}

	public int lock() {
		return disableLock.lock();
	}

	public void unlock(int v) {
		disableLock.unlock(v);
	}

}
