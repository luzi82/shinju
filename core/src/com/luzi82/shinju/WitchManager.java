package com.luzi82.shinju;

import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.luzi82.homuvalue.Slot;
import com.luzi82.shinju.WorldElementManager.ElementManager;
import com.luzi82.shinju.WorldElementManager.ElementManagerFactory;
import com.luzi82.shinju.logic.Witch;

public class WitchManager extends ElementManager {

	WorldElementManager iElementManager;

	Slot<Map<String, Object>> mElementSlot;

	Image mImage;

	ResourceBar mHpBar;
	
	Witch iModel;

	public WitchManager(WorldElementManager aElementManager) {
		this.iElementManager = aElementManager;

		iModel = iElementManager.iElementModel.witch.get();

		long size = iModel.getSize();

		mImage = new Image(new Texture(Gdx.files.internal("img/icon_madoka_inv.png")));
		mImage.setSize(size, size);
		iElementManager.iPlayScreenWorldGroup.mWitchLayer.addActor(mImage);

		mHpBar = new ResourceBar(iModel.hp.get().value, iModel.hp.get().max, new Texture(Gdx.files.internal("img/hp1.png")));
		mHpBar.setScale(size, size * ShinjuCommon.BAR_SIZE);
		iElementManager.iPlayScreenWorldGroup.mUiLayer.addActor(mHpBar);
		
		mElementSlot = new Slot<Map<String, Object>>(null);
		mElementSlot.set(iElementManager.iElementModel);
	}

	public void act(float turn) {
		if (mElementSlot.dirty()) {
			long[] xy = iModel.getXY();
			mImage.setPosition(xy[0], xy[1]);
			mHpBar.setPosition(xy[0], xy[1]);
			mElementSlot.get();
		}
	}

	@Override
	public void dispose() {
		mImage.remove();
		mHpBar.remove();
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
