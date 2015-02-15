package com.luzi82.shinju;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.luzi82.homuvalue.Slot;
import com.luzi82.homuvalue.Value;

public class ResourceBar extends Group {

	Slot<Long> mValueSlot;
	Slot<Long> mMaxSlot;
	Image mImage;

	public ResourceBar(Value<Long> aValue, Value<Long> aMax, Texture aTexture) {
		mValueSlot = new Slot<Long>(null);
		mValueSlot.set(aValue);
		mMaxSlot = new Slot<Long>(null);
		mMaxSlot.set(aMax);

		mImage = new Image(aTexture);
		addActor(mImage);
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		if (mValueSlot.dirty() || mMaxSlot.dirty()) {
			long value = mValueSlot.get();
			long max = mMaxSlot.get();
			float ratio = ((float) value) / max;
			mImage.setSize(ratio, 1f);
		}
	}

}
