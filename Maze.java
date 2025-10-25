package Maze;

/**
 * Class that solves maze problems with backtracking.
 * @author Koffman and Wolfgang
 **/

//IMPORT JAVA.UTIL

	import java.util.Stack;
	import java.util.ArrayList;
	import java.util.List;

//CLASS DECLARATION
	
	public class Maze implements GridColors {


//PARAMETER DECLARATION
		
	/** The maze */
    
	private TwoDimGrid maze;

 //CONSTRUCTOR METHOD: MAZE
   
    public Maze(TwoDimGrid m) {
        maze = m;
    }

 //WRAPPER METHOD: findMazePath  
   
    /** Wrapper method. */
    
    public boolean findMazePath() {
        return findMazePath(0, 0); // (0, 0) is the start point.
    }

 //RECURSIVE ALGORITH: findMazePath  
    
    /**
     * Attempts to find a path through point (x, y).
     * @pre Possible path cells are in BACKGROUND color;
     *      barrier cells are in ABNORMAL color.
     * @post If a path is found, all cells on it are set to the
     *       PATH color; all cells that were visited but are
     *       not on the path are in the TEMPORARY color.
     * @param x The x-coordinate of current point
     * @param y The y-coordinate of current point
     * @return If a path through (x, y) is found, true;
     *         otherwise, false
     */
    
    public boolean findMazePath(int x, int y) {
        
    	// COMPLETE HERE FOR PROBLEM 1
    	
    	// EXPLANATION OF METHOD: USED SOLELY TO DETERMINE IF MAZE CAN BE SOLVED (TRUE) OR NOT (FALSE)
    	
    		//Step 1 
    		//Check if current cell is outside the grid and if so, return false.
    	
		    	if (x<0 || y < 0 || x>=maze.getNCols() || y >= maze.getNRows()) {
		    		return false;
		    	}
    	
		    	
		    //Step 2 
		    //Check if current cell does not have NON_BACKGROUND color, and if it does not, return false.
		    	
		    	else if (!maze.getColor(x, y).equals(NON_BACKGROUND)) {
		    		return false;
		    	}
		    	
		    
		    //Step 3
		    //Check if current cell is exit cell. If it is, paint the cell to the PATH color and return true.
		    	
		    	else if (x == maze.getNCols()-1 && y == maze.getNRows()-1) {
		    		
		    		//Paint the cell the PATH color
		    		
		    		maze.recolor(x, y, PATH);
		    		
		    		// Return true
		    		
		    		return true;
		    		
		    	}
		    		
		    //Step 4
		    //If the current cell is not affected by above steps, then it is assumed to be part of the path. Therefore:
		    		
		    		//STEP 4a: paint the cell to PATH color
		    		//STEP 4b: explore the four neighboring cells to see if they are part of the path (recursion)
		    		//Step 4c: if no neighboring cell is part of the path, mark it as a dead end by painting it to TEMPORARY color
		    		
		    	else {
		    		
		    		//STEP 4a
		    		
		    		maze.recolor(x, y, PATH);
		    		
		    		//STEP 4b
		    		//Explanation: return true when the neighboring cells are shown to be on the path
		    		
		    		if (findMazePath (x-1,y)
		    				|| findMazePath (x+1,y)
		    				|| findMazePath (x,y-1)		
		    				|| findMazePath (x,y+1)) {
		    			return true;
		    		} 
		    		
		    		//Step 4c
		    		//Explanation: Set dead ends to TEMPORARY color. The recursion won't happen unless the color is NON_BACKGROUND.

		    		else {
		    			maze.recolor(x, y, TEMPORARY);
		    			return false;
		    		}	
		    		
		    	}   	
		    	
	//Close findMazePath	 
    }

    // ADD METHOD FOR PROBLEM 2 HERE
   
    public ArrayList<ArrayList<PairInt>> findAllMazePaths(int x, int y) {
   	
    
    	//Step 1 
	    //Check if there are no solutions, return empty list
	        	
	    	if(!findMazePath(x,y)) {
	    		
	    		//Running findMazePath set colors on the maze to PATH & Temporary. Needs to be reset
	    		
	    		maze.recolor(PATH, NON_BACKGROUND);
	    		maze.recolor(TEMPORARY, NON_BACKGROUND);
	    		
	    		//Build an empty solution to return
	    		
	    		ArrayList<ArrayList<PairInt>> nosolution = new ArrayList<>();
	    		return nosolution;
	    				
	    	} else {
	    		
	    		//Running findMazePath set colors on the maze to PATH & Temporary. Needs to be reset
	    		
	    		maze.recolor(PATH, NON_BACKGROUND);
	    		maze.recolor(TEMPORARY, NON_BACKGROUND);
	    	
	    		// Creates empty result and trace to be placed in the helper method
	        	
	        	ArrayList<ArrayList<PairInt>> result = new ArrayList<>(); 
	        	Stack<PairInt> trace = new Stack <>(); 
	        	
	        	//Call helper method findMazePathStackBased
	        	//In this case, both result and trace are empty
	        	
	        	findMazePathStackBased(x,y,result,trace); 
	        	
	        	//Return the result, which shows all successful paths
	        	return result;
	    		
	    	}
    	
    }

//HELPER METHOD: findMazePathStackBased
    
    //Explanation: (x,y) are coordinates currently being visited), result is the list of successful paths, trace is path being explored
    //Repeat steps of findMazePath but if the current cell is on a path, it adds the cell to trace.
    //Once trace is proven successful, it is added to result
    
    public void findMazePathStackBased(int x, int y, ArrayList<ArrayList<PairInt>> result, Stack<PairInt> trace) {
    	
	    //Ensure current cell is inside grid is in NON_BACKGROUND color 
    	
    	if (x>=0 && y >= 0 && x < maze.getNCols() && y < maze.getNRows() && maze.getColor(x, y).equals(NON_BACKGROUND)) {
    				
    		// Add current cell to trace
    		trace.push(new PairInt(x,y));
    		
    		//Check if exit. If so, add all of trace to result and remove current cell from trace
    		
    		if (x == maze.getNCols()-1 && y == maze.getNRows()-1) {
    			
    			// add to result
    			
    			ArrayList<PairInt> toaddtoresult = new ArrayList<>();
    			toaddtoresult.addAll(trace);
    			result.add(toaddtoresult);
    			
    			//BACKTRACK STEP
    			//Pop from trace, 
    			trace.pop();
    			
    		} else {
    			
    			//Change current cell color to PATH
    			//Do before recursion but after check for end to ensure end remains open?
        		maze.recolor(x, y, PATH);
    			
    			//RECURSIVE STEPS - run helper method in all four directions
    			
    				//right
    			
        			findMazePathStackBased(x+1, y, result, trace);
    				
    				//left
        			
    				findMazePathStackBased(x-1, y, result, trace);
    				
    				//down
        			
    				findMazePathStackBased(x, y+1, result, trace);
    				
    				//up
        			
    				findMazePathStackBased(x, y-1, result, trace);
    			
    			//Recolor back to NON_BACKGROUND?
    			maze.recolor(x, y, NON_BACKGROUND);
    			
    			//Pop off the stack? 
    			trace.pop();
    				
    		} 			
    		
    	} 
	
    	// Close findMazePathStackBased
    	}
	       	
    
    // ADD METHOD FOR PROBLEM 3 HERE  
    //Answer will be a single ArrayList of PairInt objects
   
    public ArrayList<PairInt> findMazePathMin(int x, int y){
    	
    	//Check if maze has a solution
    	
    	if(!findMazePath(x,y)) {
    		
    		//Running findMazePath set colors on the maze to PATH & Temporary. Needs to be reset
    		
    		maze.recolor(PATH, NON_BACKGROUND);
    		maze.recolor(TEMPORARY, NON_BACKGROUND);
    		
    		//Return empty ArrayList
    		
    		ArrayList<PairInt> nosolution = new ArrayList<>();
    		return nosolution;
    		
    		
    	}
    	
    	else {
    		
		//Running findMazePath set colors on the maze to PATH & Temporary. Needs to be reset
    		
    		maze.recolor(PATH, NON_BACKGROUND);
    		maze.recolor(TEMPORARY, NON_BACKGROUND);
    		
		//Build solution set using findAllMazePaths
        
        ArrayList<ArrayList<PairInt>> allpaths = findAllMazePaths(x, y);
        
        //Initialize the return value "shortest" to be the first path in the ArrayList
        
        ArrayList<PairInt> shortest = new ArrayList<>();
        shortest = allpaths.get(0);
        
        //Cycle through the remaining paths to see if any are longer
        
        for(int i = 1; i < allpaths.size(); i++) {
        	
        	if(shortest.size() > allpaths.get(i).size()) {
        		shortest = allpaths.get(i);
        	}
            	
        }

        //Return the shortest path 
        
        return shortest;
            
    	}     
    	
    }
    
    /*<exercise chapter="5" section="6" type="programming" number="2">*/
    public void resetTemp() {
        maze.recolor(TEMPORARY, BACKGROUND);
    }
    /*</exercise>*/

    /*<exercise chapter="5" section="6" type="programming" number="3">*/
    public void restore() {
        resetTemp();
        maze.recolor(PATH, BACKGROUND);
        maze.recolor(NON_BACKGROUND, BACKGROUND);
    }
    /*</exercise>*/
}
/*</listing>*/
