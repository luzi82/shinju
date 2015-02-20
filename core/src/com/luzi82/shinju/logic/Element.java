package com.luzi82.shinju.logic;

import java.util.HashMap;
import java.util.Map;

import com.luzi82.homuvalue.obj.ObjectVariable;

public class Element extends ObjectVariable {

	public final World iWorld;

	public final HashMap<String, VarField<? extends Type, Map<String, Object>>> mTypeField;

	public final ObjectField<Long> id = new ObjectField<Long>("id", this);

	public final ObjectField<Boolean> delete = new ObjectField<Boolean>("delete", this);

	public final ObjectField<String> type = new ObjectField<String>("type", this);

	public final VarField<Hero, Map<String, Object>> hero = new VarField<Hero, Map<String, Object>>("hero", new Hero.Factory(this), this);

	public final VarField<Witch, Map<String, Object>> witch = new VarField<Witch, Map<String, Object>>("witch", new Witch.Factory(this), this);

	public final VarField<Seed, Map<String, Object>> seed = new VarField<Seed, Map<String, Object>>("seed", new Seed.Factory(this), this);

	public final VarField<BulletSimple.Eff, Map<String, Object>> bullet_simple = new VarField<BulletSimple.Eff, Map<String, Object>>("bullet_simple", new BulletSimple.Eff.Factory(this), this);

	protected Element(World aWorld) {
		iWorld = aWorld;
		mTypeField = new HashMap<String, VarField<? extends Type, Map<String, Object>>>();

		mTypeField.put(Hero.TYPE, hero);
		mTypeField.put(Witch.TYPE, witch);
		mTypeField.put(Seed.TYPE, seed);
		mTypeField.put(BulletSimple.TYPE, bullet_simple);

		delete.set(false);
	}

	public Type getTypeVar() {
		return mTypeField.get(type.get()).get();
	}

	public static abstract class Type extends ObjectVariable {
		public final String mType;

		public final Element iElement;

		final public ObjectField<Long> element_id = new ObjectField<Long>("element_id", this);

		protected Type(String aType, Element aElement) {
			mType = aType;
			iElement = aElement;

			element_id.set(iElement.id.get());
		}

		public void act_0_unit() {
		}

		public void act_1_effect() {
		}

		public void act_2_afterEffect() {
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

	public void act_0_unit() {
		getTypeVar().act_0_unit();
	}

	public void act_1_effect() {
		getTypeVar().act_1_effect();
	}

	public void act_2_afterEffect() {
		getTypeVar().act_2_afterEffect();
	}

	public static Element create(World aWorld) {
		long id = aWorld.nextId();
		Element ret = new Element(aWorld);
		ret.id.set(id);
		aWorld.element_map.get().put(id, ret);
		return ret;
	}

}
