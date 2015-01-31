package com.luzi82.shinju.logic;

import java.util.Map;

import com.luzi82.common.Factory;
import com.luzi82.homuvalue.obj.ObjectVariable;
import com.luzi82.shinju.ShinjuCommon;
import com.luzi82.shinju.logic.Element.TypeModel;
import com.luzi82.shinju.logic.Element.TypeModelFactory;

public class Hero {

	public static class Logic extends Unit.Logic<Model> {

		protected Logic() {
			super(TYPE);
		}

		@Override
		public long[] getXY(Model aModel) {
			Position.Var p = aModel.getTypeData().position.get();
			return new long[] { p.x.get(), p.y.get() };
		}

		@Override
		public long getSize(Model aModel) {
			return ShinjuCommon.HERO_SIZE;
		}

		public boolean setXY(Model aModel, long aX, long aY) {
			if (aX % ShinjuCommon.HERO_SIZE != 0)
				return false;
			if (aY % ShinjuCommon.HERO_SIZE != 0)
				return false;

			long mySize = getSize(aModel);

			for (Element.Model m : aModel.iWorldModel.mElementModelMap.values()) {
				String type = m.iVar.type.get();
				if (!isBlock(TYPE, type))
					continue;
				Unit.Model um = (Unit.Model) m.mTypeModel;
				Unit.Logic l = (Unit.Logic) Element.TypeLogic.sLogicMap.get(type);
				if (l == null)
					continue;
				long[] xy = l.getXY(um);
				long size = l.getSize(um);
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

			aModel.getTypeData().position.get().x.set(aX);
			aModel.getTypeData().position.get().y.set(aY);
			return true;
		}

	}

	public static final Logic sLogic = new Logic();

	//

	public static final String TYPE = "hero";

	public static class Var extends ObjectVariable {

		final public VarField<Position.Var, Map<String, Object>> position;

		final public VarField<Hp.Var, Map<String, Object>> hp;

		public Var() {
			position = new VarField<Position.Var, Map<String, Object>>("position", Factory.C.create(Position.Var.class));
			addField(position);
			hp = new VarField<Hp.Var, Map<String, Object>>("hp", Factory.C.create(Hp.Var.class));
			addField(hp);

			position.set(new Position.Var());
			hp.set(new Hp.Var());
		}

	}

	public static class Model extends Unit.Model {
		public Element.Var iVar;

		public Model(Element.Var aVar, World.Model aWorldModel) {
			super(aWorldModel);
			this.iVar = aVar;
		}

		@Override
		public Var getTypeData() {
			return iVar.hero.get();
		}

	}

	public static class ModelFactory implements TypeModelFactory {

		@Override
		public String type() {
			return TYPE;
		}

		@Override
		public TypeModel createElementModel(Element.Var aVar, World.Model aWorldModel) {
			return new Model(aVar, aWorldModel);
		}

	}

}
