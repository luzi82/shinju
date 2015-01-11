package com.luzi82.shinju;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class ShinjuCommon {

	public BitmapFont font;

	public ShinjuCommon() {
		FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("font/Roboto-Regular.ttf"));
		FreeTypeFontParameter fontParameter = new FreeTypeFontParameter();
		font = fontGenerator.generateFont(fontParameter);
		fontGenerator.dispose();
	}

}
