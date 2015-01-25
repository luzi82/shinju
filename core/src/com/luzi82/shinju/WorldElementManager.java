package com.luzi82.shinju;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.luzi82.homuvalue.Value;
import com.luzi82.homuvalue.Value.Listener;
import com.luzi82.shinju.logic.Element;

public class WorldElementManager {

	ShinjuCommon iCommon;
	Element.Model iElementModel;
	PlayScreenWorldGroup iPlayScreenWorldGroup;

	boolean mElementTypeDirty;

	Listener<String> mElementTypeListener = new Listener<String>() {
		@Override
		public void onValueDirty(Value<String> v) {
			mElementTypeDirty = true;
		}
	};

	ElementManager mElementManager;

	public WorldElementManager(ShinjuCommon aCommon, Element.Model aElement, PlayScreenWorldGroup aPlayScreenWorldGroup) {
		iCommon = aCommon;
		iElementModel = aElement;
		iPlayScreenWorldGroup = aPlayScreenWorldGroup;

		iElementModel.iVar.iType.addListener(mElementTypeListener);

		mElementTypeDirty = true;
	}

	public void act() {
		if (mElementTypeDirty) {
			Gdx.app.debug(getClass().getSimpleName(), "EuWORn4l mElementTypeDirty");
			mElementManager = null;
			String type = iElementModel.iVar.iType.get();
			ElementManagerFactory elementManagerFactory = mElementManagerFactoryMap.get(type);
			if (elementManagerFactory != null) {
				mElementManager = mElementManagerFactoryMap.get(type).create(this);
			}
			iElementModel.iVar.get();
			mElementTypeDirty = false;
		}
		if (mElementManager != null) {
			mElementManager.act();
		}
	}

	public abstract static class ElementManager {
		public abstract void act();
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
		addElementManagerFactory(new HeroManager.Factory());
	}

}
