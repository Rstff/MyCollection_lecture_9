import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyCollection<E> implements Collection<E> {

    private int size;

    @SuppressWarnings("checkstyle:MagicNumber")
    private Object[] elementData = new Object[10];

    @SuppressWarnings("checkstyle:MagicNumber")
    @Override
    public final boolean add(final E e) {
        if (size == elementData.length) {
            elementData = Arrays.copyOf(elementData, (int) (size * 1.5f));
        }
        elementData[size++] = e;
        return true;
    }

    @Override
    public final int size() {
        return this.size;
    }

    @Override
    public final boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public final Iterator<E> iterator() {
        return new MyIterator<>();
    }

    @Override
    public final boolean contains(final Object o) {
        for (int i = 0; i < size; i++) {
            if (elementData[i] == null && o == null) {
                return true;
            }
            if (elementData[i] != null && elementData[i].equals(o)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public final Object[] toArray() {
        for (int i = 0; i < size; i++) {
            elementData[i] = (Object) elementData[i];
        }
        return Arrays.copyOf(elementData, size);
    }

    @Override
    @SuppressWarnings("unchecked")
    public final <T> T[] toArray(final T[] a) {
        if (a.length < size) {
            T[] array = Arrays.copyOf(a, size);
            for (int i = 0; i < size; i++) {
                array[i] = (T) elementData[i];
            }
            return array;
        } else {
            for (int i = 0; i < size; i++) {
                a[i] = (T) elementData[i];
            }
        }
        return a;
    }

    @Override
    public final boolean remove(final Object o) {
        int i;
        boolean findElement = false;
        for (i = 0; i < size; i++) {
            if (elementData[i] == null && o == null) {
                findElement = true;
                break;
            }
            if (elementData[i] != null && elementData[i].equals(o)) {
                findElement = true;
                break;
            }
        }
        if (findElement) {
            if (size - 1 - i >= 0) {
                System.arraycopy(elementData, i + 1,
                        elementData, i, size - 1 - i);
            }
            size--;
        }
        return findElement;
    }

    @Override
    public final boolean containsAll(final Collection<?> c) {
        for (Object checkElement : c) {
            if (!contains(checkElement)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public final boolean addAll(final Collection<? extends E> c) {
        for (E i : c) {
            add(i);
        }
        return true;
    }

    @Override
    public final boolean removeAll(final Collection<?> c) {
        boolean result = false;
        for (Object checkElement : c) {
            while (contains(checkElement)) {
                remove(checkElement);
                result = true;
            }
        }
        return result;
    }

    @Override
    public final boolean retainAll(final Collection<?> c) {
        boolean isChange = false;
        for (int i = 0; i < size; i++) {
            while (!c.contains(elementData[i]) && contains(elementData[i])) {
                remove(elementData[i]);
                isChange = true;
            }
        }
        return isChange;
    }

    @Override
    public final void clear() {
        Arrays.fill(elementData, null);
        size = 0;
    }

    private class MyIterator<T> implements Iterator<T> {

        private int cursor = 0;
        private boolean canRemove = false;

        @Override
        public final boolean hasNext() {
            return cursor < size;
        }

        @Override
        @SuppressWarnings("unchecked")
        public T next() {
            if (cursor >= size) {
                throw new NoSuchElementException();
            }
            canRemove = true;
            return (T) elementData[cursor++];
        }

        @Override
        public void remove() {
            if (!canRemove) {
                throw new java.lang.IllegalStateException("Not called method next()");
            } else {
                if (size - 1 - (cursor - 1) >= 0) {
                    System.arraycopy(elementData, cursor - 1 + 1,
                            elementData, cursor - 1, size - 1 - (cursor - 1));
                }
                cursor--;
                size--;
                canRemove = false;
            }
        }
    }
}
