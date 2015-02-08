package com.luzi82.homuvalue;

public class FloatValue {

	// sum

	private static class Sum extends Function<Float> {

		Value<? extends Number>[] values;

		public Sum(Value<? extends Number>[] values) {
			this.values = values;
			for (Value<? extends Number> v : values) {
				addChild(v);
			}
		}

		@Override
		public Float update() {
			float ret = 0;
			for (Value<? extends Number> v : values) {
				ret += v.get().floatValue();
			}
			return ret;
		}
	}

	@SuppressWarnings("unchecked")
	public static Value<Float> sum(Value<? extends Number> a, Value<? extends Number> b) {
		return sum(new Value[] { a, b });
	}

	public static Value<Float> sum(Value<? extends Number>[] values) {
		return new Sum(values).optimize();
	}

	// negative

	private static class Negative extends Function<Float> {

		final Value<? extends Number> v;

		public Negative(Value<? extends Number> v) {
			this.v = v;
			addChild(v);
		}

		@Override
		public Float update() {
			return -v.get().floatValue();
		}
	}

	public static Value<Float> negative(Value<? extends Number> v) {
		return new Negative(v).optimize();
	}

	// product

	@SuppressWarnings("unchecked")
	public static Value<Float> product(Value<? extends Number> a, Value<? extends Number> b) {
		return product(new Value[] { a, b });
	}

	public static Value<Float> product(Value<? extends Number>[] values) {
		return new Product(values).optimize();
	}

	private static class Product extends Function<Float> {

		Value<? extends Number>[] values;

		public Product(Value<? extends Number>[] values) {
			this.values = values;
			for (Value<? extends Number> v : values) {
				addChild(v);
			}
		}

		@Override
		public Float update() {
			float ret = 1;
			for (Value<? extends Number> v : values) {
				ret *= v.get().floatValue();
				if (ret == 0)
					return ret;
			}
			return ret;
		}

		@Override
		public Value<Float> optimize() {
			for (Value<? extends Number> v : values) {
				if (v.isConstant() && v.get().floatValue() == 0) {
					return new Constant<Float>(0f);
				}
			}
			return super.optimize();
		}
	}

	// div

	public static Value<Float> div(Value<? extends Number> a, Value<? extends Number> b) {
		return new Div(a, b).optimize();
	}

	private static class Div extends Function<Float> {

		Value<? extends Number> a;
		Value<? extends Number> b;

		public Div(Value<? extends Number> a, Value<? extends Number> b) {
			this.a = a;
			this.b = b;
			addChild(a);
			addChild(b);
		}

		@Override
		public Float update() {
			return a.get().floatValue() / b.get().floatValue();
		}

		@Override
		public Value<Float> optimize() {
			if (a.isConstant() && a.get().floatValue() == 0) {
				return new Constant<Float>(0f);
			}
			return super.optimize();
		}
	}

	// min

	public static Value<Float> min(Value<? extends Number> a, Value<? extends Number> b) {
		return new Min(a, b).optimize();
	}

	private static class Min extends Function<Float> {

		Value<? extends Number> a;
		Value<? extends Number> b;

		public Min(Value<? extends Number> a, Value<? extends Number> b) {
			this.a = a;
			this.b = b;
			addChild(a);
			addChild(b);
		}

		@Override
		public Float update() {
			return Math.min(a.get().floatValue(), b.get().floatValue());
		}
	}

	// max

	public static Value<Float> max(Value<? extends Number> a, Value<? extends Number> b) {
		return new Max(a, b).optimize();
	}

	private static class Max extends Function<Float> {

		Value<? extends Number> a;
		Value<? extends Number> b;

		public Max(Value<? extends Number> a, Value<? extends Number> b) {
			this.a = a;
			this.b = b;
			addChild(a);
			addChild(b);
		}

		@Override
		public Float update() {
			return Math.max(a.get().floatValue(), b.get().floatValue());
		}
	}

	// min-max

	public static Value<Float> minMax(Value<? extends Number> min, Value<? extends Number> t, Value<? extends Number> max) {
		return new MinMax(min, t, max).optimize();
	}

	private static class MinMax extends Function<Float> {

		Value<? extends Number> min;
		Value<? extends Number> max;
		Value<? extends Number> t;

		public MinMax(Value<? extends Number> min, Value<? extends Number> t, Value<? extends Number> max) {
			this.min = min;
			this.max = max;
			this.t = t;
			addChild(min);
			addChild(max);
			addChild(t);
		}

		@Override
		public Float update() {
			float minF = min.get().floatValue();
			float maxF = max.get().floatValue();
			float tF = t.get().floatValue();

			if (maxF < minF)
				return tF;
			if (tF < minF)
				return minF;
			if (maxF < tF)
				return maxF;
			return tF;
		}
	}

	// linear

	public static Value<Float> linear(Value<? extends Number> u0, Value<? extends Number> u1, Value<? extends Number> v0, Value<? extends Number> v1, Value<? extends Number> t) {
		return new Linear(u0, u1, v0, v1, t).optimize();
	}

	private static class Linear extends Function<Float> {

		Value<? extends Number> u0;
		Value<? extends Number> u1;
		Value<? extends Number> v0;
		Value<? extends Number> v1;
		Value<? extends Number> t;

		public Linear(Value<? extends Number> u0, Value<? extends Number> u1, Value<? extends Number> v0, Value<? extends Number> v1, Value<? extends Number> t) {
			this.u0 = u0;
			this.u1 = u1;
			this.v0 = v0;
			this.v1 = v1;
			this.t = t;
			addChild(u0);
			addChild(u1);
			addChild(v0);
			addChild(v1);
			addChild(t);
		}

		@Override
		public Float update() {
			float u0f = u0.get().floatValue();
			float u1f = u1.get().floatValue();
			float v0f = v0.get().floatValue();
			float v1f = v1.get().floatValue();
			float tf = t.get().floatValue();
			return v0f + (v1f - v0f) * (tf - u0f) / (u1f - u0f);
		}
	}

}
