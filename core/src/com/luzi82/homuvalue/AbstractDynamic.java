package com.luzi82.homuvalue;

public abstract class AbstractDynamic<T> extends AbstractValue<T> implements Dynamic<T> {

	private final Dirty<T> mDirty;
	private T value;

	public AbstractDynamic() {
		super(false);
		mDirty = new Dirty<T>(this);
	}

	protected abstract T update();

	protected final void markDirty() {
		mDirty.set(true);
	}

	@Override
	public final T get() {
		if (mDirty.get()) {
			value = update();
			mDirty.set(false);
		}
		return value;
	}

	@Override
	public final void addListener(Value.Listener<T> listener) {
		mDirty.add(listener);
	}

	@Override
	public final void removeListener(AbstractValue.Listener<T> listener) {
		mDirty.remove(listener);
	}

	@Override
	public boolean dirty() {
		return mDirty.get();
	}
}