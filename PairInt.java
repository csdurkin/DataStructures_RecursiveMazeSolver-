package Maze;

//CLASS DECLARATION

	public class PairInt {

//PARAMETER DECLARATIONS
	
	private int x;
	private int y;

//Constructor Method 	
	public PairInt(int x, int y) {
		this.x = x; 
		this.y = y;
	}
	
//GETTER METHODS
	
	//X
	public int getX(){
		return x;
	}
	
	//Y
	public int getY() {
		return y;
	}
	
//SETTER METHODS	
	
	//X
	public void setX(int x) {
		this.x = x;
	}
	
	//Y
	public void setY(int y) {
		this.y = y;
	}
	
//EQUALS METHODS	
	
	public boolean equals(Object p) {
		
		//Confirm Object p is an instance of PairInt
		if(!(p instanceof PairInt)) {
			return false;
		} 
		
		else {
			
			//Downcast the Object to make it PairInt
			//Question: Necessary to downcast?
			PairInt temp = (PairInt)p;
			
			//Compare temp's x and y to this object's x and y
			return temp.x == this.x && temp.y == this.y;
		}
		
	}
	
//TO STRING METHOD	
	public String toString() {
		
		//Return string, using String.valueOf to make the int values string values
		return "("+ String.valueOf(x) + "," + String.valueOf(y) +") ";
	}
	
//COPY METHOD	
	
	public PairInt copy() {
		
		//Create a new copy of PairInt
		PairInt copypairint = new PairInt(x,y);
		
		//Return the new copy
		return copypairint;
	}
	
}
