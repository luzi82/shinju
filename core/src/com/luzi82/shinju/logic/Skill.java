package com.luzi82.shinju.logic;

import java.util.HashMap;
import java.util.Map;

import com.luzi82.homuvalue.RemoteGroup;
import com.luzi82.homuvalue.Value;

public class Skill {

	public static class Data {

		BulletSimple.Ski.Data bullet_simple;

		String[] order;

	}

	public static class Var extends RemoteGroup<Data> {

		BulletSimple.Ski.Var mBulletSimpleVar;

		public Var(Data aData) {
			super(aData);
		}

	}

	public static class Model {

		protected final World.Model iWorldModel;

		public final Var iVar;

		public Model(Var var, World.Model aWorldModel) {
			iWorldModel = aWorldModel;
			this.iVar = var;
		}

	}

	public static class Logic {

		public void act(Element.Model aMode, Skill.Model aSkillModel) {
			for (String k : aSkillModel.iVar.get().order) {
				TypeLogic tl = TypeLogic.sLogicMap.get(k);
				tl.act(aMode, aSkillModel);
			}
		}

	}

	// type

	public static abstract class TypeLogic<M extends TypeModel> {

		public final String mType;

		protected TypeLogic(String aType) {
			this.mType = aType;
		}

		// public static TypeLogic getLogic(TypeModel aModel, long id) {
		// return Skill.TypeLogic.sLogicMap.get(getModel(aModel,
		// id).iVar.iType.get());
		// }
		//
		// public static Model getModel(TypeModel aModel, long id) {
		// World.Model worldModel = aModel.iWorldModel;
		// return worldModel.mElementModelMap.get(id);
		// }

		public abstract void act(Element.Model aMode, Skill.Model aSkillModel);

		// map

		public static Map<String, TypeLogic> sLogicMap = new HashMap<String, TypeLogic>();

		public static void addLogic(TypeLogic aLogic) {
			sLogicMap.put(aLogic.mType, aLogic);
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

	public static interface TypeModelFactory {
		public String type();

		public TypeModel createElementModel(Var aVar, World.Model aWorldModel);
	}

	protected static final HashMap<String, TypeModelFactory> mTypeModelFactoryMap = new HashMap<String, Skill.TypeModelFactory>();

	protected static void addElementModelFactory(TypeModelFactory factory) {
		mTypeModelFactoryMap.put(factory.type(), factory);
	}

}
