package com.luzi82.shinju;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.luzi82.shinju.logic.World;

public class ShinjuCommon {

	public BitmapFont font;

	public ShinjuCommon() {
		FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("font/Roboto-Regular.ttf"));
		FreeTypeFontParameter fontParameter = new FreeTypeFontParameter();
		font = fontGenerator.generateFont(fontParameter);
		fontGenerator.dispose();
	}

	public World mShinjuData;

	public static final long CELL_SIZE = 256;

	public static final long HERO_SIZE = 2 * CELL_SIZE;
	public static final long WITCH_SIZE = 4 * CELL_SIZE;
	public static final long WITCHSEED_SIZE = 1 * CELL_SIZE;

	public static final float PHI = (1f + (float) Math.sqrt(5)) / 2f;
	public static final float PHI2 = 1f + PHI;
	public static final float PHI3 = 1f + 2 * PHI;
	public static final float PHI_1 = -1 + PHI;
	public static final float PHI_2 = 2 - PHI;
	public static final float PHI_3 = -3 + 2 * PHI;

}
