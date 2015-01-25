package com.luzi82.shinju.logic;

import com.luzi82.homuvalue.RemoteGroup;
import com.luzi82.shinju.ShinjuCommon;
import com.luzi82.shinju.logic.Element.ElementModel;
import com.luzi82.shinju.logic.Element.ElementModelFactory;

public class Hero {

	public static class Logic extends Unit.Logic<Model> {

		@Override
		public long getX(Model aModel) {
			return aModel.getElementData().iPosition.iX.get();
		}

		@Override
		public long getY(Model aModel) {
			return aModel.getElementData().iPosition.iY.get();
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

			aModel.getElementData().iPosition.iX.set(aX);
			aModel.getElementData().iPosition.iY.set(aY);
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

		public Model(Element.Var aVar,World.Model aWorldModel) {
			super(aWorldModel);
			this.iVar = aVar;
		}

		@Override
		public Var getElementData() {
			return iVar.iHero;
		}

	}

	public static class ModelFactory implements ElementModelFactory {

		@Override
		public String type() {
			return TYPE;
		}

		@Override
		public ElementModel createElementModel(Element.Var aVar,World.Model aWorldModel) {
			return new Model(aVar,aWorldModel);
		}

	}

}
