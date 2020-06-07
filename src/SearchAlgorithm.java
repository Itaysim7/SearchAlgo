import java.util.HashMap;

public interface SearchAlgorithm
{
	/**
	 * Perform the search according to the algorithm
	 */
	public vertex doAlgo();
	/**
	 * return number of vertices that generated to solve the problem 
	 */
	public  int getCountVertices() ;
	/**
	 * print the open list to the screen
	 */
	public void printOpenL(HashMap<String, vertex> h) ;
	
}
