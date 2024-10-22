import java.util.ArrayList;
import java.util.NoSuchElementException;

public class MyQueue<T> implements QueueADT<T>{
    ArrayList<T> a = new ArrayList<T>();

    public void enqueue(T item){
        a.add(item);
    }

    public T dequeue(){
        if(isEmpty()){throw new NoSuchElementException();}
        T item = a.get(0);
        a.remove(0);
        return item;
    }

    public T front(){
        if(isEmpty()){throw new NoSuchElementException();}
        T item = a.get(0);
        return item;
    }

    public int size(){
        return a.size();
    }

    public void clear(){
        a.clear();
    }

    public boolean isEmpty(){
        return a.isEmpty();
    }
    
}
