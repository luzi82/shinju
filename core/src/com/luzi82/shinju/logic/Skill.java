package com.luzi82.shinju.logic;

import java.util.HashMap;
import java.util.Map;

import com.luzi82.homuvalue.obj.ObjectVariable;

public class Skill extends ObjectVariable {

	public final Unit iUnit;

	public final HashMap<String, VarField<? extends Type, Map<String, Object>>> mTypeField;

	public final ObjectField<Long> id = new ObjectField<Long>("id", this);

	public final ObjectField<Long> element_id = new ObjectField<Long>("element_id", this);

	public final ObjectField<String> type = new ObjectField<String>("type", this);

	public final VarField<BulletSimple.Ski, Map<String, Object>> bullet_simple = new VarField<BulletSimple.Ski, Map<String, Object>>("bullet_simple", new BulletSimple.Ski.Factory(this), this);

	protected Skill(Unit aUnit) {
		iUnit = aUnit;
		mTypeField = new HashMap<String, ObjectVariable.VarField<? extends Type, Map<String, Object>>>();

		mTypeField.put(BulletSimple.TYPE, bullet_simple);

		element_id.set(iUnit.element_id.get());
	}

	public Type getTypeVar() {
		return mTypeField.get(type.get()).get();
	}

	public void act() {
		getTypeVar().act();
	}

	public static abstract class Type extends ObjectVariable {
		public final String mType;

		public final Skill iSkill;

		final ObjectField<Long> skill_id = new ObjectField<Long>("skill_id", this);

		protected Type(String aType, Skill aSkill) {
			mType = aType;
			iSkill = aSkill;

			skill_id.set(iSkill.id.get());
		}

		public abstract void act();
	}

	public static class Factory implements com.luzi82.common.Factory<Skill> {

		private final Unit iUnit;

		public Factory(Unit aUnit) {
			iUnit = aUnit;
		}

		@Override
		public Skill create() {
			return new Skill(iUnit);
		}

	}

	public static Skill create(Unit aUnit) {
		long id = aUnit.iElement.iWorld.nextId();
		Skill ret = new Skill(aUnit);
		ret.id.set(id);
		aUnit.skillMap().put(id, ret);
		return ret;
	}

}
