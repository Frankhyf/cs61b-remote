public class LinkedListDeque<T> {


    public class Deque {
        public Deque prev;
        public Deque next;
        public T item;

        public Deque(T item,Deque p,Deque n){
            this.item = item;
            this.prev = p;
            this.next = n;
        }
    }

    private Deque sentinel;
    private int size = 0;

    public LinkedListDeque(){
        sentinel = new Deque(null,null,null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }
    public LinkedListDeque(LinkedListDeque<T> other){
        LinkedListDeque deepcopy = new LinkedListDeque();//create a new empty linkedlistdeque
        Deque ptr = other.sentinel.next;
        while (ptr !=other.sentinel){
             deepcopy.addLast(ptr.item);
             ptr = ptr.next;
        }
    }
    public void addFirst(T item){
        size+=1;
        sentinel.next = new Deque(item,sentinel,sentinel.next);
        sentinel.next.next.prev = sentinel.next;
    }
    public void addLast(T item){
        size+=1;
        sentinel.prev= new Deque(item,sentinel.prev,sentinel);
        sentinel.prev.prev.next = sentinel.prev;
    }
    public boolean isEmpty(){
        if(sentinel.next == sentinel){
            return true;
        }
        return false;
    }

    public int size(){
        return size;
    } //Returns the number of items in the deque.

    public void printDeque(){
        if (this.isEmpty() == true) {
            System.out.println();
            System.out.println();
        }
        else{
            Deque ptr = sentinel.next;
            while (ptr!=sentinel){
                System.out.print(ptr.item+" ");
                ptr=ptr.next;
            }
            System.out.println();
        }
    }//Prints the items in the deque from first to last, separated by a space. Once all the items have been printed, print out a new line.
    public T removeFirst(){
        if(size ==0){
            return null;
        }
        size -=1;
        T item = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        return item;
    }//Removes and returns the item at the front of the deque. If no such item exists, returns null.
    public T removeLast(){
        if(size ==0){
            return null;
        }
        size -=1;
        T item = sentinel.prev.item;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        return item;
    }//Removes and returns the item at the back of the deque. If no such item exists, returns null.
    public T get(int index){
         if(index+1>size || index <0){
             return null;
         }

         else{
             Deque ptr = sentinel;
             while(index !=0){
                 index--;
                 ptr = ptr.next;
             }
             return ptr.next.item;
         }
    }//Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth. If no such item exists, returns null. Must not alter the deque!
    private T getRecursiveHelper(int index, Deque node) {
        if (index == 0) return node.item;
        return getRecursiveHelper(index - 1, node.next);
    }

    public T getRecursive(int index) {
        if(index >= size || index < 0) return null;
        return getRecursiveHelper(index, sentinel.next);
    }

}

