package com.luzi82.common;

import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class WeakList<E> implements List<E> {

	private List<WeakReference<E>> mList;

	public WeakList() {
		this(new LinkedList<WeakReference<E>>());
	}

	private WeakList(List<WeakReference<E>> list) {
		mList = list;
	}

	@Override
	public boolean add(E e) {
		return mList.add(wr(e));
	}

	@Override
	public void add(int index, E element) {
		mList.add(index, wr(element));
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		for (E e : c) {
			mList.add(wr(e));
		}
		return true;
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		LinkedList<WeakReference<E>> wrList = new LinkedList<WeakReference<E>>();
		for (E e : c) {
			wrList.add(wr(e));
		}
		return mList.addAll(wrList);
	}

	@Override
	public void clear() {
		mList.clear();
	}

	@Override
	public boolean contains(Object o) {
		for (WeakReference<E> w : mList) {
			if (w.get() == o)
				return true;
		}
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		for (Object o : c) {
			if (!contains(o))
				return false;
		}
		return true;
	}

	@Override
	public E get(int index) {
		return mList.get(index).get();
	}

	@Override
	public int indexOf(Object o) {
		int ret = 0;
		for (WeakReference<E> w : mList) {
			if (w.get() == o)
				return ret;
			++ret;
		}
		return -1;
	}

	@Override
	public boolean isEmpty() {
		return mList.isEmpty();
	}

	@Override
	public Iterator<E> iterator() {
		return listIterator();
	}

	@Override
	public int lastIndexOf(Object o) {
		int ret = -1;
		int i = 0;
		for (WeakReference<E> w : mList) {
			if (w.get() == o)
				ret = i;
			++i;
		}
		return ret;
	}

	@Override
	public ListIterator<E> listIterator() {
		return listIterator(0);
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		final ListIterator<WeakReference<E>> w = mList.listIterator(index);
		ListIterator<E> ret = new ListIterator<E>() {
			@Override
			public void add(E e) {
				w.add(wr(e));
			}

			@Override
			public boolean hasNext() {
				return w.hasNext();
			}

			@Override
			public boolean hasPrevious() {
				return w.hasPrevious();
			}

			@Override
			public E next() {
				return w.next().get();
			}

			@Override
			public int nextIndex() {
				return w.nextIndex();
			}

			@Override
			public E previous() {
				return w.previous().get();
			}

			@Override
			public int previousIndex() {
				return w.previousIndex();
			}

			@Override
			public void remove() {
				w.remove();
			}

			@Override
			public void set(E e) {
				w.set(wr(e));
			}
		};
		return ret;
	}

	@Override
	public boolean remove(Object o) {
		Iterator<WeakReference<E>> itr = mList.iterator();
		while (itr.hasNext()) {
			WeakReference<E> w = itr.next();
			if (w.get() != o)
				continue;
			itr.remove();
			return true;
		}
		return false;
	}

	@Override
	public E remove(int index) {
		return mList.remove(index).get();
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		boolean ret = false;
		Iterator<WeakReference<E>> itr = mList.iterator();
		while (itr.hasNext()) {
			WeakReference<E> w = itr.next();
			if (!c.contains(w.get()))
				continue;
			itr.remove();
			ret = true;
		}
		return ret;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		boolean ret = false;
		Iterator<WeakReference<E>> itr = mList.iterator();
		while (itr.hasNext()) {
			WeakReference<E> w = itr.next();
			if (c.contains(w.get()))
				continue;
			itr.remove();
			ret = true;
		}
		return ret;
	}

	@Override
	public E set(int index, E element) {
		return mList.set(index, wr(element)).get();
	}

	@Override
	public int size() {
		return mList.size();
	}

	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		return new WeakList<E>(mList.subList(fromIndex, toIndex));
	}

	@Override
	public Object[] toArray() {
		LinkedList<E> list = new LinkedList<E>();
		for (WeakReference<E> w : mList) {
			list.add(w.get());
		}
		return list.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		LinkedList<E> list = new LinkedList<E>();
		for (WeakReference<E> w : mList) {
			list.add(w.get());
		}
		return list.toArray(a);
	}

	public void clearNull() {
		Iterator<WeakReference<E>> itr = mList.iterator();
		while (itr.hasNext()) {
			WeakReference<E> w = itr.next();
			if (w.get() != null)
				continue;
			itr.remove();
		}
	}

	protected WeakReference<E> wr(E element) {
		return new WeakReference<E>(element);
	}

}
