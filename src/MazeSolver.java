import java.util.ArrayList;
import java.util.Stack;

abstract class MazeSolver {
    Maze m;
    boolean solved=false;
    Square endSquare;
    boolean started=false;
    ArrayList<Square> neighbors2 = new ArrayList<>();
    abstract void makeEmpty();

    abstract boolean isEmpty();

    abstract void add(Square sq);

    abstract Square next();

    MazeSolver(Maze maze){
        m=maze;
    };

    boolean isSolved(){
        //return true;
        return solved;
    };

    String getPath(){
        Stack<Square> stac = new Stack<>();
        boolean startreached = false;
        Square currentSquare = endSquare;
        int i=0;
        if(solved){
            while(startreached==false){
                i++;
                stac.push(currentSquare);
                currentSquare = currentSquare.previous;
                if(currentSquare.isStart==true){
                    System.out.println("end reached in "+i+" steps");
                    startreached=true;
                }
                if(i>10000){return "No path exists";}
            }
            stac.push(currentSquare);
            String coords="";
            while(!stac.isEmpty()){
                Square temp=stac.pop();
                coords+=(" ["+temp.getCol()+","+temp.getRow()+"]");
                temp.onPath=true;
            }
            return coords;
        }else{return "Maze not solved yet";}

    };

    Square step(){
        if(started==false){
            Square t1=m.getStart();
            add(t1);
            t1.isStart=true;
            started=true;
        }
        //System.out.println("called next");
        if(isEmpty()){
            //System.out.println("worklist empty");
            return null;
        }else{
            Square sq = next();

            
            if(m.getFinish()==sq){
                System.out.println("finished");
                solved=true;
                endSquare=sq;
                return null;
            }
            for(int i=0;i<neighbors2.size();i++){
                //neighbors2.get(i).isActive=false;
            }
            neighbors2.clear();
            ArrayList<Square> neighbors = m.getNeighbors(sq);
            sq.explored=true;
            sq.isActive=false;
            for(int i=0;i<neighbors.size();i++){
                neighbors.get(i).isActive=true;
                if(neighbors.get(i).getType()!=Square.WALL){
                    if(neighbors.get(i).previous==null&&neighbors.get(i).explored==false){
                        neighbors.get(i).previous=sq;
                        add(neighbors.get(i));
                        //neighbors.get(i).isActive=true;
                        neighbors2.add(neighbors.get(i));
                    }
                }
            }
            //System.out.println("explored "+sq.getCol()+","+sq.getRow());
            //sq.explored=true;
            //sq.isActive=false;
            return sq;
        }

    };

    void solve(){
        System.out.println("called solve");
    };
}
