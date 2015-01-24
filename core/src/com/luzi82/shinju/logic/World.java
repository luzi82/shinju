package com.luzi82.shinju.logic;

import java.util.HashMap;
import java.util.SortedMap;
import java.util.TreeMap;

import com.badlogic.gdx.Gdx;
import com.luzi82.homuvalue.RemoteGroup;
import com.luzi82.homuvalue.RemoteMap;
import com.luzi82.homuvalue.RemoteMap.Constractor;
import com.luzi82.homuvalue.RemoteMap.MapListener;

public class World {

	public static class Data {

		public Val.Data<Long> turn = new Val.Data<Long>();

		public SortedMap<Long, Element.Data> element_map = new TreeMap<Long, Element.Data>();

		public Val.Data<Long> next_id = new Val.Data<Long>();

		public Data() {
			turn.value = 0L;
			next_id.value = 0L;
		}

	}

	public static class Var extends RemoteGroup<Data> {

		public final Val.Var<Long> iTurn;

		public final RemoteMap<Long, Element.Var, Element.Data> iElementMap;

		public final Val.Var<Long> iNextId;

		public Var(Data aV) {
			super(aV);
			iTurn = new Val.Var<Long>(iV.turn);
			addChild(iTurn);
			iElementMap = new RemoteMap<Long, Element.Var, Element.Data>(iV.element_map, new Constractor<Element.Var, Element.Data>() {
				@Override
				public Element.Var create(Element.Data u) {
					return new Element.Var(u);
				}
			});
			addChild(iElementMap);
			iNextId = new Val.Var<Long>(iV.next_id);
			addChild(iNextId);
		}

	}

	public static class Model {

		public final Var iVar;

		public final HashMap<Long, Element.Model> mElementModelMap;

		public Model(Var var) {
			this.iVar = var;

			mElementModelMap = new HashMap<Long, Element.Model>();
			for (Element.Var elementVar : iVar.iElementMap.values()) {
				mElementModelMap.put(elementVar.id(), new Element.Model(elementVar));
			}
			iVar.iElementMap.addMapListener(mMapListener);
		}

		public void addElement(Element.Data aElement) {
			long id = iVar.iNextId.get();
			iVar.iNextId.set(id + 1);
			aElement.id = id;
			iVar.iElementMap.remotePut(id, aElement);
		}

		protected final MapListener<Long, Element.Var, Element.Data> mMapListener = new MapListener<Long, Element.Var, Element.Data>() {
			@Override
			public void onMapAdd(RemoteMap<Long, Element.Var, Element.Data> map, Long key, Element.Var value, Element.Data e) {
				Gdx.app.debug(getClass().getSimpleName(), "onMapAdd");
				mElementModelMap.put(key, new Element.Model(value));
			}

			@Override
			public void onMapRemove(RemoteMap<Long, Element.Var, Element.Data> map, Long key, Element.Var value, Element.Data e) {
				mElementModelMap.remove(key);
			}
		};

	}

}
