package com.luzi82.shinju.logic;

import org.apache.commons.lang3.ArrayUtils;

import com.badlogic.gdx.math.MathUtils;
import com.luzi82.homuvalue.RemoteGroup;
import com.luzi82.homuvalue.Value;
import com.luzi82.homuvalue.Variable;
import com.luzi82.shinju.logic.Element.TypeModel;
import com.luzi82.shinju.logic.Element.TypeModelFactory;

public class BulletSimple {

	public static class Eff {

		public static class Logic extends Effect.Logic<Model> {

			protected Logic() {
				super(TYPE);
			}

			public long[] getXY(Model aModel, long mTurn) {
				Data var = aModel.iVar.iBulletSimple.get();

				Unit.Model source_model = (Unit.Model) (getModel(aModel, var.source_id).mTypeModel);
				Unit.Logic source_logic = (Unit.Logic) (getLogic(aModel, var.source_id));
				Unit.Model dest_model = (Unit.Model) (getModel(aModel, var.dest_id).mTypeModel);
				Unit.Logic dest_logic = (Unit.Logic) (getLogic(aModel, var.dest_id));

				long[] xy0 = source_logic.getCenterXY(source_model);
				long[] xy1 = dest_logic.getCenterXY(dest_model);

				if (mTurn <= var.start_turn) {
					return xy0;
				} else if (mTurn >= var.end_turn) {
					return xy1;
				}

				long t0 = mTurn - var.start_turn;
				long t1 = var.end_turn - mTurn;
				long t = var.end_turn - var.start_turn;

				long x = (t0 * xy1[0] + t1 * xy0[0]) / t;
				long y = (t0 * xy1[1] + t1 * xy0[1]) / t;

				return new long[] { x, y };
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

	public static class Ski {

		public static class Logic extends Skill.TypeLogic<Model> {

			protected Logic() {
				super(TYPE);
			}

			@Override
			public void act(Element.Model aModel, Skill.Model aSkillModel) {
				World.Model worldModel = aModel.iWorldModel;
				String type = aModel.getType();
				Unit.Model unitModel = (Unit.Model) aModel.mTypeModel;
				Unit.Logic unitLogic = (Unit.Logic) Element.TypeLogic.sLogicMap.get(type);
				BulletSimple.Ski.Data skillData = aSkillModel.iVar.get().bullet_simple;

				long[] center = unitLogic.getCenterXY(unitModel);

				long range2 = skillData.range.value;
				range2 *= range2;
				range2++;

				Element.Model target = null;

				for (Element.Model em : worldModel.mElementModelMap.values()) {
					String t = em.getType();
					if (!ArrayUtils.contains(skillData.target_unit_type_list, t)) {
						continue;
					}
					Element.TypeModel etm = em.mTypeModel;
					Unit.Logic logic = (Unit.Logic) Element.TypeLogic.sLogicMap.get(t);
					long[] targetCenter = logic.getCenterXY((Unit.Model) etm);
					long len2 = Util.len2(center, targetCenter);
					if (len2 >= range2)
						continue;
					target = em;
					range2 = len2;
				}

				if (target == null)
					return;
			}

		}

		public static final Logic sLogic = new Logic();

		//

		public static final String TYPE = "bullet_simple";

		public static class Data {

			public long id;

			public long cooldown;

			public Val.Data<Long> range = new Val.Data<Long>();

			public String[] target_unit_type_list;

		}

		public static class Var extends RemoteGroup<Data> {

			public final Variable<Long> iRange;

			public Var(Data aData) {
				super(aData);
				iRange = new Val.Var<Long>(iV.range);
				addChild(iRange);
			}

		}

		public static class Model extends Skill.TypeModel {
			public Skill.Var iVar;

			public Model(Skill.Var aVar, World.Model aWorldModel) {
				super(aWorldModel);
				this.iVar = aVar;
			}

			@Override
			public Value getTypeData() {
				return iVar.mBulletSimpleVar;
			}

		}

		public static class ModelFactory implements Skill.TypeModelFactory {

			@Override
			public String type() {
				return TYPE;
			}

			@Override
			public Skill.TypeModel createElementModel(Skill.Var aVar, World.Model aWorldModel) {
				return new Model(aVar, aWorldModel);
			}

		}

	}

}
