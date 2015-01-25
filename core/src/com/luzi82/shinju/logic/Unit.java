package com.luzi82.shinju.logic;

import java.util.HashMap;
import java.util.Map;

import com.luzi82.shinju.logic.Element.ElementModel;

public class Unit {

	public static abstract class Logic<M extends Model> {

		public final String mType;

		protected Logic(String aType) {
			this.mType = aType;
		}

		public abstract long getX(M aModel);

		public abstract long getY(M aModel);

		public abstract long getSize(M aModel);
		
		// map

		public static final Map<String, Logic> sLogicMap = new HashMap<String, Logic>();

		protected static void addLogic(Logic aLogic) {
			sLogicMap.put(aLogic.mType, aLogic);
		}

		static {
			addLogic(Hero.sLogic);
			addLogic(Witch.sLogic);
		}
		
		// block

	}

	public static abstract class Model extends ElementModel {

		protected final World.Model iWorldModel;

		protected Model(World.Model aWorldModel) {
			iWorldModel = aWorldModel;
		}

	}

}
