package com.luzi82.shinju;

import com.badlogic.gdx.Gdx;
import com.luzi82.homuvalue.Slot;
import com.luzi82.homuvalue.Value;
import com.luzi82.homuvalue.Value.Listener;
import com.luzi82.shinju.ShinjuCommon.GdxLifecycle;

public class FastForwardManager {

	public final ShinjuCommon mCommon;
	public final Slot<ShinjuCommon.GdxLifecycle> mLifecycleSlot;

	// public boolean mIsForeground;

	public FastForwardManager(ShinjuCommon aCommon) {
		mCommon = aCommon;
		mLifecycleSlot = new Slot<ShinjuCommon.GdxLifecycle>(null);
		mLifecycleSlot.set(mCommon.mLifecycleVar);
		mLifecycleSlot.addListener(mLifecycleListener);
		maintain();
	}

	private boolean isForeground() {
		return mLifecycleSlot.get() == ShinjuCommon.GdxLifecycle.RESUME;
	}

	public void maintain() {
		synchronized (mCommon) {
			if (isForeground() && (fastForwardThread == null)) {
				fastForwardThread = new Thread(fastForwardRunnable);
				fastForwardThread.setPriority(Thread.MIN_PRIORITY);
				fastForwardThread.start();
			}
		}
	}

	Thread fastForwardThread;
	Runnable fastForwardRunnable = new Runnable() {
		@Override
		public void run() {
			while (true) {
				synchronized (mCommon) {
					if (isForeground() && (mCommon.mShinjuData != null) && (mCommon.mShinjuData.turn.get() < mCommon.getTargetTurn())) {
						mCommon.mShinjuData.act();
					} else {
						fastForwardThread = null;
						break;
					}
				}
			}
		}
	};

	public final Listener<ShinjuCommon.GdxLifecycle> mLifecycleListener = new Listener<ShinjuCommon.GdxLifecycle>() {
		@Override
		public void onValueDirty(Value<GdxLifecycle> v) {
			Gdx.app.postRunnable(new Runnable() {
				@Override
				public void run() {
					maintain();
				}
			});
		}
	};

}
