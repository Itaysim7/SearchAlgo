/**
 * This class represents a search type of DFBnB to solve the problem 
 * countVertices will count number of state the generated before the solution found
 * start - the start state for the problem
 * @author Itay Simhayev
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import java.util.Map.Entry;

public class DFBnB implements SearchAlgorithm
{
	private  int countVertices=1;
	private vertex start;
	private int infimum;
	private boolean printOpenList;
	
	public DFBnB(vertex s,boolean pol)
	{
		printOpenList=pol;
		start=s;
		int count=countCell();
		if(count>12)
			infimum=Integer.MAX_VALUE;
		infimum=factorial(count);
	}
	@Override
  /**
  * The function will run DFBnB algorithm to find the goal node
  */
	public vertex doAlgo()
    { 
	  vertex result=null;
	  Stack<vertex> stack=new Stack<vertex>();stack.push(start);
	  HashMap<String, vertex> hMap= new HashMap<String, vertex>();hMap.put(start.b.uniqeString(), start);
	  int trash=infimum;
	  while(!stack.empty()) //while there is more state that not generated his child's
	  {
		  if(printOpenList)
			  printOpenL(hMap);
		  vertex front=stack.pop();		  
		  if(front.getOut()) 
			  hMap.remove(front.b.uniqeString());
		  else
		  {
			  front.setOut(true);
			  stack.push(front);
			  move lastStep=front.getLastStep();
			  int row=front.b.getRow();int col=front.b.getCol();
			  ArrayList<vertex> operators=new ArrayList<vertex>();
			  if(lastStep!=move.Left&&col<front.b.mat[0].length-1&&front.b.mat[row][col+1].getColor()!=0) //operator left
			  {
				  countVertices++;
				  Board matLeft=front.b.createLeft();
				  operators.add(new vertex(matLeft,front.getCost()+front.b.mat[row][col+1].getColor(),front.getCost()+front.b.mat[row][col+1].getColor()+matLeft.heuristicFunction(),move.Right,""));			 
			  }		  
			  if(lastStep!=move.Up&&row<front.b.mat.length-1&&front.b.mat[row+1][col].getColor()!=0) //operator up
			  {
				  countVertices++;
				  Board matUp=front.b.createUp();
				  operators.add(new vertex(matUp,front.getCost()+front.b.mat[row+1][col].getColor(),front.getCost()+front.b.mat[row+1][col].getColor()+matUp.heuristicFunction(),move.Down,""));
			  }
			  if(lastStep!=move.Right&&col>0&&front.b.mat[row][col-1].getColor()!=0) //operator right
			  {
				  countVertices++;
				  Board matRight=front.b.createRight();
				  operators.add(new vertex(matRight,front.getCost()+front.b.mat[row][col-1].getColor(),front.getCost()+front.b.mat[row][col-1].getColor()+matRight.heuristicFunction(),move.Left,""));
			  }
			  if(lastStep!=move.Down&&row>0&&front.b.mat[row-1][col].getColor()!=0) //operator down
			  {
				  countVertices++;
				  Board matDown=front.b.createDown();
				  operators.add(new vertex(matDown,front.getCost()+front.b.mat[row-1][col].getColor(),front.getCost()+front.b.mat[row-1][col].getColor()+matDown.heuristicFunction(),move.Up,""));
			  }
			  operators.sort(new vertex_Comperator());
			  for(int i=0;i<operators.size();i++)
			  {
				  vertex op=operators.get(i);
				  if(op.getCostH()>=trash) //if the cost of this vertex and the other in the list is higher then the trash
				  {
					  for(int j=i;j<operators.size();)
						  operators.remove(j);
				  }
				  else
				  {
					  if(hMap.containsKey(op.b.uniqeString())&&hMap.get(op.b.uniqeString()).getOut())//if is already in the list and marked as out
					  {
						  operators.remove(i);
						  i--;
					  }
					  else
					  {
						  if(hMap.containsKey(op.b.uniqeString())&&!hMap.get(op.b.uniqeString()).getOut())//if is already in the list and not marked as out
						  {
							  vertex contain=hMap.get(op.b.uniqeString());
							  if(contain.getCostH()<=op.getCostH()) //if contain has higher cost then the state in the open list
							  {
								  operators.remove(i);
								  i--;
							  }
							  else
							  {
								  stack.remove(contain);
								  hMap.remove(op.b.uniqeString());
							  }		  
						  }
						  else
						  {
							  if(op.b.isAns()) //if this is the goal
							  {
								  trash=op.getCostH();
								  Stack<vertex> copyStack=(Stack<vertex>)stack.clone();
								  String path=reversePath(copyStack);
								  result=op.copy();
								  result.setPath(path+result.b.mat[row][col].getNum()+result.lastStep());
								  for(int j=i;j<operators.size();)
									  operators.remove(j);
							  }
						  }
					  }
				  }
			  }
			  while(!operators.isEmpty()) //add all state to the open list
			  {
				  stack.push(operators.get(operators.size()-1));
				  hMap.put(operators.get(operators.size()-1).b.uniqeString(), operators.get(operators.size()-1));
				  operators.remove(operators.size()-1);
			  }
		  }	  
	  }
	  return result;
  }
	public int getCountVertices() 
	{
		return countVertices;
	}
	 /**
	   * The function will count the cell that not black in the matrix
	   * @return number of cell 
	   */
	public int countCell() 
	{
		int count=0;
		for(int i=0;i<start.b.mat.length;i++)
		{
			for(int j=0;j<start.b.mat[i].length;j++)
			{
				if(start.b.mat[i][j].getColor()!=0)
					count++;
			}
		}
		return count;
	}
	 /**
	   * The function will return the factorial of n
	   * @param n- input to calculate
	   * @return factorial of n 
	   */
	public int factorial(int n)
	{
	    int fact = 1;
	    for (int i = 2; i <= n; i++) {
	        fact = fact * i;
	    }
	    return fact;
	}
	  /**
	   * The function will return the solution path for ida*, DFBnb algorithms
	   * @param s- Stack type of vertex 
	   * @return String 
	   */
	  private  String reversePath(Stack<vertex> s) 
	  {
		  String path="";
		  vertex pre=s.pop();
		  while(!s.empty())
		  {
			  vertex next=s.pop();
			  if(pre.getOut()&&next.getOut())
			  {
				  if(pre.b.getRow()<next.b.getRow())
				  {
					  path=pre.b.mat[next.b.getRow()][next.b.getCol()].getNum()+"D"+"-"+path;
				  }
				  if(pre.b.getRow()>next.b.getRow())
				  {
					  path=pre.b.mat[next.b.getRow()][next.b.getCol()].getNum()+"U"+"-"+path;
				  }
				  if(pre.b.getCol()<next.b.getCol())
				  {
					  path=pre.b.mat[next.b.getRow()][next.b.getCol()].getNum()+"R"+"-"+path;
				  }
				  if(pre.b.getCol()>next.b.getCol())
				  {
					  path=pre.b.mat[next.b.getRow()][next.b.getCol()].getNum()+"L"+"-"+path;
				  }
				  pre=next;
			  }
		  }
		  return path;
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
