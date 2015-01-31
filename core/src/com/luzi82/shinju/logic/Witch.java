package com.luzi82.shinju.logic;

import java.util.Map;

import com.luzi82.common.Factory;
import com.luzi82.homuvalue.obj.ObjectVariable;
import com.luzi82.shinju.ShinjuCommon;
import com.luzi82.shinju.logic.Element.TypeModel;
import com.luzi82.shinju.logic.Element.TypeModelFactory;

public class Witch {

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
			return ShinjuCommon.WITCH_SIZE;
		}

	}

	public static final Logic sLogic = new Logic();

	//

	public static final String TYPE = "witch";

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
			return iVar.witch.get();
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
