import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner; 

/**
 * Stores the logical layout of a maze.
 *
 */
public class Maze
{
    private Square[][] maze;

    /**
     * Constructor for objects of class Maze
     */
    public Maze()
    {
    }

    /**
     * Loads the maze from the specified file
     *
     * @param  fname  the name of the file containing the maze to be loaded
     * @return    true if the maze was successfully loaded; otherwise, false
     */
    public boolean loadMaze( String fname )
    {
        int numRows = 0;
        int numCols = 0;
        File mazeFile = new File( fname );
        Scanner in = null;

        try
        {
            in = new Scanner( mazeFile );

            // read the number of rows
            numRows = in.nextInt();

            // read the number of columns
            numCols = in.nextInt();

            this.maze = new Square[numRows][numCols];

            // read the maze
            for( int row = 0; row < numRows; row++ )
            {
                for( int col = 0; col < numCols; col++ )
                {
                    int type = in.nextInt();
                    this.maze[row][col] = new Square(row, col, type);
                }
            }
        }
        catch( IOException exception )
        {
            System.out.println( "The specified file: " + fname + " was not found." );
            return false;
        }
        catch( NoSuchElementException exception )
        {
            System.out.println( "The specified file: " + fname + " is not formatted correctly." );
            in.close();
            return false;
        }

        in.close();
        return true;
    }

    /**
     * Returns a list of the neighbors of the specified square
     *
     * @param  sq  the square whose neighbors to return
     * @return    a list of the neighbors of the specified square
     */
    public ArrayList<Square> getNeighbors( Square sq )
    {
        ArrayList<Square> neighbors = new ArrayList<Square>();
        int row = sq.getRow();
        int col = sq.getCol();

        if( row > 0 )
        {
            neighbors.add( this.maze[row-1][col] );
        }

        if( col < this.maze[0].length - 1 )
        {
            neighbors.add( this.maze[row][col+1] );
        }

        if( row < this.maze.length - 1 )
        {
            neighbors.add( this.maze[row+1][col] );
        }

        if( col > 0 )
        {
            neighbors.add( this.maze[row][col-1] );
        }

        return neighbors;
    }

    /**
     * Returns the start square
     *
     * @return    the start square
     */
    public Square getStart()
    {
        for( int row = 0; row < this.maze.length; row++ )
        {
            for( int col = 0; col < this.maze[row].length; col++ )
            {
                if( this.maze[row][col].getType() == Square.START)
                {
                    return this.maze[row][col];
                }
            }
        }

        return null;
    }

    /**
     * Returns the finish square
     *
     * @return    the finish square
     */
    public Square getFinish()
    {
        for( int row = 0; row < this.maze.length; row++ )
        {
            for( int col = 0; col < this.maze[row].length; col++ )
            {
                if( this.maze[row][col].getType() == Square.EXIT)
                {
                    return this.maze[row][col];
                }
            }
        }

        return null;
    }

    /**
     * Returns the maze back to the initial state after loading.
     *
     */
    public void reset()
    {
        for( int row = 0; row < this.maze.length; row++ )
        {
            for( int col = 0; col < this.maze[row].length; col++ )
            {
                this.maze[row][col].reset();
            }
        }
    }

    /**
     * toString
     *
     * @return    string representation of the maze
     */
    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        for( int row = 0; row < this.maze.length; row++ )
        {
            for( int col = 0; col < this.maze[row].length; col++ )
            {
                sb.append( this.maze[row][col].toString() + " " );
            }
            
            sb.append( "\n" );
        }

        return new String( sb );
    }

}