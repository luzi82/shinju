package com.luzi82.shinju;

import java.util.HashSet;
import java.util.LinkedList;

public class Lock {

	static public interface Listener {

		public void onLock(Lock lock);

		public void onUnlock(Lock lock);

	}

	LinkedList<Listener> mListenerList = new LinkedList<Listener>();

	public void addListener(Listener aListener) {
		mListenerList.add(aListener);
	}

	public void removeListener(Listener aListener) {
		mListenerList.remove(mListenerList);
	}

	HashSet<Integer> mLockValue = new HashSet<Integer>();
	int next = 0;

	public int lock() {
		boolean oldEmpty = mLockValue.isEmpty();
		int ret = next++;
		mLockValue.add(ret);
		if (oldEmpty) {
			notifyLock();
		}
		return ret;
	}

	public void unlock(int v) {
		boolean exist = mLockValue.remove(v);
		if (!exist)
			return;
		if (mLockValue.isEmpty()) {
			notifyUnlock();
		}
	}

	public boolean isLock() {
		return !mLockValue.isEmpty();
	}

	private void notifyLock() {
		for (Listener listener : mListenerList) {
			listener.onLock(this);
		}
	}

	private void notifyUnlock() {
		for (Listener listener : mListenerList) {
			listener.onUnlock(this);
		}

	}

	public static class Session {
		Integer mLockValue;
		Lock lock;

		public Session(Lock lock) {
			this.lock = lock;
		}

		public void lock() {
//			if (lock == null)
//				return;
			if (mLockValue != null)
				return;
			mLockValue = lock.lock();
		}

		public void unlock() {
//			if (lock == null)
//				return;
			if (mLockValue == null)
				return;
			lock.unlock(mLockValue);
			mLockValue = null;
		}
	}

}
