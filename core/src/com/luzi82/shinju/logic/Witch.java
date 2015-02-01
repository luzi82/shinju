package com.luzi82.shinju.logic;

import java.util.Map;

import com.luzi82.shinju.ShinjuCommon;

public class Witch extends Unit {

	final public VarField<Position, Map<String, Object>> position;

	final public VarField<Hp, Map<String, Object>> hp;

	public Witch(World aWorld) {
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
		return ShinjuCommon.WITCH_SIZE;
	}

	public static final String TYPE = "witch";

	//

	public static class Factory implements com.luzi82.common.Factory<Witch> {

		private final World iWorld;

		public Factory(World aWorld) {
			iWorld = aWorld;
		}

		@Override
		public Witch create() {
			return new Witch(iWorld);
		}

	}

}
