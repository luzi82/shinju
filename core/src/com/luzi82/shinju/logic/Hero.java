package com.luzi82.shinju.logic;

import java.util.Map;

import com.luzi82.homuvalue.obj.VariableMapVariable;
import com.luzi82.shinju.ShinjuCommon;

public class Hero extends Unit {

	final public VarField<Position, Map<String, Object>> position = new VarField<Position, Map<String, Object>>("position", Factory.C.create(Position.class),this);

	final public VarField<Hp, Map<String, Object>> hp = new VarField<Hp, Map<String, Object>>("hp", Factory.C.create(Hp.class),this);

	final public VarField<Mp, Map<String, Object>> mp = new VarField<Mp, Map<String, Object>>("mp", Factory.C.create(Mp.class),this);

	final public ObjectField<Long> cooldown = new ObjectField<Long>("cooldown",this);

	final public VarField<VariableMapVariable<Long, Skill, Map<String, Object>>, Map<Long, Map<String, Object>>> skill_map;

	protected Hero(Element aElement) {
		super(TYPE, aElement);

		Skill.Factory skill_factory = new Skill.Factory(this);
		VariableMapVariable.F<Long, Skill, Map<String, Object>> skill_map_factory = new VariableMapVariable.F(skill_factory);
		skill_map = new VarField<VariableMapVariable<Long, Skill, Map<String, Object>>, Map<Long, Map<String, Object>>>("skill_list", skill_map_factory,this);

		position.set(new Position());
		hp.set(new Hp());
		mp.set(new Mp());
		cooldown.set(0L);
		skill_map.set(new VariableMapVariable<Long, Skill, Map<String, Object>>(skill_factory));
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

		if (!iElement.iWorld.isPositionOk(iElement.id.get(), TYPE, new long[] { aX, aY }, getSize()))
			return false;

		position.get().x.set(aX);
		position.get().y.set(aY);
		return true;
	}

	@Override
	public ObjectField<Long> cooldownField() {
		return cooldown;
	}

	@Override
	public VariableMapVariable<Long, Skill, Map<String, Object>> skillMap() {
		return skill_map.get();
	}

	@Override
	public void act_0_unit() {
		if (cooldown.get() > iElement.iWorld.turn.get())
			return;
		for (Skill skill : skill_map.get().values()) {
			skill.act();
		}
	}

	public static final String TYPE = "hero";

	//

	public static class Factory implements com.luzi82.common.Factory<Hero> {

		private final Element iElement;

		public Factory(Element aElement) {
			iElement = aElement;
		}

		@Override
		public Hero create() {
			return new Hero(iElement);
		}

	}

	// public void addSkill(Skill skill) {
	// skill.id.set(iElement.iWorld.nextId());
	// skill_map.get().put(skill.id.get(), skill);
	// }

	public static Hero create(World aWorld) {
		Element element = Element.create(aWorld);
		Hero ret = new Hero(element);
		element.type.set(TYPE);
		element.hero.set(ret);
		return ret;
	}

	@Override
	public Hp hp() {
		return hp.get();
	}

	@Override
	public Mp mp() {
		return mp.get();
	}

}
