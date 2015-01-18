package com.luzi82.shinju;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.luzi82.shinju.logic.ShinjuData;
import com.luzi82.shinju.logic.ShinjuLogic;

public class ShinjuCommon {

	public BitmapFont font;

	public ShinjuCommon() {
		FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("font/Roboto-Regular.ttf"));
		FreeTypeFontParameter fontParameter = new FreeTypeFontParameter();
		font = fontGenerator.generateFont(fontParameter);
		fontGenerator.dispose();
	}

	public ShinjuData mShinjuData;
	public ShinjuLogic mShinjuLogic;

	public static final float PHI = (1f + (float) Math.sqrt(5)) / 2f;
	public static final float PHI2 = 1f + PHI;
	public static final float PHI3 = 1f + 2 * PHI;
	public static final float PHI_1 = -1 + PHI;
	public static final float PHI_2 = 2 - PHI;
	public static final float PHI_3 = -3 + 2 * PHI;

}
