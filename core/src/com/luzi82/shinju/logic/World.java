package com.luzi82.shinju.logic;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.luzi82.common.Factory;
import com.luzi82.homuvalue.obj.MapVariable;
import com.luzi82.homuvalue.obj.ObjectVariable;
import com.luzi82.homuvalue.obj.VariableMapVariable;

public class World {

	public static class Var extends ObjectVariable {

		public final ObjectField<Long> turn;

		public final VarField<VariableMapVariable<Long, Element.Var, Map<String, Object>>, Map<Long, Map<String, Object>>> element_map;

		public final ObjectField<Long> next_id;

		public Var() {
			turn = new ObjectField<Long>("turn");
			addField(turn);
			element_map = new VarField<VariableMapVariable<Long, Element.Var, Map<String, Object>>, Map<Long, Map<String, Object>>>("element_map", VariableMapVariable.createFactory(Long.class, Factory.C.create(Element.Var.class)));
			addField(element_map);
			next_id = new ObjectField<Long>("next_id");
			addField(next_id);

			turn.set(0L);
			element_map.set(new VariableMapVariable<Long, Element.Var, Map<String, Object>>(Factory.C.create(Element.Var.class)));
			next_id.set(0L);
		}

	}

	public static class Model {

		public final Var iVar;

		public final HashMap<Long, Element.Model> mElementModelMap;

		public Model(Var var) {
			this.iVar = var;

			mElementModelMap = new HashMap<Long, Element.Model>();
			for (Element.Var elementVar : iVar.element_map.get().values()) {
				mElementModelMap.put(elementVar.id.get(), new Element.Model(elementVar, this));
			}
			iVar.element_map.get().addChangeListener(mMapListener);
		}

		public void addElement(Element.Var aElement) {
			long id = iVar.next_id.get();
			iVar.next_id.set(id + 1);
			aElement.id.set(id);
			iVar.element_map.get().put(id, aElement);
		}

		protected final MapVariable.ChangeListener<Long, Element.Var> mMapListener = new MapVariable.ChangeListener<Long, Element.Var>() {

			@Override
			public void onAdd(Long aK, Element.Var aI) {
//				Gdx.app.debug(getClass().getSimpleName(), "onMapAdd");
				mElementModelMap.put(aK, new Element.Model(aI, Model.this));
			}

			@Override
			public void onRemove(Long aK, Element.Var aI) {
				mElementModelMap.remove(aK);
			}
		};

	}

}
