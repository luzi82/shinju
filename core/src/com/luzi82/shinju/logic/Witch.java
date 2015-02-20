package com.luzi82.shinju.logic;

import java.util.Map;

import com.luzi82.homuvalue.obj.VariableMapVariable;
import com.luzi82.shinju.ShinjuCommon;

public class Witch extends Unit {

	final public VarField<Position, Map<String, Object>> position = new VarField<Position, Map<String, Object>>("position", Factory.C.create(Position.class), this);

	final public VarField<Hp, Map<String, Object>> hp = new VarField<Hp, Map<String, Object>>("hp", Factory.C.create(Hp.class), this);

	final public ObjectField<Long> cooldown = new ObjectField<Long>("cooldown", this);

	protected Witch(Element aElement) {
		super(TYPE, aElement);

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
	public VariableMapVariable<Long, Skill, Map<String, Object>> skillMap() {
		return null;
	}

	@Override
	public void act_0_unit() {
		// TODO
	}

	@Override
	public void act_2_afterEffect() {
		if (hp.get().value.get() <= 0) {
			Seed seed = new Seed(iElement);
			seed.position.get().x.set(position.get().x.get());
			seed.position.get().y.set(position.get().y.get());

			iElement.type.set(Seed.TYPE);
			iElement.witch.set(null);
			iElement.seed.set(seed);
		}
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

	public static Witch create(World aWorld) {
		Element element = Element.create(aWorld);
		Witch ret = new Witch(element);
		element.type.set(TYPE);
		element.witch.set(ret);
		return ret;
	}

	@Override
	public Hp hp() {
		return hp.get();
	}

	@Override
	public Mp mp() {
		return null;
	}

}
