public class MazeSolverStack extends MazeSolver{
    MyStack<Square> s=new MyStack<>();
    Maze maze;
    public MazeSolverStack(Maze m){
        super(m);
        maze=m;
    }

    public Square next(){
        return s.pop();
    }

    public void add(Square sq){
        System.out.println("called add");
        s.push(sq);
    }

    public boolean isEmpty(){
        printQueue();
        return s.isEmpty();
    }

    public void makeEmpty(){
        s.clear();
    }

    public void printQueue(){
        System.out.println(s);
    }
    
    
}
