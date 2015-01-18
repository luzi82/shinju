package com.luzi82.shinju.logic;

import java.util.HashMap;
import java.util.Observable;

public class ShinjuLogic {

	public final ShinjuData iData;

	private Observable mHeroCountObservable = new Observable();
	private HashMap<Long, O> mHeroObservableMap = new HashMap<Long, O>();

	public ShinjuLogic(ShinjuData aData) {
		iData = aData;
	}

	public void addHero(ShinjuDataHero aHero) {
		iData.mHeroMap.put(aHero.mHeroId, aHero);
		mHeroObservableMap.put(aHero.mHeroId, new O());
		mHeroCountObservable.notifyObservers(aHero.mHeroId);
	}

	public void moveHero(long aHeroId, int aX, int aY) {
		// Gdx.app.debug(getClass().getSimpleName(),
		// String.format("moveHero %d, %d, %d", aHeroId, aX, aY));
		ShinjuDataHero hero = iData.mHeroMap.get(aHeroId);
		if (hero == null) {
			// Gdx.app.debug(getClass().getSimpleName(), "moveHero ignore");
			return;
		}
		if ((hero.mX == aX) && (hero.mY == aY)) {
			return;
		}
		hero.mX = aX;
		hero.mY = aY;
		O obs = mHeroObservableMap.get(aHeroId);
		obs.setChanged();
		obs.notifyObservers(aHeroId);
	}

	public Observable getHeroCountObservable() {
		return mHeroCountObservable;
	}

	public Observable getHeroObservable(long aHeroId) {
		return mHeroObservableMap.get(aHeroId);
	}

	private static class O extends Observable {

		@Override
		public synchronized void setChanged() {
			super.setChanged();
		}

	}

}
