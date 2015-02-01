package com.luzi82.shinju.logic;

import java.util.List;
import java.util.Map;

import com.luzi82.homuvalue.obj.ObjectListVariable;

public class BulletSimple {

	public static final String TYPE = "bullet_simple";

	public static class Eff extends Effect {

		public final VarField<Position.Var, Map<String, Object>> source_position;

		public final ObjectField<Long> dest_id;

		public final ObjectField<Long> start_turn;

		public final ObjectField<Long> end_turn;

		public Eff(World aWorld) {
			super(aWorld, TYPE);

			source_position = new VarField<Position.Var, Map<String, Object>>("source_position", Position.Var.class);
			addField(source_position);
			dest_id = new ObjectField<Long>("dest_id");
			addField(dest_id);
			start_turn = new ObjectField<Long>("start_turn");
			addField(start_turn);
			end_turn = new ObjectField<Long>("end_turn");
			addField(end_turn);
		}

		public long[] getXY(long mTurn) {
			Unit dest_unit = (Unit) iWorld.element_map.get().get(dest_id.get()).getTypeVar();

			long[] xy0 = source_position.get().getXY();
			long[] xy1 = dest_unit.getCenterXY();

			if (mTurn <= start_turn.get()) {
				return xy0;
			} else if (mTurn >= end_turn.get()) {
				return xy1;
			}

			long t0 = mTurn - start_turn.get();
			long t1 = end_turn.get() - mTurn;
			long t = end_turn.get() - start_turn.get();

			long x = (t0 * xy1[0] + t1 * xy0[0]) / t;
			long y = (t0 * xy1[1] + t1 * xy0[1]) / t;

			return new long[] { x, y };
		}

		public static class Factory implements com.luzi82.common.Factory<Eff> {

			private final World iWorld;

			public Factory(World aWorld) {
				iWorld = aWorld;
			}

			@Override
			public Eff create() {
				return new Eff(iWorld);
			}

		}

	}

	public static class Ski extends Skill.Type {

		public final ObjectField<Long> id;

		public final ObjectField<Long> cooldown;

		public final ObjectField<Long> range;

		public final VarField<ObjectListVariable<String>, List<String>> target_unit_type_list;

		public Ski(World aWorld, Unit aUnit) {
			super(aWorld, aUnit, TYPE);

			id = new ObjectField<Long>("id");
			addField(id);
			cooldown = new ObjectField<Long>("cooldown");
			addField(cooldown);
			range = new ObjectField<Long>("range");
			addField(range);
			target_unit_type_list = new VarField<ObjectListVariable<String>, List<String>>("target_unit_type_list", ObjectListVariable.createFactory(String.class));
			addField(target_unit_type_list);
		}

		public void act() {
			long[] center = iUnit.getCenterXY();

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
		}

		public static class Factory implements com.luzi82.common.Factory<Ski> {

			private final World iWorld;
			private final Unit iUnit;

			public Factory(World aWorld, Unit aUnit) {
				iWorld = aWorld;
				iUnit = aUnit;
			}

			@Override
			public Ski create() {
				return new Ski(iWorld, iUnit);
			}

		}

	}

}
