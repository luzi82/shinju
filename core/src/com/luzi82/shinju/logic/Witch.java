package com.luzi82.shinju.logic;

import com.luzi82.homuvalue.RemoteGroup;
import com.luzi82.homuvalue.Value;
import com.luzi82.shinju.ShinjuCommon;
import com.luzi82.shinju.logic.Element.ElementModel;
import com.luzi82.shinju.logic.Element.ElementModelFactory;

public class Witch {

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

		public Model(Element.Var aVar) {
			this.iVar = aVar;
		}

		@Override
		public Var getElementData() {
			return iVar.iWitch;
		}

		@Override
		public Position.Var position() {
			return getElementData().iPosition;
		}

		@Override
		public long size() {
			return ShinjuCommon.WITCH_SIZE;
		}

	}

	public static class ModelFactory implements ElementModelFactory {

		@Override
		public String type() {
			return TYPE;
		}

		@Override
		public ElementModel createElementModel(Element.Var aVar) {
			return new Model(aVar);
		}

	}

}
