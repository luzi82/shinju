package com.luzi82.shinju.logic;

import com.luzi82.homuvalue.RemoteGroup;

public class Unit {

	public static class Data {

		public long id;

		public Val.Data<String> type = new Val.Data<String>();

		public Hero.Data hero;

	}

	public static class Var extends RemoteGroup<Data> {

		public final Val.Var<String> iType;

		public Hero.Var iHero;

		public Var(Data aData) {
			super(aData);
			iType = new Val.Var<String>(iV.type);
			addChild(iType);
			if (iV.hero != null) {
				iHero = new Hero.Var(iV.hero);
				addChild(iHero);
			}
		}

		public long id() {
			return iV.id;
		}

	}

}
