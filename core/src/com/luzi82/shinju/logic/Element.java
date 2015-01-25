package com.luzi82.shinju.logic;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.luzi82.homuvalue.RemoteGroup;
import com.luzi82.homuvalue.Value;
import com.luzi82.homuvalue.Value.Listener;

public class Element {

	public static abstract class TypeLogic<M extends TypeModel> {

		public final String mType;

		protected TypeLogic(String aType) {
			this.mType = aType;
		}

		public static TypeLogic getLogic(TypeModel aModel, long id) {
			return Element.TypeLogic.sLogicMap.get(getModel(aModel, id).iVar.iType.get());
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

	public static class Data {

		public long id;

		public Val.Data<String> type = new Val.Data<String>();

		public Hero.Data hero;

		public Witch.Data witch;

		public BulletSimple.Data bullet_simple;

	}

	public static class Var extends RemoteGroup<Data> {

		public final Val.Var<String> iType;

		public Hero.Var iHero;

		public Witch.Var iWitch;

		public BulletSimple.Var iBulletSimple;

		public Var(Data aData) {
			super(aData);
			iType = new Val.Var<String>(iV.type);
			addChild(iType);
			if (iV.hero != null) {
				iHero = new Hero.Var(iV.hero);
				addChild(iHero);
			}
			if (iV.witch != null) {
				iWitch = new Witch.Var(iV.witch);
				addChild(iWitch);
			}
		}

		public long id() {
			return iV.id;
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
			iVar.iType.addListener(mTypeListener);
			updateElementModel();
		}

		protected void updateElementModel() {
			Gdx.app.debug(getClass().getSimpleName(), "6YZA16yq updateElementModel");
			String type = iVar.iType.get();
			mTypeModel = mElementModelFactoryMap.get(type).createElementModel(iVar, iWorldModel);
		}

	}

	public static abstract class TypeModel {
		protected final World.Model iWorldModel;

		protected TypeModel(World.Model aWorldModel) {
			iWorldModel = aWorldModel;
		}

		public abstract Value getTypeData();
	}

	// ElementModelFactory

	public static interface ElementModelFactory {
		public String type();

		public TypeModel createElementModel(Var aVar, World.Model aWorldModel);
	}

	protected static final HashMap<String, ElementModelFactory> mElementModelFactoryMap = new HashMap<String, Element.ElementModelFactory>();

	protected static void addElementModelFactory(ElementModelFactory factory) {
		mElementModelFactoryMap.put(factory.type(), factory);
	}

}
