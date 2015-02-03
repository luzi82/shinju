package com.luzi82.shinju.logic;

import java.util.HashMap;
import java.util.Map;

import com.luzi82.homuvalue.obj.ObjectVariable;

public class Skill extends ObjectVariable {

	public final Unit iUnit;

	public final HashMap<String, VarField<? extends Type, Map<String, Object>>> mTypeField;

	public final ObjectField<Long> id;

	public final ObjectField<Long> element_id;

	public final ObjectField<String> type;

	public final VarField<BulletSimple.Ski, Map<String, Object>> bullet_simple;

	public Skill(Unit aUnit) {
		iUnit = aUnit;
		mTypeField = new HashMap<String, ObjectVariable.VarField<? extends Type, Map<String, Object>>>();

		id = new ObjectField<Long>("id");
		addField(id);
		element_id = new ObjectField<Long>("element_id");
		addField(element_id);
		type = new ObjectField<String>("type");
		addField(type);
		bullet_simple = new VarField<BulletSimple.Ski, Map<String, Object>>("bullet_simple", new BulletSimple.Ski.Factory(this));
		addField(bullet_simple);

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

		final ObjectField<Long> skill_id;

		protected Type(String aType, Skill aSkill) {
			mType = aType;
			iSkill = aSkill;

			skill_id = new ObjectField<Long>("skill_id");
			addField(skill_id);

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

}
