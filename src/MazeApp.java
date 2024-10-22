/**
 * The CSCI 151 Amazing Maze Solver GUI application.
 * 
 * Students should not need to modify anything in this file.
 * 
 * @author Benjamin Kuperman (Spring 2012)
 *
 * When you are ready to incorporate this class in the project, 
 * 	select the entire contents of this file and then select the
 *   Toggle Line Comment item from the Edit menu.
 */

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;

public class MazeApp extends JFrame implements ActionListener {
    
    private static final Color SPACECOLOR = Color.WHITE;
    private static final Color WALLCOLOR = Color.BLACK;
    private static final Color STARTCOLOR = Color.BLUE;
    private static final Color EXITCOLOR = Color.RED;
    private static final Color WORKLISTCOLOR = Color.YELLOW;
    private static final Color EXPLOREDCOLOR = Color.GREEN;
    private static final Color PATHCOLOR = Color.CYAN;

    // Initial font size for the display
    private static int imageSize = 16;

    // Initial interval between animation in milliseconds
    private static int timerInterval = 500; 
    
    private static final long serialVersionUID = 6228378229836664288L;

    // Fields for internal data representation
    private Maze maze;
	private MazeSolver solver;
	private boolean mazeLoaded;
    
    // Fields for GUI interface
	private JTextField filename;
	private JTextField timerField;
	private JTextField fontField;
	private JTextArea  pathDisplay;
	private JButton    loadButton;
	private JButton    solveButton;
	private JButton    stepButton;
	private JButton    solverType;
	private JButton    resetButton;
	private JButton    quitButton;
	private Timer      timer;
    
    private JScrollPane     mazepane;
	private JPanel          imageDisplay;
	private BufferedImage   img;
	private JLabel          displayImage;

    /**
     * Constructor -- does most of the work setting up the GUI.
     */
    public MazeApp() {
	// Set up the frame
	super("Amazing Maze Solver");
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    UIManager.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	
	// Field for the maze file name
	filename = new JTextField(10);
	filename.setEditable(false);
	filename.setText("<no maze loaded>");
	
	// Timer and font size fields
	timerField = new JTextField(5);
	fontField  = new JTextField(5);
	
	// Glue text and input together
	JPanel filenamePanel = new JPanel(new BorderLayout());
	filenamePanel.add(new JLabel("File: "), "West");
	filenamePanel.add(filename, "Center");
	
	JPanel fontPanel = new JPanel(new BorderLayout());
	fontPanel.add(new JLabel("Font size:"), "West");
	fontPanel.add(fontField, "Center");
	
	JPanel timerPanel = new JPanel(new BorderLayout());
	timerPanel.add(new JLabel("Timer (ms):"), "West");
	timerPanel.add(timerField, "Center");
	
	JPanel controls = new JPanel(new FlowLayout());
	controls.add(timerPanel);
	controls.add(fontPanel);
	
	// Create the buttons
	loadButton = new JButton("load");
	resetButton = new JButton("reset");
	quitButton = new JButton("quit");
	solverType = new JButton("stack");
	solveButton = new JButton("start");
	stepButton = new JButton("step");

	// places to put all the top menu items
	JPanel buttons1 = new JPanel(new GridLayout(1, 3));  // top row of buttons
	JPanel buttons2 = new JPanel(new GridLayout(1, 3));  // bottom row of buttons
	JPanel buttonBar = new JPanel(new GridLayout(2, 2)); // combined layout of buttons
							     // and text
	
    imageDisplay = new JPanel(new FlowLayout(FlowLayout.LEADING,0,0));
    imageDisplay.setPreferredSize(new Dimension(300, 300));
    imageDisplay.setBackground(Color.WHITE);
    addImage();

	// load up the buttons in L to R order
	buttons1.add(loadButton);
	buttons1.add(resetButton);
	buttons1.add(quitButton);
	buttons2.add(solverType);
	buttons2.add(solveButton);
	buttons2.add(stepButton);
	
	// Glue the components together row by row
	buttonBar.add(filenamePanel); // top left
	buttonBar.add(buttons1);      // top right
	buttonBar.add(controls);      // bottom left
	buttonBar.add(buttons2);      // bottom right
	// add padding from edges
	buttonBar.setBorder(BorderFactory.createEmptyBorder(10,10,0,10));
	
	// Timer for the animations
	timer = new Timer(timerInterval, this);
	
	
	// Set up the bottom area to show the maze and path
	pathDisplay = new JTextArea(4, 30);
	pathDisplay.setEditable(false);
	JPanel pane = new JPanel(new BorderLayout());
	pane.setBorder(BorderFactory.createEmptyBorder(
                10, //top
                10, //left
                10, //bottom
                10) //right
                );
    mazepane = new JScrollPane(imageDisplay);
    pane.add(mazepane, "Center"); // let's maze be biggest
	pane.add(new JScrollPane(pathDisplay), "South");
	
	// Create the overall layout (buttons on top, maze info below)
	JPanel panel = new JPanel(new BorderLayout());
	panel.add(buttonBar,"North");
	panel.add(pane);
	
	// add to the frame
	this.getContentPane().add(panel);
	
	// shrink wrap and display
	this.pack();
	this.setLocationRelativeTo(null);	// center
	this.setVisible(true);

	// Actionlisteners
	loadButton.addActionListener(this);
	filename.addActionListener(this);
	solveButton.addActionListener(this);
	solverType.addActionListener(this);
	stepButton.addActionListener(this);
	resetButton.addActionListener(this);
	quitButton.addActionListener(this);
	
	timerField.addActionListener(this);
	fontField.addActionListener(this);
	
	// Set up the class variables
	doTimer();
	doFontSize();
	mazeLoaded = false;
	this.maze = new Maze();
    updateMaze();
	//makeNewSolver();
    }
    
    /*
     * Collection of handlers to deal with GUI events.
     * 
     * (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
	if ( (e.getSource() == loadButton) || (e.getSource() == filename) ){
	    loadFile();
        updateMaze();
	}
	if (e.getSource() == solveButton) {
	    if (mazeLoaded) {
		makeNewSolver();
		solveButton();
	    }
	}
	if (e.getSource() == resetButton) {
	    reset();
	}
	if (e.getSource() == solverType) {
	    toggleSolverType();
	    makeNewSolver();
	}
	if (e.getSource() == quitButton) {
	    doQuit();
	}
	if (e.getSource() == timerField) {
	    doTimer();
	}
	if (e.getSource() == fontField) {
	    doFontSize();
	}
	if (e.getSource() == stepButton) {
	    if (mazeLoaded)
		doStep();
	}
	if (e.getSource() == timer) {
	    // animate a step
	    if (mazeLoaded) {
		doStep();
	    }
	}
	
    }

    /**
     * Allow the user to change the timer interval. 
     */
    private void doTimer() {
	int newValue = -1;
	try {
	    newValue = Integer.parseInt(timerField.getText());
	} catch (NumberFormatException nfe) {
	    // do nothing
	}
	if (newValue>0)
	    timerInterval = newValue;
	timerField.setText(Integer.toString(timerInterval));
	timer.setDelay(timerInterval);
    }


    /**
     * Allow the user to change the font size. 
     */
    private void doFontSize() {
	int newValue = -1;
	try {
	    newValue = Integer.parseInt(fontField.getText());
	} catch (NumberFormatException nfe) {
	    // do nothing
	}
	if (newValue>0)
	    imageSize = newValue;
	fontField.setText(Integer.toString(imageSize));
	pathDisplay.setFont(new Font("Courier",Font.BOLD, 16));
    updateMaze();
    }

    /**
     * Allow the user to quit via button.
     */
    private void doQuit() {
    	System.exit(0);
    }

    /**
     * Set things back to the ready state.  Called by the "reset" button
     * as well as many other methods.
     */
    private void reset() {
	maze.reset();
	makeNewSolver();
	updateMaze();
    }

    /**
     * Performs a single step of the MazeSolver.  Called when the
     * user clicks on "Step" as well as by the interval timer.
     */
    private void doStep() {
	if (mazeLoaded && !solver.isSolved()) {
	    solver.step();
	    if (solver.isSolved()) {
		solveButton();
		timer.stop();
	    }
	}
	updateMaze();
    }

    /**
     * Handles the user clicking on the solver type button.
     */
    private void toggleSolverType() {
	String oldType = solverType.getText();
	if (oldType.equalsIgnoreCase("queue")) {
	    solverType.setText("stack");
	} else if (oldType.equalsIgnoreCase("stack")) {
	    solverType.setText("queue");
	} else
	    throw new UnsupportedOperationException("Don't know how to change from a: " + oldType);
	reset();
    }
    
    /**
     * Builds a new MazeSolver of the type displayed on the button.
     */
    private void makeNewSolver() {
	String oldType = solverType.getText();
	if (oldType.equalsIgnoreCase("queue")) {
	    solver = new MazeSolverQueue(this.maze);
	} else if (oldType.equalsIgnoreCase("stack")) {
	    solver = new MazeSolverStack(this.maze);
	} else
	    throw new UnsupportedOperationException("Don't know how to solve using a: " + oldType);
    }

    /**
     * Handles the starting/stopping of the timer.
     */
    private void solveButton() {
	String label = solveButton.getText();
	if (solver.isSolved()) {
	    solveButton.setBackground(Color.white);
	    solveButton.setText("start");
	    return;
	}
	if (label.equalsIgnoreCase("start")) {
	    if ( mazeLoaded ) {
		solveButton.setText("stop");
		solveButton.setBackground(Color.red);
		timer.start();
	    }
	} else if (label.equalsIgnoreCase("stop")) {
	    solveButton.setText("start");
	    solveButton.setBackground(Color.green);
	    timer.stop();
	}
    }

    /**
     * Load a maze file into the solver.
     */
    private void loadFile() {

	// Let the user pick from a filtered list of files
	JFileChooser chooser = new JFileChooser(new File("."));
	chooser.setFileFilter(new FileFilter() {
	    String description = "Maze files";

	    @Override
	    public boolean accept(File f) {
		if (f.isDirectory()) {
		        return true;
		    }
		if (f.getName().startsWith("maze-"))
		    return true;
		return false;
	    }

	    @Override
	    public String getDescription() {
		return this.description;
	    }
	    
	});
	File newFile = null;
	String newFileName = null;
        int returnVal = chooser.showOpenDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            newFile = chooser.getSelectedFile();
            newFileName = newFile.getName();
        } else {
            // if they didn't pick a file, cancel the rest of the update
            return;
        }
        
        // Try to load it
        if (! maze.loadMaze(newFile.getPath()) ) {
            JOptionPane.showMessageDialog(this, "Cannot load file: "+newFileName);
        } else {
            // update name without path
            filename.setText(newFileName);
            
            // set things up as ready to go
            solveButton.setText("start");
            solveButton.setBackground(Color.green);
            mazeLoaded=true;
            timer.stop();
            reset();
        }
    }

    /**
     * Update both the maze and the path text areas.
     */
    private void updateMaze() {
	if (mazeLoaded) {  // leave blank until first maze is loaded
        
        //update the maze
        refreshImage();
	    // update the path
	    if (solver.isSolved()) {
		pathDisplay.setText(solver.getPath());
        refreshImage();
	    } else {
		pathDisplay.setText("Maze is unsolved");
	    }
	}
    }

    int width;
    int height;
    String[][] mazeScan;
    String[][] prevMazeScan;
    
    /**
     * Creates the image that represents the maze
     */
	private void addImage(){
		img = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
		displayImage = new JLabel(new ImageIcon(img.getScaledInstance(100 * imageSize,100 * imageSize, Image.SCALE_SMOOTH)));
		imageDisplay.add(displayImage);
    }

    /**
     * Scans the toString output of Maze into a 2d array
     * @return The 2d array
     */
    private String[][] scanMaze(){
        String[] tempMaze = maze.toString().split("\n");
        width = tempMaze[0].split(" ").length;
        height = tempMaze.length;
        String[][] scannedMaze = new String[width][height];
        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for(int i = 0; i < tempMaze.length; i++){
            String[] tempMaze2 = tempMaze[i].split(" ");
            for(int b = 0; b < tempMaze2.length; b++){
                scannedMaze[b][i] = tempMaze2[b];
            }
        }
        return scannedMaze;
    }

    /**
     * Converts the character returned by Square.toString()
     * to a predefined color.
     * @param s input string
     * @return color
     */
    private Color stringToColor(String s){
        Color clr = Color.WHITE;
        if(s.equals("_")){
            clr = SPACECOLOR;
        }else if(s.equals("#")){
            clr = WALLCOLOR;
        }else if(s.equals("S")){
            clr = STARTCOLOR;
        }else if(s.equals("E")){
            clr = EXITCOLOR;
        }else if(s.equals("o")){
            clr = WORKLISTCOLOR;
        }else if(s.equals(".")){
            clr = EXPLOREDCOLOR;
        }else if(s.equals("x")){
            clr = PATHCOLOR;
        }else{
            clr = Color.BLACK;
        }
        return clr;
    }

    /**
     * Refreshes the maze display image
     */
    private void refreshImage(){
        mazeScan = scanMaze();
        if(prevMazeScan == null || prevMazeScan.length != mazeScan.length){
            prevMazeScan = scanMaze();
        }
		for(int y=0;y<height;y++){
			for(int x=0;x<width;x++){
				if(mazeScan[x][y]!=prevMazeScan[x][y]){
					img.setRGB(x,y,stringToColor(mazeScan[x][y]).getRGB());
				}
			}
		}
		displayImage.setIcon(new ImageIcon(img.getScaledInstance(width*imageSize,height*imageSize, Image.SCALE_SMOOTH)));
        prevMazeScan = scanMaze();
	}

    /**
     * @param args
     */
    public static void main(String[] args) {
        new MazeApp();
    }

}

