package com.luzi82.shinju.logic;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.tuple.Pair;

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

		public static Map<String, Logic> sLogicMap = new HashMap<String, Logic>();

		protected static void addLogic(Logic aLogic) {
			sLogicMap.put(aLogic.mType, aLogic);
		}

		// block

		public static final Set<Pair<String, String>> sBlockSet = new TreeSet<Pair<String, String>>();

		protected static void addBlock(String aA, String aB) {
			if (aA.equals(aB)) {
				sBlockSet.add(Pair.of(aA, aB));
			} else {
				sBlockSet.add(Pair.of(aA, aB));
				sBlockSet.add(Pair.of(aB, aA));
			}
		}

		public static boolean isBlock(String aA, String aB) {
			return sBlockSet.contains(Pair.of(aA, aB));
		}

	}

	public static abstract class Model extends ElementModel {

		protected final World.Model iWorldModel;

		protected Model(World.Model aWorldModel) {
			iWorldModel = aWorldModel;
		}

	}

}
