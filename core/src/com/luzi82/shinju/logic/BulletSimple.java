package com.luzi82.shinju.logic;

import com.badlogic.gdx.math.MathUtils;
import com.luzi82.homuvalue.RemoteGroup;
import com.luzi82.shinju.logic.Element.TypeModel;
import com.luzi82.shinju.logic.Element.TypeModelFactory;

public class BulletSimple {

	public static class Eff {

		public static class Logic extends Effect.Logic<Model> {

			protected Logic() {
				super(TYPE);
			}

			public float[] getXY(Model aModel, long mTurn) {
				Data var = aModel.iVar.iBulletSimple.get();

				Unit.Model source_model = (Unit.Model) (getModel(aModel, var.source_id).mTypeModel);
				Unit.Logic source_logic = (Unit.Logic) (getLogic(aModel, var.source_id));
				Unit.Model dest_model = (Unit.Model) (getModel(aModel, var.dest_id).mTypeModel);
				Unit.Logic dest_logic = (Unit.Logic) (getLogic(aModel, var.dest_id));

				float[] xy0 = source_logic.getCenterXY(source_model);
				float[] xy1 = dest_logic.getCenterXY(dest_model);

				if (mTurn <= var.start_turn) {
					return xy0;
				} else if (mTurn >= var.end_turn) {
					return xy1;
				}

				float t = mTurn - var.start_turn;
				float t1 = var.end_turn - var.start_turn;

				float x = MathUtils.lerp(xy0[0], xy1[0], t / t1);
				float y = MathUtils.lerp(xy0[1], xy1[1], t / t1);

				return new float[] { x, y };
			}

		}

		public static final Logic sLogic = new Logic();

		//

		public static final String TYPE = "bullet_simple";

		public static class Data {

			public long source_id;

			public long dest_id;

			public long start_turn;

			public long end_turn;

		}

		public static class Var extends RemoteGroup<Data> {

			public Var(Data aData) {
				super(aData);
			}

		}

		public static class Model extends Effect.Model {
			public Element.Var iVar;

			public Model(Element.Var aVar, World.Model aWorldModel) {
				super(aWorldModel);
				this.iVar = aVar;
			}

			@Override
			public Var getTypeData() {
				return iVar.iBulletSimple;
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

}
