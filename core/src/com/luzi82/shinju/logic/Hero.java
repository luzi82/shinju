package com.luzi82.shinju.logic;

import com.luzi82.homuvalue.RemoteGroup;
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
			Position.Var p = aModel.getTypeData().iPosition;
			return new long[] { p.iX.get(), p.iY.get() };
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
				String type = m.iVar.iType.get();
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

			aModel.getTypeData().iPosition.iX.set(aX);
			aModel.getTypeData().iPosition.iY.set(aY);
			return true;
		}

	}

	public static final Logic sLogic = new Logic();

	//

	public static final String TYPE = "hero";

	public static class Data {

		public Position.Data position = new Position.Data();

		public Hp.Data hp = new Hp.Data();

	}

	public static class Var extends RemoteGroup<Data> {

		final public Position.Var iPosition;

		final public Hp.Var iHp;

		public Var(Data aData) {
			super(aData);
			iPosition = new Position.Var(iV.position);
			addChild(iPosition);
			iHp = new Hp.Var(iV.hp);
			addChild(iHp);
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
			return iVar.iHero;
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
