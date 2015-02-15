package com.luzi82.shinju;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.LifecycleListener;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.utils.TimeUtils;
import com.luzi82.homuvalue.LocalVariable;
import com.luzi82.shinju.logic.World;

public class ShinjuCommon {

	public BitmapFont font;

	public ShinjuCommon() {
		mLifecycleVar.set(GdxLifecycle.RESUME);
		Gdx.app.addLifecycleListener(new LL());

		FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("font/Roboto-Regular.ttf"));
		FreeTypeFontParameter fontParameter = new FreeTypeFontParameter();
		font = fontGenerator.generateFont(fontParameter);
		fontGenerator.dispose();

		mFastForwardManager = new FastForwardManager(this);
	}

	public long getTargetTurn() {
		return (TimeUtils.millis() - mShinjuActTime) * DAY_TURN / DAY_TIME + mShinjuActTurn;
	}

	public float getTargetTurnF() {
		return (TimeUtils.millis() - mShinjuActTime) * DAY_TURN / (float) DAY_TIME + mShinjuActTurn;
	}

	public World mShinjuData;
	public long mShinjuActTurn;
	public long mShinjuActTime;
	public FastForwardManager mFastForwardManager;

	// life cycle

	enum GdxLifecycle {
		PAUSE, RESUME, DISPOSE
	}

	public LocalVariable<GdxLifecycle> mLifecycleVar = new LocalVariable<ShinjuCommon.GdxLifecycle>();

	class LL implements LifecycleListener {

		@Override
		public void pause() {
			Gdx.app.debug("IdMONqZG", "pause");
			mLifecycleVar.set(GdxLifecycle.PAUSE);
			mLifecycleVar.get();
		}

		@Override
		public void resume() {
			Gdx.app.debug("ZttTOOOI", "resume");
			mLifecycleVar.set(GdxLifecycle.RESUME);
			mLifecycleVar.get();
		}

		@Override
		public void dispose() {
			Gdx.app.debug("EzSmgdhr", "dispose");
			mLifecycleVar.set(GdxLifecycle.DISPOSE);
			mLifecycleVar.get();
		}

	}

	// ////////////////////////////////

	// number of milli sec in a day
	public static final long DAY_TIME = 24 * 60 * 60 * 1000L;
	// number of turn in a day
	public static final long DAY_TURN = 7 * 7 * 7 * 7 * 7 * 7 * 7;

	public static final long CELL_SIZE = 256;

	public static final long HERO_SIZE = 2 * CELL_SIZE;
	public static final long WITCH_SIZE = 4 * CELL_SIZE;
	public static final long WITCHSEED_SIZE = 1 * CELL_SIZE;

	public static final float BAR_SIZE = 0.05f;

	public static final float PHI = (1f + (float) Math.sqrt(5)) / 2f;
	public static final float PHI2 = 1f + PHI;
	public static final float PHI3 = 1f + 2 * PHI;
	public static final float PHI_1 = -1 + PHI;
	public static final float PHI_2 = 2 - PHI;
	public static final float PHI_3 = -3 + 2 * PHI;

}
