import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class BFS implements SearchAlgorithm
{
	private  int countVertices=1;
	private vertex start;
	public BFS(vertex s)
	{
		start=s;
	}
	@Override
  /**
  * The function will run bfs algorithm to find the goal node
  * @param start-the node start
  */
	public vertex doAlgo()
	{
		  Queue<vertex> queue = new LinkedList<>();
		  queue.add(start);
		  HashMap<String, vertex> openList= new HashMap<String, vertex>();
		  HashMap<String, vertex> closeList= new HashMap<String, vertex>();
		  while(!queue.isEmpty())
		  {
			  vertex temp=queue.remove();
			  closeList.put(temp.b.uniqeString(), temp);
			  move lastStep=temp.getLastStep();
			  int row=temp.b.getRow();int col=temp.b.getCol();
			  if(lastStep!=move.Left&&col<temp.b.mat[0].length-1&&temp.b.mat[row][col+1].getColor()!=0) //step left
			  {
				  countVertices++;
				  Board matLeft=temp.b.createLeft();
				  vertex left=new vertex(matLeft,temp.getCost()+temp.b.mat[row][col+1].getColor(),move.Right,temp.getPath()+""+matLeft.mat[row][col].getNum()+"L-");
				  if(!closeList.containsKey(matLeft.uniqeString())&&!openList.containsKey(matLeft.uniqeString()))
				  {
					  if(matLeft.isAns())
						  return left;
					  openList.put(matLeft.uniqeString(), left);
					  queue.add(left);
				  }
			  }
			  if(lastStep!=move.Up&&row<temp.b.mat.length-1&&temp.b.mat[row+1][col].getColor()!=0) //step up
			  {
				  countVertices++;
				  Board matUp=temp.b.createUp();
				  vertex up=new vertex(matUp,temp.getCost()+temp.b.mat[row+1][col].getColor(),move.Down,temp.getPath()+""+matUp.mat[row][col].getNum()+"U-");
				  if(!closeList.containsKey(matUp.uniqeString())&&!openList.containsKey(matUp.uniqeString()))
				  {
					  if(matUp.isAns())
						  return up;
					  openList.put(matUp.uniqeString(), up);
					  queue.add(up);
				  }
			  }
			  if(lastStep!=move.Right&&col>0&&temp.b.mat[row][col-1].getColor()!=0) //step right
			  {
				  countVertices++;
				  Board matRight=temp.b.createRight();
				  vertex right=new vertex(matRight,temp.getCost()+temp.b.mat[row][col-1].getColor(),move.Left,temp.getPath()+""+matRight.mat[row][col].getNum()+"R-");
				  if(!closeList.containsKey(matRight.uniqeString())&&!openList.containsKey(matRight.uniqeString()))
				  {
					  if(matRight.isAns())
						  return right;
					  openList.put(matRight.uniqeString(), right);
					  queue.add(right);
				  }
			  }
			  if(lastStep!=move.Down&&row>0&&temp.b.mat[row-1][col].getColor()!=0) //step up
			  {
				  countVertices++;
				  Board matDown=temp.b.createDown();
				  vertex down=new vertex(matDown,temp.getCost()+temp.b.mat[row-1][col].getColor(),move.Up,temp.getPath()+""+matDown.mat[row][col].getNum()+"D-"); 
				  if(!closeList.containsKey(matDown.uniqeString())&&!openList.containsKey(matDown.uniqeString()))
				  {
					  if(matDown.isAns())
						  return down;
					  openList.put(matDown.uniqeString(), down);
					  queue.add(down);
				  }
			  }
		  }
		  return null;
	}
	public int getCountVertices() 
	{
		return countVertices;
	}

}
