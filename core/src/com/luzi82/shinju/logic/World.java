package com.luzi82.shinju.logic;

import java.util.Map;

import com.luzi82.common.Factory;
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

	public void addElement(Element aElement) {
		long id = next_id.get();
		next_id.set(id + 1);
		aElement.id.set(id);
		element_map.get().put(id, aElement);
	}

}
