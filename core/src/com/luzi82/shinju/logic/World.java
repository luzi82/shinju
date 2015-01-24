package com.luzi82.shinju.logic;

import java.util.SortedMap;
import java.util.TreeMap;

import com.luzi82.homuvalue.RemoteGroup;
import com.luzi82.homuvalue.RemoteMap;
import com.luzi82.homuvalue.RemoteMap.Constractor;

public class World {

	public static class Data {

		public Val.Data<Long> turn = new Val.Data<Long>();

		public SortedMap<Long, Unit.Data> unit_map = new TreeMap<Long, Unit.Data>();

		public Val.Data<Long> next_id = new Val.Data<Long>();

		public Data() {
			turn.value = 0L;
			next_id.value = 0L;
		}

	}

	public static class Var extends RemoteGroup<Data> {

		public final Val.Var<Long> iTurn;

		public final RemoteMap<Long, Unit.Var, Unit.Data> iUnitMap;

		public final Val.Var<Long> iNextId;

		public Var(Data aV) {
			super(aV);
			iTurn = new Val.Var<Long>(iV.turn);
			addChild(iTurn);
			iUnitMap = new RemoteMap<Long, Unit.Var, Unit.Data>(iV.unit_map, new Constractor<Unit.Var, Unit.Data>() {
				@Override
				public Unit.Var create(Unit.Data u) {
					return new Unit.Var(u);
				}
			});
			addChild(iUnitMap);
			iNextId = new Val.Var<Long>(iV.next_id);
			addChild(iNextId);
		}

		public void addUnit(Unit.Data aUnit) {
			long id = iNextId.get();
			iNextId.set(id + 1);
			aUnit.id = id;
			iUnitMap.remotePut(id, aUnit);
		}

	}

}
