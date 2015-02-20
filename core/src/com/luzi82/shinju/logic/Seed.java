package com.luzi82.shinju.logic;

import java.util.Map;

import com.luzi82.homuvalue.obj.VariableMapVariable;
import com.luzi82.shinju.ShinjuCommon;

public class Seed extends Unit {

	final public VarField<Position, Map<String, Object>> position = new VarField<Position, Map<String, Object>>("position", Factory.C.create(Position.class), this);

	final public ObjectField<Long> mp = new ObjectField<Long>("mp", this);
	
	final public ObjectField<Long> mp_reduce = new ObjectField<Long>("mp_reduce", this);

	protected Seed(Element aElement) {
		super(TYPE, aElement);

		position.set(new Position());
	}

	@Override
	public long[] getXY() {
		return position.get().getXY();
	}

	@Override
	public long getSize() {
		return ShinjuCommon.CELL_SIZE;
	}

	@Override
	public ObjectField<Long> cooldownField() {
		return null;
	}

	@Override
	public VariableMapVariable<Long, Skill, Map<String, Object>> skillMap() {
		return null;
	}

	@Override
	public void act_0_unit() {

	}

	public static final String TYPE = "seed";

	//

	public static class Factory implements com.luzi82.common.Factory<Seed> {

		private final Element iElement;

		public Factory(Element aElement) {
			iElement = aElement;
		}

		@Override
		public Seed create() {
			return new Seed(iElement);
		}

	}

	public static Seed create(World aWorld) {
		Element element = Element.create(aWorld);
		Seed ret = new Seed(element);
		element.type.set(TYPE);
		return ret;
	}

	@Override
	public Hp hp() {
		return null;
	}

	@Override
	public Mp mp() {
		return null;
	}

}
