package com.luzi82.shinju.logic;

import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.luzi82.homuvalue.obj.ObjectListVariable;

public class BulletSimple {

	public static final String TYPE = "bullet_simple";

	public static class Eff extends Effect {

		public final VarField<Position, Map<String, Object>> source_position;

		public final ObjectField<Long> dest_id;

		public final ObjectField<Long> start_turn;

		public final ObjectField<Long> end_turn;

		public Eff(Element aElement) {
			super(TYPE, aElement);

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
			Unit dest_unit = (Unit) iElement.iWorld.element_map.get().get(dest_id.get()).getTypeVar();

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

			private final Element iElement;

			public Factory(Element aElement) {
				iElement = aElement;
			}

			@Override
			public Eff create() {
				return new Eff(iElement);
			}

		}

		@Override
		public void act_1_effect() {
			if (iElement.iWorld.turn.get() > end_turn.get()) {
				iElement.delete.set(true);
			}
		}

		public static Eff create(World aWorld) {
			Element element = Element.create(aWorld);
			Eff ret = new Eff(element);
			element.type.set(TYPE);
			element.bullet_simple.set(ret);
			return ret;
		}

	}

	public static class Ski extends Skill.Type {

		public final ObjectField<Long> cooldown;

		public final ObjectField<Long> range;

		public final ObjectField<Long> speed;

		public final VarField<ObjectListVariable<String>, List<String>> target_unit_type_list;

		public Ski(Skill aSkill) {
			super(TYPE, aSkill);

			cooldown = new ObjectField<Long>("cooldown");
			addField(cooldown);
			range = new ObjectField<Long>("range");
			addField(range);
			speed = new ObjectField<Long>("speed");
			addField(speed);
			target_unit_type_list = new VarField<ObjectListVariable<String>, List<String>>("target_unit_type_list", ObjectListVariable.createFactory(String.class));
			addField(target_unit_type_list);

			target_unit_type_list.set(new ObjectListVariable<String>());
		}

		public void act() {
			World world = iSkill.iUnit.iElement.iWorld;
			long[] center = iSkill.iUnit.getCenterXY();

			long range2 = range.get();
			range2 *= range2;
			range2++;

			Element target = null;

			for (Element em : world.element_map.get().values()) {
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

			long travelTime = Util.sqrt(range2)[1] / speed.get();

			// Element effElement = new Element(world);
			// effElement.type.set(TYPE);
			// Eff eff = new Eff(effElement);
			Eff eff = Eff.create(world);
			eff.source_position.get().x.set(center[0]);
			eff.source_position.get().y.set(center[1]);
			eff.dest_id.set(target.id.get());
			eff.start_turn.set(world.turn.get());
			eff.end_turn.set(world.turn.get() + travelTime); // TODO
			// effElement.bullet_simple.set(eff);
			// world.addElement(effElement);

			iSkill.iUnit.cooldownField().set(world.turn.get() + cooldown.get());
		}

		public static class Factory implements com.luzi82.common.Factory<Ski> {

			private final Skill iSkill;

			public Factory(Skill aSkill) {
				iSkill = aSkill;
			}

			@Override
			public Ski create() {
				return new Ski(iSkill);
			}

		}

		public static Ski create(Unit aUnit) {
			Skill skill = Skill.create(aUnit);
			Ski ret = new Ski(skill);
			skill.type.set(TYPE);
			skill.bullet_simple.set(ret);
			return ret;
		}

	}

}
