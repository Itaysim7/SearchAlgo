/**
 * This class represents a search type of IDA* to solve the problem 
 * countVertices will count number of state the generated before the solution found
 * start - the start state for the problem
 * @author Itay Simhayev
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import java.util.Map.Entry;

public class IDA implements SearchAlgorithm
{
	private  int countVertices=1;
	private vertex start;
	private boolean printOpenList;

	public IDA(vertex s,boolean pol)
	{
		printOpenList=pol;
		start=s;
	}
	@Override
  /**
  * The function will run IDA* algorithm to find the goal node
  * @param start-the node start
  */
	public vertex doAlgo()
	{ 
	  vertex result=null;
	  Stack<vertex> stack=new Stack<vertex>();
	  HashMap<String, vertex> hMap= new HashMap<String, vertex>();
	  int trash=start.getCostH();
	  int infimum=Integer.MAX_VALUE;
	  while(trash<infimum)
	  {
		  int minf=Integer.MAX_VALUE;
		  start.setOut(false);
		  stack.push(start);
		  hMap.put(start.b.uniqeString(), start);
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
				  stack.push(front); //to save the path of the solution
				  move lastStep=front.getLastStep();
				  int row=front.b.getRow();int col=front.b.getCol();
				  ArrayList<vertex> operators=new ArrayList<vertex>();
				  //check each operator
				  if(lastStep!=move.Left&&col<front.b.mat[0].length-1&&front.b.mat[row][col+1].getColor()!=0) 
				  {
					  Board matLeft=front.b.createLeft();
					  operators.add(new vertex(matLeft,front.getCost()+front.b.mat[row][col+1].getColor(),front.getCost()+front.b.mat[row][col+1].getColor()+matLeft.heuristicFunction(),move.Right,""));
				  }
				  //operator up
				  if(lastStep!=move.Up&&row<front.b.mat.length-1&&front.b.mat[row+1][col].getColor()!=0) 
				  {
					  Board matUp=front.b.createUp();
					  operators.add(new vertex(matUp,front.getCost()+front.b.mat[row+1][col].getColor(),front.getCost()+front.b.mat[row+1][col].getColor()+matUp.heuristicFunction(),move.Down,""));				  
				  }
				  if(lastStep!=move.Right&&col>0&&front.b.mat[row][col-1].getColor()!=0) //step right
				  {
					  Board matRight=front.b.createRight();
					  operators.add(new vertex(matRight,front.getCost()+front.b.mat[row][col-1].getColor(),front.getCost()+front.b.mat[row][col-1].getColor()+matRight.heuristicFunction(),move.Left,""));
				  }
				  if(lastStep!=move.Down&&row>0&&front.b.mat[row-1][col].getColor()!=0) //step down
				  {
					  Board matDown=front.b.createDown();
					  operators.add(new vertex(matDown,front.getCost()+front.b.mat[row-1][col].getColor(),front.getCost()+front.b.mat[row-1][col].getColor()+matDown.heuristicFunction(),move.Up,""));
				  }
				  for(int i=0;i<operators.size();)
				  {
					  countVertices++;
					  vertex op=operators.remove(0);
					  boolean continueToNextOp=false;
					  if(op.getCostH()>trash) //cut this branch
					  {
						  if(minf>op.getCostH())
							  minf=op.getCostH();
						  continueToNextOp=true;
					  }
					  if(!continueToNextOp&&hMap.containsKey(op.b.uniqeString())) //loop avoidance
					  {
						  vertex contain=hMap.get(op.b.uniqeString());
						  if(!contain.getOut())
						  {
							  if(contain.getCostH()>op.getCostH())// if this is better f(g)
							  {
								  hMap.remove(contain.b.uniqeString());
								  stack.remove(contain);
							  }
							  else
								  continueToNextOp=true;
						  }
						  else
							  continueToNextOp=true;
					  }
					  if(!continueToNextOp&&op.b.isAns()) //if this is the goal
					  {
						  String path=reversePath(stack);
						  op.setPath(path+op.b.mat[row][col].getNum()+"D-");
						  return op;
					  }
					  if(!continueToNextOp) //add to the open list
					  {
						  stack.push(op);
						  hMap.put(op.b.uniqeString(), op);
					  }
				  }
			  }
		  }
		  trash=minf;
	  }
	  return result;
  }
	public int getCountVertices() 
	{
		return countVertices;
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
