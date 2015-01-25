package com.luzi82.shinju.logic;

import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.tuple.Pair;

import com.luzi82.shinju.logic.Element.TypeModel;

public class Unit {

	public static abstract class Logic<M extends Model> extends Element.TypeLogic<M> {

		protected Logic(String aType) {
			super(aType);
		}

		public abstract long[] getXY(M aModel);

		public abstract long getSize(M aModel);

		public float[] getCenterXY(M aModel) {
			float halfsize = getSize(aModel) / 2f;
			long[] xy = getXY(aModel);
			return new float[] { xy[0] + halfsize, xy[1] + halfsize };
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

	public static abstract class Model extends TypeModel {

		protected Model(World.Model aWorldModel) {
			super(aWorldModel);
		}

	}

}
