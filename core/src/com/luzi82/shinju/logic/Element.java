package com.luzi82.shinju.logic;

import java.util.HashMap;
import java.util.Map;

import com.luzi82.homuvalue.obj.ObjectVariable;

public class Element extends ObjectVariable {

	public final World iWorld;

	public final HashMap<String, VarField<? extends Type, Map<String, Object>>> mTypeField;

	public final ObjectField<Long> id;

	public final ObjectField<String> type;

	public final VarField<Hero, Map<String, Object>> hero;

	public final VarField<Witch, Map<String, Object>> witch;

	public final VarField<BulletSimple.Eff, Map<String, Object>> bullet_simple;

	public Element(World aWorld) {
		iWorld = aWorld;
		mTypeField = new HashMap<String, VarField<? extends Type, Map<String, Object>>>();

		id = new ObjectField<Long>("id");
		addField(id);
		type = new ObjectField<String>("type");
		addField(type);
		hero = new VarField<Hero, Map<String, Object>>("hero", new Hero.Factory(iWorld));
		addField(hero);
		witch = new VarField<Witch, Map<String, Object>>("witch", new Witch.Factory(iWorld));
		addField(witch);
		bullet_simple = new VarField<BulletSimple.Eff, Map<String, Object>>("bullet_simple", new BulletSimple.Eff.Factory(iWorld));
		addField(bullet_simple);

		mTypeField.put(Hero.TYPE, hero);
		mTypeField.put(Witch.TYPE, witch);
		mTypeField.put(BulletSimple.TYPE, bullet_simple);
	}

	public Type getTypeVar() {
		return mTypeField.get(type.get()).get();
	}

	public static abstract class Type extends ObjectVariable {
		public final World iWorld;

		public final String mType;

		protected Type(World aWorld, String aType) {
			iWorld = aWorld;
			mType = aType;
		}
	}

	public static class Factory implements com.luzi82.common.Factory<Element> {

		private final World iWorld;

		public Factory(World aWorld) {
			iWorld = aWorld;
		}

		@Override
		public Element create() {
			return new Element(iWorld);
		}

	}

}
