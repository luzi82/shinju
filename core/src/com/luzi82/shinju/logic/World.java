package com.luzi82.shinju.logic;

import java.util.LinkedList;
import java.util.Map;

import com.luzi82.homuvalue.obj.ObjectVariable;
import com.luzi82.homuvalue.obj.VariableMapVariable;

public class World extends ObjectVariable {

	public final ObjectField<Long> turn;

	public final VarField<VariableMapVariable<Long, Element, Map<String, Object>>, Map<Long, Map<String, Object>>> element_map;

	public final ObjectField<Long> next_id;

	public World() {
		turn = new ObjectField<Long>("turn");
		addField(turn);
		element_map = new VarField<VariableMapVariable<Long, Element, Map<String, Object>>, Map<Long, Map<String, Object>>>("element_map", VariableMapVariable.createFactory(Long.class, new Element.Factory(this)));
		addField(element_map);
		next_id = new ObjectField<Long>("next_id");
		addField(next_id);

		turn.set(0L);
		element_map.set(new VariableMapVariable<Long, Element, Map<String, Object>>(new Element.Factory(this)));
		next_id.set(0L);
	}

//	public void addElement(Element aElement) {
//		long id = nextId();
//		aElement.id.set(id);
//		element_map.get().put(id, aElement);
//	}

	public void act() {
		// Gdx.app.debug(getClass().getName(), "oTWmEPSX act");
		long t = turn.get();
		turn.set(t + 1);

		LinkedList<Element> element_list = new LinkedList<Element>(element_map.get().values());

		for (Element e : element_list) {
			e.act_0_unit();
		}

		for (Element e : element_list) {
			e.act_1_effect();
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

}
