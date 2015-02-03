package com.luzi82.shinju.logic;

import java.util.Map;

import com.luzi82.homuvalue.obj.VariableMapVariable;
import com.luzi82.shinju.ShinjuCommon;

public class Hero extends Unit {

	final public VarField<Position, Map<String, Object>> position;

	final public VarField<Hp, Map<String, Object>> hp;

	final public ObjectField<Long> cooldown;

	final public VarField<VariableMapVariable<Long, Skill, Map<String, Object>>, Map<Long, Map<String, Object>>> skill_map;

	public Hero(Element aElement) {
		super(TYPE, aElement);

		position = new VarField<Position, Map<String, Object>>("position", Factory.C.create(Position.class));
		addField(position);
		hp = new VarField<Hp, Map<String, Object>>("hp", Factory.C.create(Hp.class));
		addField(hp);
		cooldown = new ObjectField<Long>("cooldown");
		addField(cooldown);
		Skill.Factory skill_factory = new Skill.Factory(this);
		VariableMapVariable.F<Long, Skill, Map<String, Object>> skill_map_factory = new VariableMapVariable.F(skill_factory);
		skill_map = new VarField<VariableMapVariable<Long, Skill, Map<String, Object>>, Map<Long, Map<String, Object>>>("skill_list", skill_map_factory);
		addField(skill_map);

		position.set(new Position());
		hp.set(new Hp());
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

		World world = iElement.iWorld;
		long mySize = getSize();

		for (Element m : world.element_map.get().values()) {
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

	@Override
	public ObjectField<Long> cooldownField() {
		return cooldown;
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

	public void addSkill(Skill skill) {
		skill.id.set(iElement.iWorld.nextId());
		skill_map.get().put(skill.id.get(), skill);
	}

}
