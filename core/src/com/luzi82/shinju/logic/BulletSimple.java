package com.luzi82.shinju.logic;

import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.luzi82.homuvalue.obj.ObjectListVariable;
import com.luzi82.shinju.ShinjuCommon;

public class BulletSimple {

	public static final String TYPE = "bullet_simple";

	public static class Eff extends Effect {

		public final VarField<Position, Map<String, Object>> source_position;

		public final ObjectField<Long> dest_id;

		public final ObjectField<Long> start_turn;

		public final ObjectField<Long> end_turn;

		public Eff(World aWorld, Element aElement) {
			super(TYPE, aWorld, aElement);

			Gdx.app.debug(getClass().getName(), "DXMt63xc construct");

			source_position = new VarField<Position, Map<String, Object>>("source_position", Position.class);
			addField(source_position);
			dest_id = new ObjectField<Long>("dest_id");
			addField(dest_id);
			start_turn = new ObjectField<Long>("start_turn");
			addField(start_turn);
			end_turn = new ObjectField<Long>("end_turn");
			addField(end_turn);

			source_position.set(new Position());
		}

		public float[] getXY(float mTurn) {
			Unit dest_unit = (Unit) iWorld.element_map.get().get(dest_id.get()).getTypeVar();

			long[] xy0 = source_position.get().getXY();
			long[] xy1 = dest_unit.getCenterXY();

			if (mTurn <= start_turn.get()) {
				return new float[] { (float) xy0[0], (float) xy0[1] };
			} else if (mTurn >= end_turn.get()) {
				return new float[] { (float) xy1[0], (float) xy1[1] };
			}

			float t0 = mTurn - start_turn.get();
			float t1 = end_turn.get() - mTurn;
			long t = end_turn.get() - start_turn.get();

			float x = (t0 * xy1[0] + t1 * xy0[0]) / t;
			float y = (t0 * xy1[1] + t1 * xy0[1]) / t;

			return new float[] { x, y };
		}

		public static class Factory implements com.luzi82.common.Factory<Eff> {

			private final World iWorld;

			private final Element iElement;

			public Factory(World aWorld, Element aElement) {
				iWorld = aWorld;
				iElement = aElement;
			}

			@Override
			public Eff create() {
				return new Eff(iWorld, iElement);
			}

		}

		@Override
		public void act_1_effect() {
			if (iWorld.turn.get() > end_turn.get()) {
				iElement.delete.set(true);
			}
		}

	}

	public static class Ski extends Skill.Type {

		public final ObjectField<Long> cooldown;

		public final ObjectField<Long> range;

		public final VarField<ObjectListVariable<String>, List<String>> target_unit_type_list;

		public Ski(World aWorld, Skill aSkill) {
			super(TYPE, aWorld, aSkill);

			cooldown = new ObjectField<Long>("cooldown");
			addField(cooldown);
			range = new ObjectField<Long>("range");
			addField(range);
			target_unit_type_list = new VarField<ObjectListVariable<String>, List<String>>("target_unit_type_list", ObjectListVariable.createFactory(String.class));
			addField(target_unit_type_list);

			target_unit_type_list.set(new ObjectListVariable<String>());
		}

		public void act() {
			long[] center = iSkill.iUnit.getCenterXY();

			long range2 = range.get();
			range2 *= range2;
			range2++;

			Element target = null;

			for (Element em : iWorld.element_map.get().values()) {
				String t = em.type.get();
				if (!target_unit_type_list.get().get().contains(t)) {
					continue;
				}
				Unit u = (Unit) em.getTypeVar();
				long[] targetCenter = u.getCenterXY();
				long len2 = Util.len2(center, targetCenter);
				if (len2 >= range2)
					continue;
				target = em;
				range2 = len2;
			}

			if (target == null)
				return;

			Element effElement = new Element(iWorld);
			effElement.type.set(TYPE);
			Eff eff = new Eff(iWorld, effElement);
			eff.source_position.get().x.set(center[0]);
			eff.source_position.get().y.set(center[1]);
			eff.dest_id.set(target.id.get());
			eff.start_turn.set(iWorld.turn.get());
			eff.end_turn.set(iWorld.turn.get() + Util.sqrt(range2)[1] / ShinjuCommon.CELL_SIZE); // TODO
			effElement.bullet_simple.set(eff);
			iWorld.addElement(effElement);

			iSkill.iUnit.cooldownField().set(iWorld.turn.get() + cooldown.get());
		}

		public static class Factory implements com.luzi82.common.Factory<Ski> {

			private final World iWorld;
			private final Skill iSkill;

			public Factory(World aWorld, Skill aSkill) {
				iWorld = aWorld;
				iSkill = aSkill;
			}

			@Override
			public Ski create() {
				return new Ski(iWorld, iSkill);
			}

		}

	}

}
