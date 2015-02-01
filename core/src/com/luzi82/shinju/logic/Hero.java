package com.luzi82.shinju.logic;

import java.util.Map;

import com.luzi82.shinju.ShinjuCommon;

public class Hero extends Unit {

	final public VarField<Position, Map<String, Object>> position;

	final public VarField<Hp, Map<String, Object>> hp;

	public Hero(World aWorld) {
		super(aWorld, TYPE);

		position = new VarField<Position, Map<String, Object>>("position", Factory.C.create(Position.class));
		addField(position);
		hp = new VarField<Hp, Map<String, Object>>("hp", Factory.C.create(Hp.class));
		addField(hp);

		position.set(new Position());
		hp.set(new Hp());
	}

	@Override
	public long[] getXY() {
		return position.get().getXY();
	}

	@Override
	public long getSize() {
		return ShinjuCommon.HERO_SIZE;
	}

	public boolean setXY(long aX, long aY) {
		if (aX % ShinjuCommon.HERO_SIZE != 0)
			return false;
		if (aY % ShinjuCommon.HERO_SIZE != 0)
			return false;

		long mySize = getSize();

		for (Element m : iWorld.element_map.get().values()) {
			String type = m.type.get();
			if (!isBlock(TYPE, type))
				continue;
			Unit u = (Unit) m.getTypeVar();
			long[] xy = u.getXY();
			long size = u.getSize();
			if (aX + mySize <= xy[0])
				continue;
			if (aY + mySize <= xy[1])
				continue;
			if (aX >= xy[0] + size)
				continue;
			if (aY >= xy[1] + size)
				continue;
			return false;
		}

		position.get().x.set(aX);
		position.get().y.set(aY);
		return true;
	}

	public static final String TYPE = "hero";

	//

	public static class Factory implements com.luzi82.common.Factory<Hero> {

		private final World iWorld;

		public Factory(World aWorld) {
			iWorld = aWorld;
		}

		@Override
		public Hero create() {
			return new Hero(iWorld);
		}

	}

}
