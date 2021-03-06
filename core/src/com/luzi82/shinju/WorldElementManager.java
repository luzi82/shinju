package com.luzi82.shinju;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.luzi82.homuvalue.Slot;
import com.luzi82.shinju.logic.Element;

public class WorldElementManager {

	ShinjuCommon iCommon;
	Element iElementModel;
	PlayScreenWorldGroup iPlayScreenWorldGroup;

	// boolean mElementTypeDirty;

	// Listener<String> mElementTypeListener = new Listener<String>() {
	// @Override
	// public void onValueDirty(AbstractValue<String> v) {
	// mElementTypeDirty = true;
	// }
	// };

	Slot<String> mElementTypeSlot;

	ElementManager mElementManager;

	public WorldElementManager(ShinjuCommon aCommon, Element aElement, PlayScreenWorldGroup aPlayScreenWorldGroup) {
		iCommon = aCommon;
		iElementModel = aElement;
		iPlayScreenWorldGroup = aPlayScreenWorldGroup;

		mElementTypeSlot = new Slot<String>(null);
		mElementTypeSlot.set(iElementModel.type);

		// iElementModel.iVar.iType.addListener(mElementTypeListener);
		//
		// mElementTypeDirty = true;
	}

	public void act(float turn) {
		if (mElementTypeSlot.dirty()) {
			// Gdx.app.debug(getClass().getSimpleName(),
			// "EuWORn4l mElementTypeDirty");
			if (mElementManager != null) {
				mElementManager.dispose();
			}
			mElementManager = null;
			String type = mElementTypeSlot.get();
			ElementManagerFactory elementManagerFactory = mElementManagerFactoryMap.get(type);
			if (elementManagerFactory != null) {
				mElementManager = mElementManagerFactoryMap.get(type).create(this);
			}
		}
		if (mElementManager != null) {
			mElementManager.act(turn);
		}
	}

	public void dispose() {
		if (mElementManager != null) {
			mElementManager.dispose();
		}
	}

	public abstract static class ElementManager {
		public abstract void act(float turn);

		public abstract void dispose();
	}

	// ElementManagerFactory

	public abstract static class ElementManagerFactory {
		final String mType;

		public ElementManagerFactory(String aType) {
			mType = aType;
		}

		public abstract ElementManager create(WorldElementManager aManager);
	}

	protected static HashMap<String, ElementManagerFactory> mElementManagerFactoryMap;

	protected static void addElementManagerFactory(ElementManagerFactory factory) {
		mElementManagerFactoryMap.put(factory.mType, factory);
	}

	static {
		mElementManagerFactoryMap = new HashMap<String, ElementManagerFactory>();
		addElementManagerFactory(new BulletSimpleManager.Factory());
		addElementManagerFactory(new HeroManager.Factory());
		addElementManagerFactory(new SeedManager.Factory());
		addElementManagerFactory(new WitchManager.Factory());
	}

}
