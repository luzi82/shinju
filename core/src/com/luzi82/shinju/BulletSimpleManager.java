package com.luzi82.shinju;

import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.luzi82.homuvalue.Slot;
import com.luzi82.shinju.WorldElementManager.ElementManager;
import com.luzi82.shinju.WorldElementManager.ElementManagerFactory;
import com.luzi82.shinju.logic.BulletSimple;

public class BulletSimpleManager extends ElementManager {

	WorldElementManager iElementManager;

	Slot<Map<String, Object>> mElementSlot;

	Image mImage;

	BulletSimple.Eff iModel;

	long SIZE = ShinjuCommon.CELL_SIZE / 2;

	public BulletSimpleManager(WorldElementManager aElementManager) {
		Gdx.app.debug(getClass().getName(), "lRnTH9hK construct");

		this.iElementManager = aElementManager;

		iModel = iElementManager.iElementModel.bullet_simple.get();

		mImage = new Image(new Texture(Gdx.files.internal("img/simple_bullet_0.png")));
		mImage.setSize(SIZE, SIZE);
		iElementManager.iPlayScreenWorldGroup.mEffectLayer.addActor(mImage);

		mElementSlot = new Slot<Map<String, Object>>(null);
		mElementSlot.set(iElementManager.iElementModel);
	}

	public void act(float turn) {
		float[] xy = iModel.getXY(turn);
		// Gdx.app.debug(getClass().getName(),
		// String.format("qEiHNSfO %f %f,%f", turn, xy[0], xy[1]));
		float halfsize = (float) SIZE / 2;
		mImage.setPosition(xy[0] - halfsize, xy[1] - halfsize);
	}

	@Override
	public void dispose() {
		mImage.remove();
	}

	public static class Factory extends ElementManagerFactory {

		public Factory() {
			super(BulletSimple.TYPE);
		}

		@Override
		public ElementManager create(WorldElementManager aManager) {
			return new BulletSimpleManager(aManager);
		}

	}

}
