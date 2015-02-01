package com.luzi82.shinju.logic;

import java.util.HashMap;
import java.util.Map;

import com.luzi82.common.Factory;
import com.luzi82.homuvalue.obj.ObjectVariable;

public class Skill extends ObjectVariable {

	public final World iWorld;

	public final Unit iUnit;

	public final HashMap<String, VarField<? extends Type, Map<String, Object>>> mTypeField;

	final ObjectField<String> type;

	final VarField<BulletSimple.Ski, Map<String, Object>> bullet_simple;

	public Skill(World aWorld, Unit aUnit) {
		iWorld = aWorld;
		iUnit = aUnit;
		mTypeField = new HashMap<String, ObjectVariable.VarField<? extends Type, Map<String, Object>>>();

		type = new ObjectField<String>("type");
		addField(type);
		bullet_simple = new VarField<BulletSimple.Ski, Map<String, Object>>("bullet_simple", Factory.C.create(BulletSimple.Ski.class));
		addField(bullet_simple);

		mTypeField.put(BulletSimple.TYPE, bullet_simple);
	}

	public static abstract class Type extends ObjectVariable {
		public final World iWorld;

		public final Unit iUnit;

		public final String mType;

		protected Type(World aWorld, Unit aUnit, String aType) {
			iWorld = aWorld;
			iUnit = aUnit;
			mType = aType;
		}
	}

}
