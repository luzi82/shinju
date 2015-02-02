package com.luzi82.shinju.logic;

import java.util.HashMap;
import java.util.Map;

import com.luzi82.homuvalue.obj.ObjectVariable;

public class Element extends ObjectVariable {

	public final World iWorld;

	public final HashMap<String, VarField<? extends Type, Map<String, Object>>> mTypeField;

	public final ObjectField<Long> id;

	public final ObjectField<Boolean> delete;

	public final ObjectField<String> type;

	public final VarField<Hero, Map<String, Object>> hero;

	public final VarField<Witch, Map<String, Object>> witch;

	public final VarField<BulletSimple.Eff, Map<String, Object>> bullet_simple;

	public Element(World aWorld) {
		iWorld = aWorld;
		mTypeField = new HashMap<String, VarField<? extends Type, Map<String, Object>>>();

		id = new ObjectField<Long>("id");
		addField(id);
		delete = new ObjectField<Boolean>("delete");
		addField(delete);
		type = new ObjectField<String>("type");
		addField(type);
		hero = new VarField<Hero, Map<String, Object>>("hero", new Hero.Factory(iWorld, this));
		addField(hero);
		witch = new VarField<Witch, Map<String, Object>>("witch", new Witch.Factory(iWorld, this));
		addField(witch);
		bullet_simple = new VarField<BulletSimple.Eff, Map<String, Object>>("bullet_simple", new BulletSimple.Eff.Factory(iWorld, this));
		addField(bullet_simple);

		mTypeField.put(Hero.TYPE, hero);
		mTypeField.put(Witch.TYPE, witch);
		mTypeField.put(BulletSimple.TYPE, bullet_simple);

		delete.set(false);
	}

	public Type getTypeVar() {
		return mTypeField.get(type.get()).get();
	}

	public static abstract class Type extends ObjectVariable {
		public final String mType;

		public final World iWorld;

		public final Element iElement;

		final public ObjectField<Long> element_id;

		protected Type(String aType, World aWorld, Element aElement) {
			mType = aType;
			iWorld = aWorld;
			iElement = aElement;

			element_id = new ObjectField<Long>("element_id");
			addField(element_id);

			element_id.set(iElement.id.get());
		}

		public abstract void act_0_unit();

		public abstract void act_1_effect();
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

	public void act_0_unit() {
		getTypeVar().act_0_unit();
	}

	public void act_1_effect() {
		getTypeVar().act_1_effect();
	}

}
