package com.luzi82.shinju.logic;

import com.luzi82.homuvalue.RemoteGroup;
import com.luzi82.shinju.ShinjuCommon;
import com.luzi82.shinju.logic.Element.TypeModel;
import com.luzi82.shinju.logic.Element.ElementModelFactory;

public class Witch {

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
			return ShinjuCommon.WITCH_SIZE;
		}

	}

	public static final Logic sLogic = new Logic();

	//

	public static final String TYPE = "witch";

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
			return iVar.iWitch;
		}

	}

	public static class ModelFactory implements ElementModelFactory {

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
