import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class DFBnB implements SearchAlgorithm
{
	private  int countVertices=1;
	private vertex start;
	public DFBnB(vertex s)
	{
		start=s;
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
	//  int trash=Integer.MAX_VALUE;
	  int trash=40320;
	  while(!stack.empty())
	  {
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
				  if(op.getCostH()>=trash)
				  {
					  for(int j=i;j<operators.size();)
						  operators.remove(j);
				  }
				  else
				  {
					  if(hMap.containsKey(op.b.uniqeString())&&hMap.get(op.b.uniqeString()).getOut())
					  {
						  operators.remove(i);
						  i--;
					  }
					  else
					  {
						  if(hMap.containsKey(op.b.uniqeString())&&!hMap.get(op.b.uniqeString()).getOut())
						  {
							  vertex contain=hMap.get(op.b.uniqeString());
							  if(contain.getCostH()<=op.getCostH())
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
			  while(!operators.isEmpty())
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

}
