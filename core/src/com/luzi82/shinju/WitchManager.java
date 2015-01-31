package com.luzi82.shinju;

import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.luzi82.homuvalue.Slot;
import com.luzi82.shinju.WorldElementManager.ElementManager;
import com.luzi82.shinju.WorldElementManager.ElementManagerFactory;
import com.luzi82.shinju.logic.Witch;
import com.luzi82.shinju.logic.Witch.Model;

public class WitchManager extends ElementManager {

	WorldElementManager iElementManager;

	Slot<Map<String, Object>> mElementSlot;

	Image mImage;

	// boolean mElementDirty;

	Witch.Model iModel;

	protected static final Witch.Logic sLogic = Witch.sLogic;

	// Listener<Element.Data> mElementDataListener = new
	// Listener<Element.Data>() {
	// @Override
	// public void onValueDirty(AbstractValue<Element.Data> v) {
	// mElementDirty = true;
	// }
	// };

	public WitchManager(WorldElementManager aElementManager) {
		this.iElementManager = aElementManager;

		iModel = (Model) iElementManager.iElementModel.mTypeModel;

		mImage = new Image(new Texture(Gdx.files.internal("img/icon_madoka_inv.png")));
		mImage.setSize(sLogic.getSize(iModel), sLogic.getSize(iModel));
		iElementManager.iPlayScreenWorldGroup.mWitchLayer.addActor(mImage);

		// iElementManager.iElementModel.iVar.addListener(mElementDataListener);
		// mElementDirty = true;

		mElementSlot = new Slot<Map<String, Object>>(null);
		mElementSlot.set(iElementManager.iElementModel.iVar);
	}

	public void act() {
		if (mElementSlot.dirty()) {
			long[] xy = sLogic.getXY(iModel);
			mImage.setPosition(xy[0], xy[1]);
			mElementSlot.get();
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
