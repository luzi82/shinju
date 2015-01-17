package com.luzi82.shinju;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;

public class GroupZoomGestureListener implements GestureListener {

	final Group groupParent;
	final Group groupChild;

	boolean active;
	float moveOriX;
	float moveOriY;

	public GroupZoomGestureListener(Group groupParent, Group groupChild) {
		this.groupParent = groupParent;
		this.groupChild = groupChild;
		reset();
	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		Gdx.app.debug("GroupZoomGestureListener", String.format("touchDown x:%f, y:%f, pointer:%d, button:%d", x, y, pointer, button));
		if (pointer == 0) {
			active = true;
			moveOriX = x;
			moveOriY = y;
			return true;
		}
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		Gdx.app.debug("GroupZoomGestureListener", String.format("tap x:%f, y:%f, count:%d, button:%d", x, y, count, button));
		if (!active)
			return false;
		reset();
		return true;
	}

	@Override
	public boolean longPress(float x, float y) {
		Gdx.app.debug("GroupZoomGestureListener", String.format("longPress x:%f, y:%f", x, y));
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		Gdx.app.debug("GroupZoomGestureListener", String.format("fling velocityX:%f, velocityY:%f, button:%d", velocityX, velocityY, button));
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		Gdx.app.debug("GroupZoomGestureListener", String.format("pan x:%f, y:%f, deltaX:%f, deltaY:%f", x, y, deltaX, deltaY));
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		Gdx.app.debug("GroupZoomGestureListener", String.format("panStop x:%f, y:%f, pointer:%d, button:%d", x, y, pointer, button));
		// TODO Auto-generated method stub
		if (!active)
			return false;
		reset();
		return true;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		Gdx.app.debug("GroupZoomGestureListener", String.format("zoom initialDistance:%f, distance:%f", initialDistance, distance));
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
		Gdx.app.debug("GroupZoomGestureListener", String.format("pinch initialPointer1:%s, initialPointer2:%s, pointer1:%s, pointer2:%s", initialPointer1.toString(), initialPointer2.toString(), pointer1.toString(), pointer2.toString()));
		// TODO Auto-generated method stub
		return false;
	}

	public void reset() {
		active = false;
	}

}
