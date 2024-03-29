public class ArrayDeque<T> {
    private T[] items;
    private int size;
    private int front;

    private int rear;
    public ArrayDeque() {
        items = (T []) new Object[8];
        size = 0;
        front = rear = 0;
    }

    private void expandlength(int length) {
        T[] a = (T []) new Object[length];
        if (front > rear) {
            System.arraycopy(items, front, a, 0, size - front);
            System.arraycopy(items, 0, a, size - front, rear + 1);
            front = 0;
            rear = size - 1;
            items = a;
        } else {
            System.arraycopy(items, front, a, 0, size);
            front = 0;
            rear = size -1;
            items = a;
        }
    }




    public void addFirst(T item) {
        if (isEmpty()) {
            items[front] = item;
        } else {
            if (size == items.length) {
                this.expandlength(size * 2);
            }
            if (front == 0) {
                front = items.length - 1;
                items[front] = item;
            } else {
                front -= 1;
                items[front] = item;
            }
        }
        size += 1;
    }
    public void addLast(T item) {
        if (isEmpty()) {
            items[front] = item;
        } else {
            if (size == items.length) {
                this.expandlength(size * 2);
            }
            if (rear == items.length - 1) {
                rear = 0;
                items[rear] = item;
            } else {
                rear += 1;
                items[rear] = item;
            }
        }
        size += 1;
    }

    public boolean isEmpty() {
        if (size == 0) {
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
        }
        for (int i = front; i <= items.length+rear; i++) {
            System.out.print(items[i % items.length] + " ");
        }
        System.out.println();
    }

    public T removeFirst() {
        if (this.isEmpty()) {
            return null;
        }
        if (items.length / size >= 4 && items.length >= 16) {
            this.expandlength(items.length / 2);
        }
        T res = items[front];
        if (size == 1) {

        } else if (front == items.length - 1) {
            front = 0;
        } else {
            front += 1;
        }
        size -= 1;
        return res;
    }

    public T removeLast() {
        if (this.isEmpty()) {
            return null;
        }
        if (items.length / size >= 4 && items.length >= 16){
            this.expandlength(items.length / 2);
        }
        T res = items[rear];
        if (size == 1){

        } else if (rear == 0){
            rear = items.length-1;
        } else {
            rear -= 1;
        }
        size -= 1;
        return res;
    }
    public T get(int index) {
        if (index >= size || index < 0){
            return null;
        } else {
            return items[(front+index) % items.length];
        }
    }
}
