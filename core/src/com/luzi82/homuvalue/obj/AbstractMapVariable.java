package com.luzi82.homuvalue.obj;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import com.luzi82.common.WeakList;
import com.luzi82.homuvalue.Dirty;

public abstract class AbstractMapVariable<K, I, O> implements MapVariable<K, I, O> {

	protected final Dirty<Map<K, O>> mDirty;
	protected final HashMap<K, I> mMap;

	public AbstractMapVariable() {
		mDirty = new Dirty<Map<K, O>>(this);
		mMap = new HashMap<K, I>();
	}

	@Override
	public void addListener(Listener<Map<K, O>> listener) {
		mDirty.add(listener);
	}

	@Override
	public void removeListener(Listener<Map<K, O>> listener) {
		mDirty.remove(listener);
	}

	@Override
	public boolean isConstant() {
		return false;
	}

	@Override
	public boolean dirty() {
		return mDirty.get();
	}

	@Override
	public Map<K, O> get() {
		HashMap<K, O> ret = new HashMap<K, O>();
		for (Map.Entry<K, I> me : mMap.entrySet()) {
			ret.put(me.getKey(), toO(me.getValue()));
		}
		mDirty.set(false);
		return ret;
	}

	protected abstract O toO(I i);

	@Override
	public void set(Map<K, O> t) {
		innerClear();
		for (Map.Entry<K, O> me : t.entrySet()) {
			I i = toI(me.getValue());
			innerPut(me.getKey(), i);
		}
		mDirty.set(true);
	}

	protected abstract I toI(O o);

	//

	@Override
	public void addChangeListener(ChangeListener<K, I> aListener) {
		mChangeListenerManager.add(aListener);
	}

	@Override
	public void removeChangeListener(ChangeListener<K, I> aListener) {
		mChangeListenerManager.remove(aListener);
	}

	public static class ChangeListenerManager<K, I> extends WeakList<ChangeListener<K, I>> {

		public void onAdd(K aK, I aI) {
			clearNull();
			for (ChangeListener<K, I> c : this) {
				c.onAdd(aK, aI);
			}
		}

		public void onRemove(K aK, I aI) {
			clearNull();
			for (ChangeListener<K, I> c : this) {
				c.onRemove(aK, aI);
			}
		}

	}

	protected ChangeListenerManager<K, I> mChangeListenerManager = new ChangeListenerManager<K, I>();

	//

	@Override
	public void clear() {
		innerClear();
		mDirty.set(true);
	}

	@Override
	public boolean containsKey(Object key) {
		return mMap.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return mMap.containsValue(value);
	}

	@Override
	public Set<java.util.Map.Entry<K, I>> entrySet() {
		return mMap.entrySet();
	}

	@Override
	public I get(Object key) {
		return mMap.get(key);
	}

	@Override
	public boolean isEmpty() {
		return mMap.isEmpty();
	}

	@Override
	public Set<K> keySet() {
		return mMap.keySet();
	}

	@Override
	public I put(K key, I value) {
		I ret = innerRemove(key);
		innerPut(key, value);
		mDirty.set(true);
		return ret;
	}

	@Override
	public void putAll(Map<? extends K, ? extends I> m) {
		for (Map.Entry<? extends K, ? extends I> me : m.entrySet()) {
			innerRemove(me.getKey());
			innerPut(me.getKey(), me.getValue());
		}
		if (m.size() > 0) {
			mDirty.set(true);
		}
	}

	@Override
	public I remove(Object key) {
		I ret = innerRemove(key);
		mDirty.set(true);
		return ret;
	}

	@Override
	public int size() {
		return mMap.size();
	}

	@Override
	public Collection<I> values() {
		return mMap.values();
	}

	protected void innerClear() {
		Map<K, I> tmp = (Map<K, I>) mMap.clone();
		mMap.clear();
		for (Map.Entry<K, I> me : tmp.entrySet()) {
			mChangeListenerManager.onRemove(me.getKey(), me.getValue());
		}
	}

	protected void innerPut(K key, I value) {
		mMap.put(key, value);
		mChangeListenerManager.onAdd(key, value);
	}

	protected I innerRemove(Object key) {
		I old = mMap.remove(key);
		if (old != null) {
			mChangeListenerManager.onRemove((K) key, old);
		}
		return old;
	}

}
