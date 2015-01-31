package com.luzi82.shinju.logic;

import java.util.List;

import com.luzi82.homuvalue.Value;
import com.luzi82.homuvalue.obj.ObjectListVariable;
import com.luzi82.homuvalue.obj.ObjectVariable;
import com.luzi82.shinju.logic.Element.TypeModel;
import com.luzi82.shinju.logic.Element.TypeModelFactory;

public class BulletSimple {

	public static class Eff {

		public static class Logic extends Effect.Logic<Model> {

			protected Logic() {
				super(TYPE);
			}

			public long[] getXY(Model aModel, long mTurn) {
				Var var = aModel.iVar.bullet_simple.get();

				Unit.Model source_model = (Unit.Model) (getModel(aModel, var.source_id.get()).mTypeModel);
				Unit.Logic source_logic = (Unit.Logic) (getLogic(aModel, var.source_id.get()));
				Unit.Model dest_model = (Unit.Model) (getModel(aModel, var.dest_id.get()).mTypeModel);
				Unit.Logic dest_logic = (Unit.Logic) (getLogic(aModel, var.dest_id.get()));

				long[] xy0 = source_logic.getCenterXY(source_model);
				long[] xy1 = dest_logic.getCenterXY(dest_model);

				if (mTurn <= var.start_turn.get()) {
					return xy0;
				} else if (mTurn >= var.end_turn.get()) {
					return xy1;
				}

				long t0 = mTurn - var.start_turn.get();
				long t1 = var.end_turn.get() - mTurn;
				long t = var.end_turn.get() - var.start_turn.get();

				long x = (t0 * xy1[0] + t1 * xy0[0]) / t;
				long y = (t0 * xy1[1] + t1 * xy0[1]) / t;

				return new long[] { x, y };
			}

		}

		public static final Logic sLogic = new Logic();

		//

		public static final String TYPE = "bullet_simple";

		public static class Var extends ObjectVariable {

			public final ObjectField<Long> source_id;

			public final ObjectField<Long> dest_id;

			public final ObjectField<Long> start_turn;

			public final ObjectField<Long> end_turn;

			public Var() {
				source_id = new ObjectField<Long>("source_id");
				addField(source_id);
				dest_id = new ObjectField<Long>("dest_id");
				addField(dest_id);
				start_turn = new ObjectField<Long>("start_turn");
				addField(start_turn);
				end_turn = new ObjectField<Long>("end_turn");
				addField(end_turn);
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
				return iVar.bullet_simple.get();
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
				BulletSimple.Ski.Var skillVar = aSkillModel.iVar.bullet_simple.get();

				long[] center = unitLogic.getCenterXY(unitModel);

				long range2 = skillVar.range.get();
				range2 *= range2;
				range2++;

				Element.Model target = null;

				for (Element.Model em : worldModel.mElementModelMap.values()) {
					String t = em.getType();
					if (!skillVar.target_unit_type_list.get().get().contains(t)) {
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

		public static class Var extends ObjectVariable {

			public final ObjectField<Long> id;

			public final ObjectField<Long> cooldown;

			public final ObjectField<Long> range;

			public final VarField<ObjectListVariable<String>, List<String>> target_unit_type_list;

			public Var() {
				id = new ObjectField<Long>("id");
				addField(id);
				cooldown = new ObjectField<Long>("cooldown");
				addField(cooldown);
				range = new ObjectField<Long>("range");
				addField(range);
				target_unit_type_list = new VarField<ObjectListVariable<String>, List<String>>("target_unit_type_list", ObjectListVariable.createFactory(String.class));
				addField(target_unit_type_list);
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
				return iVar.bullet_simple.get();
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
