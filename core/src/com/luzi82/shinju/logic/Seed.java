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

	public boolean setXY(long aX, long aY) {
		if (aX % ShinjuCommon.CELL_SIZE != 0)
			return false;
		if (aY % ShinjuCommon.CELL_SIZE != 0)
			return false;

		if (!iElement.iWorld.isPositionOk(iElement.id.get(), TYPE, new long[] { aX, aY }, getSize()))
			return false;

		position.get().x.set(aX);
		position.get().y.set(aY);
		return true;
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
		mp.set(mp.get() - mp_reduce.get());
	}

	@Override
	public void act_2_transform() {
		if (mp.get() <= 0) {
			long x = position.get().x.get();
			long y = position.get().y.get();
			x -= ShinjuCommon.CELL_SIZE;
			x -= x % (ShinjuCommon.CELL_SIZE * 2);
			y -= ShinjuCommon.CELL_SIZE;
			y -= y % (ShinjuCommon.CELL_SIZE * 2);

			if (iElement.iWorld.isPositionOk(iElement.id.get(), Witch.TYPE, new long[] { x, y }, ShinjuCommon.WITCH_SIZE)) {
				Witch witch = new Witch(iElement);
				witch.position.get().x.set(x);
				witch.position.get().y.set(y);
				witch.hp.get().value.set(100L);
				witch.hp.get().max.set(100L);

				iElement.type.set(Witch.TYPE);
				iElement.witch.set(witch);
				iElement.seed.set(null);
			}
		}
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
