/**
 * This class represents a search type of A* to solve the problem 
 * countVertices will count number of state the generated before the solution found
 * start - the start state for the problem
 * @author Itay Simhayev
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Map.Entry;

public class ASTAR implements SearchAlgorithm
{
	private  int countVertices=1;
	private vertex start;
	private boolean printOpenList;

	public ASTAR(vertex s,boolean pol)
	{
		printOpenList=pol;
		start=s;
	}
	@Override
  /**
  * The function will run A* algorithm to find the goal node
  * @param start-the node start
  */
	public vertex doAlgo()
	{ 
	  PriorityQueue<vertex> pq=new PriorityQueue <vertex>(new vertex_Comperator());
	  pq.add(start);
	  HashMap<String, vertex> openList= new HashMap<String, vertex>();
	  HashMap<String, vertex> closeList= new HashMap<String, vertex>();
	  openList.put(start.b.uniqeString(), start);
	  while(!pq.isEmpty()) //while there is more state that not generated his child's
	  {
		  if(printOpenList)
			  printOpenL(openList);
		  vertex current=pq.poll();
		  openList.remove(current.b.uniqeString());
		  if(current.b.isAns())
			  return current;
		  closeList.put(current.b.uniqeString(), current);
		  move lastStep=current.getLastStep();
		  int row=current.b.getRow();int col=current.b.getCol();
		  ArrayList<vertex> operators=new ArrayList<vertex>();
		  //for each legal operator of start
		  //operator left
		  if(lastStep!=move.Left&&col<current.b.mat[0].length-1&&current.b.mat[row][col+1].getColor()!=0) 
		  {
			  Board matLeft=current.b.createLeft();
			  operators.add(new vertex(matLeft,current.getCost()+current.b.mat[row][col+1].getColor(),current.getCost()+current.b.mat[row][col+1].getColor()+matLeft.heuristicFunction(),move.Right,current.getPath()+""+matLeft.mat[row][col].getNum()+"L-"));
		  }
		  //operator up
		  if(lastStep!=move.Up&&row<current.b.mat.length-1&&current.b.mat[row+1][col].getColor()!=0) 
		  {
			  Board matUp=current.b.createUp();
			  operators.add(new vertex(matUp,current.getCost()+current.b.mat[row+1][col].getColor(),current.getCost()+current.b.mat[row+1][col].getColor()+matUp.heuristicFunction(),move.Down,current.getPath()+""+matUp.mat[row][col].getNum()+"U-"));
		  }
		  if(lastStep!=move.Right&&col>0&&current.b.mat[row][col-1].getColor()!=0) //step right
		  {
			  Board matRight=current.b.createRight();
			  operators.add(new vertex(matRight,current.getCost()+current.b.mat[row][col-1].getColor(),current.getCost()+current.b.mat[row][col-1].getColor()+matRight.heuristicFunction(),move.Left,current.getPath()+""+matRight.mat[row][col].getNum()+"R-"));
		  }
		  if(lastStep!=move.Down&&row>0&&current.b.mat[row-1][col].getColor()!=0) //step down
		  {
			  Board matDown=current.b.createDown();
			  operators.add(new vertex(matDown,current.getCost()+current.b.mat[row-1][col].getColor(),current.getCost()+current.b.mat[row-1][col].getColor()+matDown.heuristicFunction(),move.Up,current.getPath()+""+matDown.mat[row][col].getNum()+"D-"));
		  }
		  for(int i=0;i<operators.size();)
		  {
			  countVertices++;
			  vertex op=operators.remove(0);
			  String keyString=op.b.uniqeString();
			  if(!closeList.containsKey(keyString)&&!openList.containsKey(keyString))// if not in any hash table
			  {
				  pq.add(op);
				  openList.put(keyString,op);
			  }
			  else
				  if(openList.containsKey(keyString)&&openList.get(keyString).getCostH()>current.getCostH())// if the cost of the state is better then the same state in the hash
				  {
					  pq.remove(openList.get(keyString));
					  pq.add(op);
					  openList.replace(keyString, openList.get(keyString), op);
				  }
		  }
	  }
	  return null;
  }
	public int getCountVertices() 
	{
		return countVertices;
	}
	/**
	 * print the open list to the screen
	 */
	@Override
	public void printOpenL(HashMap<String, vertex> h) 
	{
		System.out.println("-----------new iteration----------");
		for (Entry<String, vertex> entry : h.entrySet())
		{
			String keyTemp=entry.getKey();
			printme(h.get(keyTemp).b.mat);
		}
	}
	/**
	 * print the board of the vertex
	 */
	private void printme(cell[][]mat) 
	{
		  for(int i=0;i<mat.length;i++) 
		  {
			  for(int j=0;j<mat[i].length;j++)
			  {
				  if(mat[i][j].getNum()!=0)
					  System.out.print(mat[i][j].getNum()+",");
				  else
					  System.out.print("_,");
			  }
			  System.out.println();
		  }
		  System.out.println();
	}

}
