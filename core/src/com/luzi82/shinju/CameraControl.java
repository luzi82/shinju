package com.luzi82.shinju;

import com.badlogic.gdx.Gdx;

//import com.luzi82.chitanda.client.Const;

public class CameraControl {

	public CameraCalc mCameraCalc;

	// force stop
	public boolean mForceStop;

	// // moving
	// public boolean mMoving;

	// touch
	public static final int TOUCH_MAX = 16;
	private boolean mTouchCountChange;
	private boolean mTouchChange;
	private boolean[] mTouching;
	private int[] mTouchSX;
	private int[] mTouchSY;
	private int[] mTouchStartSX;
	private int[] mTouchStartSY;
	private float mTouchStartBXAvg;
	private float mTouchStartBYAvg;
	private float mTouchStartSDiff;
	private float mTouchStartCameraZoom;

	// mouse
	private int mMouseOverSX;
	private int mMouseOverSY;
	private int mMouseScrolled;

	public CameraControl(CameraCalc aCameraCalc) {
		mCameraCalc = aCameraCalc;

		// mMoving = false;

		mTouching = new boolean[TOUCH_MAX];
		mTouchSX = new int[TOUCH_MAX];
		mTouchSY = new int[TOUCH_MAX];

		mTouchStartSX = new int[TOUCH_MAX];
		mTouchStartSY = new int[TOUCH_MAX];
	}

	public void update(float aDelta) {
		int i;

		float reduce = (float) Math.pow(Const.SMOOTH_REDUCE, aDelta);
		float intReduce = (reduce - 1) * Const.DIV_LN_SMOOTH_REDUCE;

		float touchSXAvg = 0;
		float touchSYAvg = 0;
		float touchSDiff = 0;
		int touchCount = 0;

		for (i = 0; i < TOUCH_MAX; ++i) {
			if (!mTouching[i])
				continue;
			touchSXAvg += mTouchSX[i];
			touchSYAvg += mTouchSY[i];
			++touchCount;
		}

		if (mForceStop) {
			mCameraCalc.mCameraBXD = 0;
			mCameraCalc.mCameraBYD = 0;
			mCameraCalc.mCameraZoomD = 0;
		} else if (touchCount > 0) {
			touchSXAvg /= touchCount;
			touchSYAvg /= touchCount;
			if (touchCount > 1) {
				for (i = 0; i < TOUCH_MAX; ++i) {
					if (!mTouching[i])
						continue;
					float d = 0, dd = 0;
					dd = mTouchSX[i] - touchSXAvg;
					dd *= dd;
					d += dd;
					dd = mTouchSY[i] - touchSYAvg;
					dd *= dd;
					d += dd;
					touchSDiff += (float) Math.sqrt(d);
				}
			}
			touchSDiff /= touchCount;
			if (mTouchCountChange) {
				mTouchStartBXAvg = mCameraCalc.screenToBoardX(touchSXAvg);
				mTouchStartBYAvg = mCameraCalc.screenToBoardY(touchSYAvg);
				if (touchCount > 1) {
					mTouchStartSDiff = touchSDiff;
					mTouchStartCameraZoom = mCameraCalc.iCameraZoom;
				} else {
					mCameraCalc.smoothZoom(aDelta, reduce, intReduce);
					float newCameraBX = mCameraCalc.screenBoardToCameraX(touchSXAvg, mTouchStartBXAvg);
					float newCameraBY = mCameraCalc.screenBoardToCameraY(touchSYAvg, mTouchStartBYAvg);
					mCameraCalc.xyMove(newCameraBX, newCameraBY, aDelta);
				}
				mTouchCountChange = false;
			} else if (mTouchChange) {
				if (touchCount > 1) {
					float newZoom = mTouchStartCameraZoom * mTouchStartSDiff / touchSDiff;
					mCameraCalc.zoomMove(newZoom, aDelta);
				} else {
					mCameraCalc.smoothZoom(aDelta, reduce, intReduce);
				}
				float newCameraBX = mCameraCalc.screenBoardToCameraX(touchSXAvg, mTouchStartBXAvg);
				float newCameraBY = mCameraCalc.screenBoardToCameraY(touchSYAvg, mTouchStartBYAvg);
				mCameraCalc.xyMove(newCameraBX, newCameraBY, aDelta);
			} else if (touchCount == 1) {
				mCameraCalc.smoothZoom(aDelta, reduce, intReduce);
				float newCameraBX = mCameraCalc.screenBoardToCameraX(touchSXAvg, mTouchStartBXAvg);
				float newCameraBY = mCameraCalc.screenBoardToCameraY(touchSYAvg, mTouchStartBYAvg);
				mCameraCalc.xySet(newCameraBX, newCameraBY);
			}
		} else if (mMouseScrolled != 0) {
			float mouseBX = mCameraCalc.screenToBoardX(mMouseOverSX);
			float mouseBY = mCameraCalc.screenToBoardY(mMouseOverSY);

			mCameraCalc.mCameraZoomD -= mMouseScrolled * Const.PHI;
			mCameraCalc.smoothZoom(aDelta, reduce, intReduce);

			float newCameraBX = mCameraCalc.screenBoardToCameraX(mMouseOverSX, mouseBX);
			float newCameraBY = mCameraCalc.screenBoardToCameraY(mMouseOverSY, mouseBY);
			mCameraCalc.xyMove(newCameraBX, newCameraBY, aDelta);
			mMouseScrolled = 0;
		} else {
			mCameraCalc.smoothZoom(aDelta, reduce, intReduce);
			mCameraCalc.smoothXY(aDelta, reduce, intReduce);
			// mMoving = false;
		}
		mTouchChange = false;

		// if (mTouchCountChange && (touchCount == 0) && (mCameraCalc.mLockTime
		// > 0)) {
		// mCameraCalc.iCameraBX = mCameraCalc.iCameraRealBX;
		// mCameraCalc.iCameraBY = mCameraCalc.iCameraRealBY;
		// mCameraCalc.iCameraZoom = mCameraCalc.iCameraRealZoom;
		// mCameraCalc.mCameraBXD = 0;
		// mCameraCalc.mCameraBYD = 0;
		// mCameraCalc.mCameraZoomD = 0;
		// }
		//
		// mCameraCalc.updateLock(reduce);
	}

	public void touchDown(int aSX, int aSY, int aPointer, int aButton, long aTime) {
		// Gdx.app.debug(getClass().getSimpleName(), "touchDown");
		mTouchCountChange = true;
		mTouchChange = true;
		mTouching[aPointer] = true;
		mTouchSX[aPointer] = aSX;
		mTouchSY[aPointer] = aSY;
		mTouchStartSX[aPointer] = aSX;
		mTouchStartSY[aPointer] = aSY;
	}

	public void touchUp(int aSX, int aSY, int aPointer, int aButton, long aTime) {
		// Gdx.app.debug(getClass().getSimpleName(), "touchUp");
		mTouchCountChange = true;
		mTouchChange = true;
		mTouching[aPointer] = false;
	}

	public void touchDragged(int aSX, int aSY, int aPointer, long aTime) {
//		Gdx.app.debug(getClass().getSimpleName(), String.format("touchDragged: x:%d, y:%d, p:%d", aSX, aSY, aPointer));
		mTouching[aPointer] = true;
		mTouchChange = mTouchChange || (mTouchSX[aPointer] != aSX) || (mTouchSY[aPointer] != aSY);
		mTouchSX[aPointer] = aSX;
		mTouchSY[aPointer] = aSY;

		// float diff = CameraCalc.diff(aSX, aSY, mTouchStartSX[aPointer],
		// mTouchStartSY[aPointer]);
		// if (diff > Gdx.graphics.getPpcX() * 3) {
		// mMoving = true;
		// mCameraCalc.mLockTime = -1;
		// }
	}

	public void touchMoved(int aX, int aY, long aTime) {
		// Gdx.app.debug(getClass().getSimpleName(), "touchMoved");
		mMouseOverSX = aX;
		mMouseOverSY = aY;
	}

	public void scrolled(int aAmount, long aTime) {
		// Gdx.app.debug(getClass().getSimpleName(), "scrolled");
		mMouseScrolled += aAmount;
	}

	static public class Const {

		public static final float SMOOTH_REDUCE = 1f / 256;
		public static final float DIV_LN_SMOOTH_REDUCE = (float) (1 / Math.log(SMOOTH_REDUCE));
		public static final float PHI = (float) (1 + Math.sqrt(5)) / 2;

		public static int minMax(int aMin, int aV, int aMax) {
			aV = Math.max(aMin, aV);
			aV = Math.min(aMax, aV);
			return aV;
		}

	}

}
