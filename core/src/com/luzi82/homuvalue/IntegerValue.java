package com.luzi82.homuvalue;

public class IntegerValue {

	// sum

	private static class Sum extends Function<Integer> {

		Value<Integer>[] values;

		public Sum(Value<Integer>[] values) {
			this.values = values;
			for (Value<Integer> v : values) {
				addChild(v);
			}
		}

		@Override
		public Integer update() {
			int ret = 0;
			for (Value<Integer> v : values) {
				ret += v.get();
			}
			return ret;
		}
	}

	@SuppressWarnings("unchecked")
	public static Value<Integer> sum(Value<Integer> a, Value<Integer> b) {
		return sum(new Value[] { a, b });
	}

	public static Value<Integer> sum(Value<Integer>[] values) {
		return new Sum(values).optimize();
	}

	// negative

	private static class Negative extends Function<Integer> {

		final Value<Integer> v;

		public Negative(Value<Integer> v) {
			this.v = v;
			addChild(v);
		}

		@Override
		public Integer update() {
			return -v.get();
		}
	}

	public static Value<Integer> negative(Value<Integer> v) {
		return new Negative(v).optimize();
	}

	// product

	@SuppressWarnings("unchecked")
	public static Value<Integer> product(Value<Integer> a, Value<Integer> b) {
		return product(new Value[] { a, b });
	}

	public static Value<Integer> product(Value<Integer>[] values) {
		return new Product(values).optimize();
	}

	private static class Product extends Function<Integer> {

		Value<Integer>[] values;

		public Product(Value<Integer>[] values) {
			this.values = values;
			for (Value<Integer> v : values) {
				addChild(v);
			}
		}

		@Override
		public Integer update() {
			int ret = 1;
			for (Value<Integer> v : values) {
				ret *= v.get();
				if (ret == 0)
					return ret;
			}
			return ret;
		}

		@Override
		public Value<Integer> optimize() {
			for (Value<Integer> v : values) {
				if (v.isConstant() && v.get() == 0) {
					return new Constant<Integer>(0);
				}
			}
			return super.optimize();
		}
	}

	// div

	public static Value<Integer> div(Value<Integer> a, Value<Integer> b) {
		return new Div(a, b).optimize();
	}

	private static class Div extends Function<Integer> {

		Value<Integer> a;
		Value<Integer> b;

		public Div(Value<Integer> a, Value<Integer> b) {
			this.a = a;
			this.b = b;
			addChild(a);
			addChild(b);
		}

		@Override
		public Integer update() {
			return a.get() / b.get();
		}

		@Override
		public Value<Integer> optimize() {
			if (a.isConstant() && a.get() == 0) {
				return new Constant<Integer>(0);
			}
			return super.optimize();
		}
	}

	// min

	public static Value<Integer> min(Value<Integer> a, Value<Integer> b) {
		return new Min(a, b).optimize();
	}

	private static class Min extends Function<Integer> {

		Value<Integer> a;
		Value<Integer> b;

		public Min(Value<Integer> a, Value<Integer> b) {
			this.a = a;
			this.b = b;
			addChild(a);
			addChild(b);
		}

		@Override
		public Integer update() {
			return Math.min(a.get(), b.get());
		}
	}

	// max

	public static Value<Integer> max(Value<Integer> a, Value<Integer> b) {
		return new Max(a, b).optimize();
	}

	private static class Max extends Function<Integer> {

		Value<Integer> a;
		Value<Integer> b;

		public Max(Value<Integer> a, Value<Integer> b) {
			this.a = a;
			this.b = b;
			addChild(a);
			addChild(b);
		}

		@Override
		public Integer update() {
			return Math.max(a.get(), b.get());
		}
	}

	// min-max

	public static Value<Integer> minMax(Value<Integer> min, Value<Integer> t, Value<Integer> max) {
		return new MinMax(min, t, max).optimize();
	}

	private static class MinMax extends Function<Integer> {

		Value<Integer> min;
		Value<Integer> max;
		Value<Integer> t;

		public MinMax(Value<Integer> min, Value<Integer> t, Value<Integer> max) {
			this.min = min;
			this.max = max;
			this.t = t;
			addChild(min);
			addChild(max);
			addChild(t);
		}

		@Override
		public Integer update() {
			int minI = min.get();
			int maxI = max.get();
			int tI = t.get();

			if (maxI < minI)
				return tI;
			if (tI < minI)
				return minI;
			if (maxI < tI)
				return maxI;
			return tI;
		}
	}

	// round

	private static class Round extends Function<Integer> {

		final Value<? extends Number> v;

		public Round(Value<? extends Number> v) {
			this.v = v;
			addChild(v);
		}

		@Override
		public Integer update() {
			return Math.round(v.get().floatValue());
		}
	}

	public static Value<Integer> round(Value<? extends Number> v) {
		return new Round(v).optimize();
	}

	// round

	private static class Floor extends Function<Integer> {

		final Value<? extends Number> v;

		public Floor(Value<? extends Number> v) {
			this.v = v;
			addChild(v);
		}

		@Override
		public Integer update() {
			return (int) Math.floor(v.get().floatValue());
		}
	}

	public static Value<Integer> floor(Value<? extends Number> v) {
		return new Floor(v).optimize();
	}

	// round

	private static class Ceil extends Function<Integer> {

		final Value<? extends Number> v;

		public Ceil(Value<? extends Number> v) {
			this.v = v;
			addChild(v);
		}

		@Override
		public Integer update() {
			return (int) Math.ceil(v.get().floatValue());
		}
	}

	public static Value<Integer> ceil(Value<? extends Number> v) {
		return new Ceil(v).optimize();
	}
}
