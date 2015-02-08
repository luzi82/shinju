package com.luzi82.homuvalue.obj;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import com.luzi82.common.WeakList;
import com.luzi82.homuvalue.Dirty;

public abstract class AbstractListVariable<I, O> implements ListVariable<I, O> {

	protected final Dirty<List<O>> mDirty;
	protected final LinkedList<I> mList;

	public AbstractListVariable() {
		mDirty = new Dirty<List<O>>(this);
		mList = new LinkedList<I>();
	}

	@Override
	public void addListener(Listener<List<O>> listener) {
		mDirty.add(listener);
	}

	@Override
	public void removeListener(Listener<List<O>> listener) {
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
	public List<O> get() {
		LinkedList<O> ret = new LinkedList<O>();
		for (I i : mList) {
			ret.add(toO(i));
		}
		mDirty.set(false);
		return ret;
	}

	protected abstract O toO(I i);

	@Override
	public void set(List<O> t) {
		LinkedList<I> tmp = new LinkedList<I>(mList);
		mList.clear();
		for (I i : tmp) {
			afterRemove(i);
		}
		for (O o : t) {
			I i = toI(o);
			beforeAdd(i);
			mList.add(i);
			afterAdd(i);
		}
		mDirty.set(true);
	}

	protected abstract I toI(O o);

	//

	@Override
	public void addChangeListener(ChangeListener<I> aListener) {
		mChangeListenerManager.add(aListener);
	}

	@Override
	public void removeChangeListener(ChangeListener<I> aListener) {
		mChangeListenerManager.remove(aListener);
	}

	public static class ChangeListenerManager<I> extends WeakList<ChangeListener<I>> {

		public void onAdd(I aI) {
			clearNull();
			for (ChangeListener<I> c : this) {
				c.onAdd(aI);
			}
		}

		public void onRemove(I aI) {
			clearNull();
			for (ChangeListener<I> c : this) {
				c.onRemove(aI);
			}
		}

	}

	protected ChangeListenerManager<I> mChangeListenerManager = new ChangeListenerManager<I>();

	//

	@Override
	public boolean add(I e) {
		beforeAdd(e);
		mList.add(e);
		afterAdd(e);
		mDirty.set(true);
		return true;
	}

	@Override
	public void add(int index, I element) {
		beforeAdd(element);
		mList.add(index, element);
		afterAdd(element);
		mDirty.set(true);
	}

	@Override
	public boolean addAll(Collection<? extends I> c) {
		for (I i : c) {
			beforeAdd(i);
		}
		mList.addAll(c);
		for (I i : c) {
			afterAdd(i);
		}
		mDirty.set(true);
		return true;
	}

	@Override
	public boolean addAll(int index, Collection<? extends I> c) {
		for (I i : c) {
			beforeAdd(i);
		}
		mList.addAll(index, c);
		for (I i : c) {
			afterAdd(i);
		}
		mDirty.set(true);
		return true;
	}

	@Override
	public void clear() {
		List<I> tmp = (List<I>) mList.clone();
		clear();
		for (I i : tmp) {
			afterRemove(i);
		}
		mDirty.set(true);
	}

	@Override
	public boolean contains(Object o) {
		return mList.contains(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return mList.containsAll(c);
	}

	@Override
	public I get(int index) {
		return mList.get(index);
	}

	@Override
	public int indexOf(Object o) {
		return mList.indexOf(o);
	}

	@Override
	public boolean isEmpty() {
		return mList.isEmpty();
	}

	@Override
	public Iterator<I> iterator() {
		// TODO, handle Iterator.remove
		return mList.iterator();
	}

	@Override
	public int lastIndexOf(Object o) {
		return mList.lastIndexOf(o);
	}

	@Override
	public ListIterator<I> listIterator() {
		// TODO, handle function
		return mList.listIterator();
	}

	@Override
	public ListIterator<I> listIterator(int index) {
		// TODO, handle function
		return mList.listIterator(index);
	}

	@Override
	public I remove(int index) {
		I ret = mList.get(index);
		afterRemove(ret);
		mDirty.set(true);
		return ret;
	}

	@Override
	public boolean remove(Object o) {
		boolean ret = mList.remove(o);
		if (ret) {
			afterRemove((I) o);
			mDirty.set(true);
		}
		return ret;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		boolean ret = false;
		for (Object o : c) {
			if (mList.remove(o)) {
				afterRemove((I) o);
				ret = true;
			}
		}
		mDirty.set(true);
		if (ret) {
			mDirty.set(true);
		}
		return ret;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		LinkedList<I> tmp = new LinkedList<I>();
		for (I i : mList) {
			if (!c.contains(i)) {
				tmp.add(i);
			}
		}
		for (I i : tmp) {
			mList.remove(i);
			afterRemove(i);
		}
		boolean ret = !tmp.isEmpty();
		if (ret) {
			mDirty.set(true);
		}
		return ret;
	}

	@Override
	public I set(int index, I element) {
		beforeAdd(element);
		I ret = mList.set(index, element);
		if (ret != null) {
			afterRemove(ret);
		}
		afterAdd(element);
		mDirty.set(true);
		return ret;
	}

	@Override
	public int size() {
		return mList.size();
	}

	@Override
	public List<I> subList(int fromIndex, int toIndex) {
		return mList.subList(fromIndex, toIndex);
	}

	@Override
	public Object[] toArray() {
		return mList.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return mList.toArray(a);
	}

	//

	protected void beforeAdd(I e) {

	}

	protected void afterAdd(I e) {
		mChangeListenerManager.onAdd(e);
	}

	// protected void beforeRemove(I e) {
	// }

	protected void afterRemove(I e) {
		mChangeListenerManager.onRemove(e);
	}

}
