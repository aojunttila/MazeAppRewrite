import java.awt.Color;

public class Square
{
    public static final int SPACE = 0;
    public static final int WALL = 1;
    public static final int START = 2;
    public static final int EXIT = 3;

    public static final Color SPACECOLOR = Color.WHITE;
    public static final Color WALLCOLOR = Color.BLACK;
    public static final Color STARTCOLOR = Color.BLUE;
    public static final Color EXITCOLOR = Color.RED;
    public static final Color EXPLOREDCOLOR = Color.GREEN;
    public static final Color WORKLISTCOLOR = Color.YELLOW;
    public static final Color PATHCOLOR = Color.CYAN;
    
    private int type;
    private int row;
    private int col;

    public boolean isActive=false;
    public boolean onPath=false;
    public boolean explored=false;
    public boolean isStart=false;
    public Square previous=null;

    /**
     * Constructor for objects of class Square
     * 
     * @param initialRow    the row for this Square in the Maze
     * @param initialCol    the column for this Square in the Maze
     * @param initialType   the type (space, wall, start, exit) for this
     *                          Square in the Maze
     */
    public Square(int initialRow, int initialCol, int initialType)
    {
        this.row = initialRow;
        this.col = initialCol;
        this.type = initialType;
    }

    /**
     * Returns this square's type
     *
     * @return    this square's type
     */
    public int getType()
    {
        return this.type;
    }

    /**
     * Returns this square's row
     *
     * @return    this square's row
     */
    public int getRow()
    {
        return this.row;
    }

    /**
     * Returns this square's column
     *
     * @return    this square's column
     */
    public int getCol()
    {
        return this.col;
    }

    @Override
    public String toString()
    {
        String str = null;

        if(this.getType() == Square.SPACE) {
            str = "_";
        }

        if(explored){
            str = ".";
        }
        else if(isActive){
            str = "o";
        }
        else if(onPath){
            str = "x";
        }

        if(this.getType() == Square.START) {
            str = "S";
        }
        else if(this.getType() == Square.EXIT) {
            str = "E";
        }

        if(this.getType() == Square.WALL) {
            str = "#";
        }

        return str;
    }

    //REMOVE
    public void reset(){}

    @Override
    public boolean equals(Object other)
    {
        // self check
        if(this == other)
        {
            return true;
        }

        // null check
        if(other == null)
        {
            return false;
        }

        // type check
        if(this.getClass() != other.getClass())
        {
            return false;
        }

        Square otherSq = (Square)other;

        // field comparision
        return (this.row == otherSq.row) &&
                (this.col == otherSq.col) &&
                (this.type == otherSq.type);
    }
}