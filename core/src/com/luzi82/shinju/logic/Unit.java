package com.luzi82.shinju.logic;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.tuple.Pair;

import com.luzi82.homuvalue.obj.VariableMapVariable;

public abstract class Unit extends Element.Type {

	protected Unit(String aType, Element aElement) {
		super(aType, aElement);
	}

	public abstract long[] getXY();

	public abstract long getSize();

	public abstract ObjectField<Long> cooldownField();

	public abstract Hp hp();

	public abstract Mp mp();

	public abstract VariableMapVariable<Long, Skill, Map<String, Object>> skillMap();

	public long[] getCenterXY() {
		long halfsize = getSize() / 2;
		long[] xy = getXY();
		return new long[] { xy[0] + halfsize, xy[1] + halfsize };
	}

	@Override
	public void act_1_effect() {
		// do nothing
	}

	// block

	public static Set<Pair<String, String>> sBlockSet;

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

	public static void initBlock() {
		if (sBlockSet == null) {
			sBlockSet = new TreeSet<Pair<String, String>>();
			addBlock(Hero.TYPE, Hero.TYPE);
			addBlock(Hero.TYPE, Witch.TYPE);
			addBlock(Hero.TYPE, Seed.TYPE);
			addBlock(Witch.TYPE, Witch.TYPE);
			addBlock(Witch.TYPE, Seed.TYPE);
			addBlock(Seed.TYPE, Seed.TYPE);
		}
	}

	static {
		initBlock();
	}

}
