import java.util.Stack;

/**
 * This class represents a node in a graph,every vertex has mat, rowEmpty, colEmpty, cost, lastStep, path
 * mat is matrix type of cell,rowEmpty and V represent the place of the empty cell in the matrix.
 * cost represent the cost to get from the node start to this node.
 * lastStep represent from which node this node created.
 * path represent the path from the node start to this start
 * @author Itay Simhayev
 */
public class vertex 
{
	public Board b;
	private int cost;
	private int costH;
	private move lastStep;
	private String path;
	private boolean out;
	private long timestamp;
	public vertex(cell [][]temp,int cost,move last,String path)
	{
		b=new Board(temp);
		out=false;
		lastStep=last;
		this.cost=cost;
		this.path=path;
		costH=b.heuristicFunction();
		timestamp=System.currentTimeMillis();
	}
	public vertex(Board b1,int cost,move last,String path)
	{
		b=b1.copy();
		out=false;
		lastStep=last;
		this.cost=cost;
		this.path=path;
		costH=b.heuristicFunction();
		timestamp=System.currentTimeMillis();
	}
	public vertex(Board b1,int cost,int costh,move last,String path)
	{
		b=b1.copy();
		out=false;
		costH=costh;
		lastStep=last;
		this.cost=cost;
		this.path=path;
		timestamp=System.currentTimeMillis();
	}
	public int getCost() 
	{
		return this.cost;
	}
	public int getCostH() 
	{
		return this.costH;
	}
	public move getLastStep() 
	{
		return this.lastStep;
	}
	public String getPath() 
	{
		return this.path;
	}
	public void setPath(String s) 
	{
		this.path=s;
	}
	public boolean getOut() 
	{
		return this.out;
	}
	public void setOut(boolean t) 
	{
		this.out=t;
	}
	public long getTime() 
	{
		return this.timestamp;
	}
	public void setTime(long t) 
	{
		this.timestamp=t;
	}
	public vertex copy()
	{
		vertex copy=new vertex(b,cost,costH,lastStep,path);
		copy.setTime(this.timestamp);
		copy.setOut(this.out);
		return copy;	
	}
	  /**
	   * The function will return the last step represent by One char
	   * @return String 
	   */
	  public String lastStep() 
	  {
		  if(lastStep==move.Right)
			  return "L-";
		  if(lastStep==move.Down)
			  return "U-";
		  if(lastStep==move.Left)
			  return "R-";
		  if(lastStep==move.Up)
			  return "D-";
		  return "";
	  }

}
