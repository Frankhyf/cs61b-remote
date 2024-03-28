public class LinkedListDeque<T> {


    private class Deque {
        private Deque prev;
        private Deque next;
        private T item;

        public Deque(T item, Deque p, Deque n) {
            this.item = item;
            this.prev = p;
            this.next = n;
        }
    }

    private Deque sentinel;
    private int size ;


    public LinkedListDeque(LinkedListDeque<T> other) {
        this.sentinel = new Deque(null, null, null);
        this.sentinel.next = this.sentinel;
        this.sentinel.prev = this.sentinel;
        this.size = 0;

        for (Deque ptr = other.sentinel.next; ptr != other.sentinel; ptr = ptr.next) {
            this.addLast(ptr.item);
        }
    }

    public void addFirst(T item) {
        size += 1;
        sentinel.next = new Deque(item, sentinel, sentinel.next);
        sentinel.next.next.prev = sentinel.next;
    }
    public void addLast(T item) {
        size += 1;
        sentinel.prev = new Deque(item, sentinel.prev, sentinel);
        sentinel.prev.prev.next = sentinel.prev;
    }
    public boolean isEmpty() {
        if (sentinel.next == sentinel) {
            return true;
        }
        return false;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        if (this.isEmpty()) {
            System.out.println();
            System.out.println();
        } else {
            Deque ptr = sentinel.next;
            while (ptr != sentinel) {
                System.out.print(ptr.item + " ");
                ptr = ptr.next;
            }
            System.out.println();
        }
    }
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        size -= 1;
        T item = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        return item;
    }
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        size -= 1;
        T item = sentinel.prev.item;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        return item;
    }
    public T get(int index) {
        if (index + 1 > size || index < 0) {
            return null;
        } else {
            Deque ptr = sentinel;
            while (index != 0) {
                index--; //--should have no whitespace before
                ptr = ptr.next;
            }
            return ptr.next.item;
        }
    }
    private T getRecursiveHelper(int index, Deque node) {
        if (index == 0) {
            return node.item;
        }
        return getRecursiveHelper(index - 1, node.next);
    }

    public T getRecursive(int index) {
        if (index >= size || index < 0) {
            return null;
        }
        return getRecursiveHelper(index, sentinel.next);
    }
}

