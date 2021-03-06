package com.luzi82.shinju.logic;

import java.util.LinkedList;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.TimeUtils;
import com.luzi82.homuvalue.obj.ObjectVariable;
import com.luzi82.homuvalue.obj.VariableMapVariable;
import com.luzi82.shinju.logic.Hero.Factory;

public class World extends ObjectVariable {

	public final ObjectField<Long> turn = new ObjectField<Long>("turn", this);

	public final VarField<VariableMapVariable<Long, Element, Map<String, Object>>, Map<Long, Map<String, Object>>> element_map;

	public final ObjectField<Long> next_id = new ObjectField<Long>("next_id", this);

	public final VarField<Rand, Map<String, Object>> rand = new VarField<Rand, Map<String, Object>>("rand", Factory.C.create(Rand.class), this);

	public World() {
		element_map = new VarField<VariableMapVariable<Long, Element, Map<String, Object>>, Map<Long, Map<String, Object>>>("element_map", VariableMapVariable.createFactory(Long.class, new Element.Factory(this)), this);

		turn.set(0L);
		element_map.set(new VariableMapVariable<Long, Element, Map<String, Object>>(new Element.Factory(this)));
		next_id.set(0L);
		rand.set(new Rand());
		rand.get().seed.set(TimeUtils.nanoTime());
	}

	// public void addElement(Element aElement) {
	// long id = nextId();
	// aElement.id.set(id);
	// element_map.get().put(id, aElement);
	// }

	public void act() {
		Gdx.app.debug("oTWmEPSX", "World.act");

		long t = turn.get();
		turn.set(t + 1);

		LinkedList<Element> element_list = new LinkedList<Element>(element_map.get().values());

		for (Element e : element_list) {
			e.act_0_unit();
		}

		for (Element e : element_list) {
			e.act_1_effect();
		}

		for (Element e : element_list) {
			e.act_2_transform();
		}

		LinkedList<Long> deleteIdList = new LinkedList<Long>();
		for (Element e : element_map.get().values()) {
			if (e.delete.get())
				deleteIdList.add(e.id.get());
		}
		for (Long id : deleteIdList) {
			element_map.get().remove(id);
		}
	}

	public long nextId() {
		long ret = next_id.get();
		next_id.set(ret + 1);
		return ret;
	}

	public boolean isPositionOk(long aElementId, String aType, long[] aXy, long aSize) {
		for (Element m : element_map.get().values()) {
			String type = m.type.get();
			if (m.id.get().equals(aElementId))
				continue;
			if (!Unit.isBlock(aType, type))
				continue;
			Unit u = (Unit) m.getTypeVar();
			long[] xy = u.getXY();
			long size = u.getSize();
			if (aXy[0] + aSize <= xy[0])
				continue;
			if (aXy[1] + aSize <= xy[1])
				continue;
			if (aXy[0] >= xy[0] + size)
				continue;
			if (aXy[1] >= xy[1] + size)
				continue;
			return false;
		}
		return true;
	}

}
