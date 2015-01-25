package com.luzi82.shinju;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.luzi82.homuvalue.Value;
import com.luzi82.homuvalue.Value.Listener;
import com.luzi82.shinju.WorldElementManager.ElementManager;
import com.luzi82.shinju.WorldElementManager.ElementManagerFactory;
import com.luzi82.shinju.logic.Element;
import com.luzi82.shinju.logic.Witch;
import com.luzi82.shinju.logic.Witch.Model;

public class WitchManager extends ElementManager {

	WorldElementManager iElementManager;

	Image mImage;

	boolean mElementDirty;

	Witch.Model iModel;

	protected static final Witch.Logic sLogic = Witch.sLogic;

	Listener<Element.Data> mElementDataListener = new Listener<Element.Data>() {
		@Override
		public void onValueDirty(Value<Element.Data> v) {
			mElementDirty = true;
		}
	};

	public WitchManager(WorldElementManager aElementManager) {
		this.iElementManager = aElementManager;

		iModel = (Model) iElementManager.iElementModel.mElementModel;

		mImage = new Image(new Texture(Gdx.files.internal("img/icon_madoka_inv.png")));
		mImage.setSize(sLogic.getSize(iModel), sLogic.getSize(iModel));
		iElementManager.iPlayScreenWorldGroup.mWitchLayer.addActor(mImage);

		iElementManager.iElementModel.iVar.addListener(mElementDataListener);
		mElementDirty = true;
	}

	public void act() {
		if (mElementDirty) {
			mImage.setPosition(sLogic.getX(iModel), sLogic.getY(iModel));
			iElementManager.iElementModel.iVar.get();
			mElementDirty = false;
		}
	}

	@Override
	public void dispose() {
		mImage.remove();
	}

	public static class Factory extends ElementManagerFactory {

		public Factory() {
			super(Witch.TYPE);
		}

		@Override
		public ElementManager create(WorldElementManager aManager) {
			return new WitchManager(aManager);
		}

	}

}
