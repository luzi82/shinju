package com.luzi82.shinju.logic;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.luzi82.common.Factory;
import com.luzi82.homuvalue.AbstractValue;
import com.luzi82.homuvalue.Value;
import com.luzi82.homuvalue.Value.Listener;
import com.luzi82.homuvalue.obj.ObjectVariable;

public class Element {

	public static abstract class TypeLogic<M extends TypeModel> {

		public final String mType;

		protected TypeLogic(String aType) {
			this.mType = aType;
		}

		public static TypeLogic getLogic(TypeModel aModel, long id) {
			return Element.TypeLogic.sLogicMap.get(getModel(aModel, id).iVar.type.get());
		}

		public static Model getModel(TypeModel aModel, long id) {
			World.Model worldModel = aModel.iWorldModel;
			return worldModel.mElementModelMap.get(id);
		}

		// map

		public static Map<String, TypeLogic> sLogicMap = new HashMap<String, TypeLogic>();

		public static void addLogic(TypeLogic aLogic) {
			sLogicMap.put(aLogic.mType, aLogic);
		}

	}

	public static class Var extends ObjectVariable {

		public final ObjectField<Long> id;

		public final ObjectField<String> type;

		public final VarField<Hero.Var, Map<String, Object>> hero;

		public final VarField<Witch.Var, Map<String, Object>> witch;

		public final VarField<BulletSimple.Eff.Var, Map<String, Object>> bullet_simple;

		public Var() {
			id = new ObjectField<Long>("id");
			addField(id);
			type = new ObjectField<String>("type");
			addField(type);
			hero = new VarField<Hero.Var, Map<String, Object>>("hero", Factory.C.create(Hero.Var.class));
			addField(hero);
			witch = new VarField<Witch.Var, Map<String, Object>>("witch", Factory.C.create(Witch.Var.class));
			addField(witch);
			bullet_simple = new VarField<BulletSimple.Eff.Var, Map<String, Object>>("bullet_simple", Factory.C.create(BulletSimple.Eff.Var.class));
			addField(bullet_simple);
		}

	}

	public static class Model {

		protected final World.Model iWorldModel;

		public final Var iVar;

		public TypeModel mTypeModel;

		protected final Listener<String> mTypeListener = new Listener<String>() {
			@Override
			public void onValueDirty(Value<String> v) {
				updateElementModel();
			}
		};

		public Model(Var var, World.Model aWorldModel) {
			iWorldModel = aWorldModel;
			this.iVar = var;
			iVar.type.addListener(mTypeListener);
			updateElementModel();
		}

		protected void updateElementModel() {
			Gdx.app.debug(getClass().getSimpleName(), "6YZA16yq updateElementModel");
			String type = iVar.type.get();
			mTypeModel = mElementModelFactoryMap.get(type).createElementModel(iVar, iWorldModel);
		}

		public String getType() {
			return iVar.type.get();
		}

	}

	public static abstract class TypeModel {
		protected final World.Model iWorldModel;

		protected TypeModel(World.Model aWorldModel) {
			iWorldModel = aWorldModel;
		}

		public abstract AbstractValue getTypeData();
	}

	// ElementModelFactory

	public static interface TypeModelFactory {
		public String type();

		public TypeModel createElementModel(Var aVar, World.Model aWorldModel);
	}

	protected static final HashMap<String, TypeModelFactory> mElementModelFactoryMap = new HashMap<String, Element.TypeModelFactory>();

	protected static void addElementModelFactory(TypeModelFactory factory) {
		mElementModelFactoryMap.put(factory.type(), factory);
	}

}
