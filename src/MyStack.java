import java.util.ArrayList; 
import java.util.NoSuchElementException;

public class MyStack<T> implements StackADT<T>{

    private ArrayList<T> a; 

    public MyStack(){
        a = new ArrayList<T>();;
    }

    public void push(T e){
        a.add(0,e);
        System.out.println(a);
    }

    public T pop(){
        if(a.isEmpty()){
            throw new NoSuchElementException();
        }
        
        return a.remove(0);
    }

    public T top(){
        if(a.isEmpty()){
            throw new NoSuchElementException();
        }
        return a.get(0);
    }

    public int size(){
        return a.size();
    }
    
    public boolean isEmpty(){
        return a.size() == 0;
    }

    /**
     * Clear out the data structure
     */
    public void clear(){
        a.clear();
    }
}
