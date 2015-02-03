package com.luzi82.shinju.logic;

import java.util.Map;

import com.luzi82.shinju.ShinjuCommon;

public class Witch extends Unit {

	final public VarField<Position, Map<String, Object>> position;

	final public VarField<Hp, Map<String, Object>> hp;

	final public ObjectField<Long> cooldown;

	public Witch(Element aElement) {
		super(TYPE, aElement);

		position = new VarField<Position, Map<String, Object>>("position", Factory.C.create(Position.class));
		addField(position);
		hp = new VarField<Hp, Map<String, Object>>("hp", Factory.C.create(Hp.class));
		addField(hp);
		cooldown = new ObjectField<Long>("cooldown");
		addField(cooldown);

		position.set(new Position());
		hp.set(new Hp());
		cooldown.set(0L);
	}

	@Override
	public long[] getXY() {
		return position.get().getXY();
	}

	@Override
	public long getSize() {
		return ShinjuCommon.WITCH_SIZE;
	}

	@Override
	public ObjectField<Long> cooldownField() {
		return cooldown;
	}
	
	@Override
	public void act_0_unit() {
		
	}

	public static final String TYPE = "witch";

	//

	public static class Factory implements com.luzi82.common.Factory<Witch> {

		private final Element iElement;

		public Factory(Element aElement) {
			iElement = aElement;
		}

		@Override
		public Witch create() {
			return new Witch(iElement);
		}

	}

}
