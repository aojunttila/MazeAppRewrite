public class MazeSolverQueue extends MazeSolver{
    MyQueue<Square> q;
    Maze maze;
    public MazeSolverQueue(Maze m){
        super(m);
        maze=m;
        q=new MyQueue<>();
    }

    public Square next(){
        return q.dequeue();
    }

    public void add(Square sq){
        q.enqueue(sq);
    }

    public boolean isEmpty(){
        //printQueue();
        return q.isEmpty();
    }

    public void makeEmpty(){
        q.clear();
    }

    public void printQueue(){
        System.out.println(q);
    }
    //hi
    
}
