package com.luzi82.shinju.logic;

import java.util.HashMap;
import java.util.Observable;

import com.luzi82.common.FakeObservable;

public class ShinjuLogic {

	public final ShinjuData iData;

	private FakeObservable mHeroCountObservable = new FakeObservable();
	private HashMap<Long, FakeObservable> mHeroObservableMap = new HashMap<Long, FakeObservable>();

	public ShinjuLogic(ShinjuData aData) {
		iData = aData;
	}

	public void addHero(ShinjuDataHero aHero) {
		iData.mHeroMap.put(aHero.mHeroId, aHero);
		mHeroObservableMap.put(aHero.mHeroId, new FakeObservable());
		mHeroCountObservable.setChanged();
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
		FakeObservable obs = mHeroObservableMap.get(aHeroId);
		obs.setChanged();
		obs.notifyObservers(aHeroId);
	}

	public Observable getHeroCountObservable() {
		return mHeroCountObservable;
	}

	public Observable getHeroObservable(long aHeroId) {
		return mHeroObservableMap.get(aHeroId);
	}

}
