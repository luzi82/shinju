package com.luzi82.shinju;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class ZoomMove extends Group {

	public CameraCalc mCameraCalc;
	public CameraControl mCameraControl;
	public Lock mStopLock;

	public Actor mActor;
	public float mMinSide = 1f;

	public ZoomMove() {
		mCameraCalc = new CameraCalc();
		mCameraControl = new CameraControl(mCameraCalc);
		mStopLock = new Lock();
		mStopLock.addListener(new LL());
		addListener(new IL());
	}

	public void setActor(Actor aActor) {
		this.mActor = aActor;
		addActor(mActor);
	}

	@Override
	public void act(float delta) {
		if (mActor != null) {
			mCameraControl.update(delta);
			// Gdx.app.debug(getClass().getSimpleName(),
			// String.format("iCameraRealZoom:%f, iCameraRealBX:%f, iCameraRealBY:%f",
			// mCameraCalc.iCameraRealZoom, mCameraCalc.iCameraRealBX,
			// mCameraCalc.iCameraRealBY));\

			// mActor.setScale(minSide / mCameraCalc.iCameraRealZoom);
			// mActor.setX(getWidth() / 2 - (mCameraCalc.iCameraRealBX * minSide
			// / mCameraCalc.iCameraRealZoom));
			// mActor.setY(getHeight() / 2 - (mCameraCalc.iCameraRealBY *
			// minSide / mCameraCalc.iCameraRealZoom));

			mActor.setScale(mMinSide / mCameraCalc.iCameraZoom);
			mActor.setX(getWidth() / 2 - (mCameraCalc.iCameraBX * mMinSide / mCameraCalc.iCameraZoom));
			mActor.setY(getHeight() / 2 - (mCameraCalc.iCameraBY * mMinSide / mCameraCalc.iCameraZoom));
		}
		super.act(delta);
	}

	protected void sizeChanged() {
		mCameraCalc.onScreenResize((int) getWidth(), (int) getHeight());
		mMinSide = Math.min(getWidth(), getHeight());
	}

	private class IL extends InputListener {

		@Override
		public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
			mCameraControl.touchDown((int) x, (int) y, pointer, button, Gdx.input.getCurrentEventTime());
			return true;
		}

		@Override
		public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
			mCameraControl.touchUp((int) x, (int) y, pointer, button, Gdx.input.getCurrentEventTime());
			super.touchUp(event, x, y, pointer, button);
		}

		@Override
		public void touchDragged(InputEvent event, float x, float y, int pointer) {
			mCameraControl.touchDragged((int) x, (int) y, pointer, Gdx.input.getCurrentEventTime());
			super.touchDragged(event, x, y, pointer);
		}

		@Override
		public boolean mouseMoved(InputEvent event, float x, float y) {
			mCameraControl.touchMoved((int) x, (int) y, Gdx.input.getCurrentEventTime());
			return true;
		}

		@Override
		public boolean scrolled(InputEvent event, float x, float y, int amount) {
			mCameraControl.scrolled(amount, Gdx.input.getCurrentEventTime());
			return true;
		}

	}

	private class LL implements Lock.Listener {

		@Override
		public void onLock(Lock lock) {
			Gdx.app.debug(getClass().getSimpleName(), "onLock");
			mCameraControl.mForceStop = true;
		}

		@Override
		public void onUnlock(Lock lock) {
			Gdx.app.debug(getClass().getSimpleName(), "onUnlock");
			mCameraControl.mForceStop = false;
		}

	}

}
