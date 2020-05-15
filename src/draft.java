import java.io.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class draft 
{
	static boolean printTimeRunning=true;
	static boolean printOpenList=true;
	///////////////input function/////////////////
	/**
	 * The function fill the matrix with number and color
	 * @param mat - matrix type of cell that we fill
	 * @param colorsCells - matrix with color of the number
	 * @throws IOException if the file is not readable 
	 */
	public static void fillNumber(cell[][]mat,int []colorsCells,BufferedReader br) throws IOException
	  {
		  for(int i=0;i<mat.length;i++)
		  {
			  String str=br.readLine();
			  for(int j=0;j<mat[i].length-1;j++)
			  {
				  int place=str.indexOf(",");
				  if(str.substring(0,place).equals("_"))// if this is the empty cell
					  mat[i][j]=new cell(-1,0);
				  else //the cell is number
				  {
					  int num=Integer.parseInt(str.substring(0,place));
					  mat[i][j]=new cell(colorsCells[num],num);
					  str=str.substring(place+1);  
				  }
			  }
			  //the last number in each row
			  if(str.equals("_"))
				  mat[i][mat[i].length-1]=new cell(-1,0);
			  else
				  mat[i][mat[i].length-1]=new cell(colorsCells[Integer.parseInt(str)],Integer.parseInt(str));
		  }
	  }
	/**
	 * The function fill the matrix with color for each index
	 * @param mat - matrix type of int that will fill with colors
	 * @param cells - string that will be convert to colors of the indexes 
	 */
	  public static void fillColors(int[]mat,int color,String cells )
	  {
		  if(cells.length()>0)//if the string empty return
		  {
			  cells=cells.substring(1);//delete the char '_' from the string
			  boolean isEmpty=false;
			  while(!isEmpty)
			  {
				  int place=cells.indexOf(",");
				  if(place==-1)//the last number in the list
				  {
					  isEmpty=true;
					  mat[Integer.parseInt(cells)]=color;
				  }
				  else
				  {
					  mat[Integer.parseInt(cells.substring(0,place))]=color;
					  cells=cells.substring(place+1);
				  }
			  }
		  }
		  
	  }
//////////////////helpful functions for the algorithms/////////////////////
	  /**
	   * The function will write to a file  the output of the algorithm
	   * @param path - path from the node start to the goal start
	   * @param countVer - the depth of the node
	   * @param cost - cost from the node start to the goal start
	   * @param time - time for the algorithm to find the goal node
	   */	  
  public static void writeToFile(String path,int countVer,int cost,float time)
  {
	  try 
	  {  
		FileWriter fw = new FileWriter("outPut.txt");
		PrintWriter outs = new PrintWriter(fw);
		outs.println(path);
		outs.println(countVer);
		outs.println(cost);
		if(printTimeRunning)
			outs.println(time);
		outs.close(); 
		fw.close();	
	}
	catch(IOException e)
	{  
      e.printStackTrace();
      System.out.println("Error file is not writable\\n");
	}
  }
  /**
   * The function will return if the node is the goal node
   * @param mat- matrix of the node 
   */
  public static boolean isAns(cell[][]mat) 
  {
	  for(int i=0;i<mat.length;i++)
	  {
		  for(int j=0;j<mat[i].length;j++)
		  {
			  if(i==mat.length-1&&j==mat[i].length-1)//if it is the last cell in the matrix 
			  {
				  if(mat[i][j].getNum()!=0)//check if the value in the cell as it should be
					  return false;
			  }
			  else
			  {
				  if(mat[i][j].getNum()!=i*mat[i].length+j+1)//check if the value in the cell as it should be
					  return false;
			  }
		  }
	  }
	  return true;
  }
  /**
   * The function will copy the matrix
   * @param mat- matrix of the node 
   */
  public static cell[][] copyMat(cell[][]mat) 
  {
	  cell[][] result=new cell[mat.length][mat[0].length];
	  //copy the matrix
	  for(int i=0;i<mat.length;i++) 
	  {
		  for(int j=0;j<mat[i].length;j++)
		  {
			  result[i][j]=mat[i][j];
		  }
	  }
	  return result;
  }
  /**
   * The function will create left matrix
   * @param mat- the matrix of the curtain node 
   */
  public static cell[][] createLeft(cell[][]mat,int row,int col) 
  {
	  cell [][]matLeft=copyMat(mat);
	  cell tempCell=matLeft[row][col];
	  matLeft[row][col]=matLeft[row][col+1];
	  matLeft[row][col+1]=tempCell;
	  return matLeft;
  }
  /**
   * The function will create up matrix
   * @param mat- the matrix of the curtain node 
   */
  public static cell[][] createUp(cell[][]mat,int row,int col) 
  {
	  cell [][]matUp=copyMat(mat);
	  cell tempCell=matUp[row][col];
	  matUp[row][col]=matUp[row+1][col];
	  matUp[row+1][col]=tempCell;
	  return matUp;
  }
  /**
   * The function will create right matrix
   * @param mat- the matrix of the curtain node 
   */
  public static cell[][] createRight(cell[][]mat,int row,int col) 
  {
	  cell [][]matRight=copyMat(mat);
	  cell tempCell=matRight[row][col];
	  matRight[row][col]=matRight[row][col-1];
	  matRight[row][col-1]=tempCell;
	  return matRight;
  }
  /**
   * The function will create down matrix
   * @param mat- the matrix of the curtain node 
   */
  public static cell[][] createDown(cell[][]mat,int row,int col) 
  {
	  cell [][]matDown=copyMat(mat);
	  cell tempCell=matDown[row][col];
	  matDown[row][col]=matDown[row-1][col];
	  matDown[row-1][col]=tempCell;
	  return matDown;
  }
  public static String uniqeString(cell[][]mat) 
  {
	  String result="";
	  for(int i=0;i<mat.length;i++) 
	  {
		  for(int j=0;j<mat[i].length;j++)
		  {
			  result=result+mat[i][j].getNum()+"$";
		  }
	  }
	  return result;
  }
  ///////////////////algorithms////////////////////////////
  /**
   * The function will run bfs algorithm to find the goal node
   * @param start-the node start
   * @param printOpenList- if true print the open list and the results to the screen else write to a file
   */
  public static void bfs(vertex start)
  {
	  Queue<vertex> queue = new LinkedList<>();
	  long startTime= System.currentTimeMillis();
	  long totalTime= 0;
	  queue.add(start);
	  int count=1;//count number of vertices that created
	  vertex goal=null;
	  HashMap<String, vertex> verString= new HashMap<String, vertex>();
	  while(!queue.isEmpty()&&goal==null)
	  {
		  vertex temp=queue.remove();
		  verString.put(uniqeString(temp.mat), temp);
		  move lastStep=temp.getLastStep();
		  int row=temp.getRowEmpty();int col=temp.getColEmpty();
		  if(lastStep!=move.Left&&col<temp.mat[0].length-1&&temp.mat[row][col+1].getColor()!=1) //step left
		  {
			  count++;
			  cell [][]matLeft=createLeft(temp.mat,row,col);
			  if(isAns(matLeft))
			  {
				  totalTime= (System.currentTimeMillis()-startTime);
				  goal=new vertex(matLeft,temp.getCost()+1,move.Right,temp.getPath()+""+matLeft[row][col].getNum()+"L");
			  }
			  else
			  {
				  vertex right=new vertex(matLeft,temp.getCost()+1,move.Right,temp.getPath()+""+matLeft[row][col].getNum()+"L-");
				  queue.add(right);
			  }
		  }
		  if(lastStep!=move.Up&&row<temp.mat.length-1&&temp.mat[row+1][col].getColor()!=1&&goal==null) //step up
		  {
			  count++;
			  cell [][]matUp=createUp(temp.mat,row,col);
			  if(isAns(matUp))
			  {
				  totalTime= (System.currentTimeMillis()-startTime);
				  goal=new vertex(matUp,temp.getCost()+1,move.Down,temp.getPath()+""+matUp[row][col].getNum()+"U");
			  }
			  else
			  {
				  vertex down=new vertex(matUp,temp.getCost()+1,move.Down,temp.getPath()+""+matUp[row][col].getNum()+"U-");
				  queue.add(down);  
			  }
		  }
		  if(lastStep!=move.Right&&col>0&&temp.mat[row][col-1].getColor()!=1&&goal==null) //step right
		  {
			  count++;
			  cell [][]matRight=createRight(temp.mat,row,col);
			  if(isAns(matRight))
			  {
				  totalTime= (System.currentTimeMillis()-startTime);
				  goal=new vertex(matRight,temp.getCost()+1,move.Left,temp.getPath()+""+matRight[row][col].getNum()+"R");

			  }
			  vertex left=new vertex(matRight,temp.getCost()+1,move.Left,temp.getPath()+""+matRight[row][col].getNum()+"R-");
			  queue.add(left);
		  }
		  if(lastStep!=move.Down&&row>0&&temp.mat[row-1][col].getColor()!=1&&goal==null) //step up
		  {
			  count++;
			  cell [][]matDown=createDown(temp.mat,row,col);
			  if(isAns(matDown))
			  {
				  totalTime= (System.currentTimeMillis()-startTime);
				  goal=new vertex(matDown,temp.getCost()+1,move.Up,temp.getPath()+""+matDown[row][col].getNum()+"D"); 
			  }
			  else
			  {
				  vertex up=new vertex(matDown,temp.getCost()+1,move.Up,temp.getPath()+""+matDown[row][col].getNum()+"D-"); 
				  queue.add(up); 
			  }
		  }
	  }
	  if(!printOpenList) 
	  {
		  writeToFile(goal.getPath(),count,goal.getCost(),totalTime/1000F);
	  }
	  else
	  {
		  System.out.println(goal.getPath());
		  System.out.println(count);
		  System.out.println(goal.getCost());
		  if(printTimeRunning)
			  System.out.println(totalTime/1000F);
	  }
  }
  
  /**
   * The function will run bfs algorithm to find the goal node
   * @param start-the node start
   * @param printOpenList- if true print the open list and the results to the screen else write to a file
   */
  public static void dfs(vertex start,int depth) 
  {
	  Deque<vertex> stack = new ArrayDeque<vertex>();
	  long startTime= System.currentTimeMillis();
	  long totalTime= 0;
	  stack.add(start);
	  int count=1;//count number of vertices that created
	  vertex goal=null;
	  HashMap<String, vertex> verString= new HashMap<String, vertex>();
	  while(!stack.isEmpty()&&goal==null)
	  {
		  
	  }
  }
  public static void main(String[] args)throws Exception 
  { 
	  
	  File file = new File("input.txt"); 
	  BufferedReader br=new BufferedReader(new FileReader(file)); 
	  String algo=br.readLine();//first line will tell us witch algorithm we need to use
	  String timeRunning=br.readLine();//second line will tell us if to print the time running
	  if(timeRunning.equals("no time")) //check the second line
		  printTimeRunning=false;
	  else
		  if(!timeRunning.equals("with time"))
				throw new RuntimeException("Wrong input for second line");
	  String OpenList=br.readLine();//third line will tell us if to print the open list in every step 
	  if(OpenList.equals("no open"))//check the third line
		  printOpenList=false;
	  else
		  if(!OpenList.equals("with open"))
				throw new RuntimeException("Wrong input for third line");
	  String sizeMatrix=br.readLine();//four line will tell us the format of the matrix
	  int placeX=sizeMatrix.indexOf("x");
	  System.out.println(placeX);
	  int row=Integer.parseInt(sizeMatrix.substring(0,placeX));
	  int col=Integer.parseInt(sizeMatrix.substring(placeX+1,sizeMatrix.length()));
	  int colorsCells[]=new int[row*col];
	  String blackList=br.readLine();//five line will tell us the format of the matrix
	  fillColors(colorsCells,1,blackList.substring(6));//fill the color 1 for black in the matrix
	  String redList=br.readLine();//five line will tell us the format of the matrix
	  fillColors(colorsCells,2,redList.substring(4));//fill the color 2 for red in the matrix
	  cell numberCells[][]=new cell[row][col];
	  fillNumber(numberCells,colorsCells,br);
	  System.out.println(isAns(numberCells));
	  switch(algo)
		{//case for algorithm
			case "BFS":
			{
				vertex start=new vertex(numberCells,0,move.None,"");	
				bfs(start);
				break;
			}
			case "DFID":
			{
				System.out.println("DFID");
				break;
			}
			case "A*":
			{
				System.out.println("A*");
				break;
			}
			case "IDA*":
			{
				System.out.println("IDA*");
				break;
			}
			case "DFBnB":
			{
				System.out.println("DFBnB*");
				break;
			}
			default :
			{
				throw new RuntimeException("There is not such algorithm");
			}
		}
  } 
} 





//public  String solve(Node root, boolean toTime, boolean openList)
//{
//	int [] lev=new int[5];
//	Node goal=null;
//	long start= System.currentTimeMillis();
//	int i=0;
//	AbstractQueue<Node> Q = new LinkedBlockingQueue<Node>(); 
//	Q.add(root);
//	lev[0]++;
//	HashMap<Integer, Node> hm= new HashMap<Integer, Node>();
//	while(!Q.isEmpty()) 
//	{
//		Node current=Q.poll();
//		if(current.getDepth()>10)
//			break;
//		hm.put(i++, current);
//		Node l=current.exploreLeft();
//		if(l!=null ) {
//			if( l.isGoal()) {  
//				goal=l;
//				break;
//			}
//			Q.add(l);
//		}
//		Node u=current.exploreUp();
//		if(u!=null ) {
//			if(u.isGoal()) {  
//				goal=u;
//				break;
//			}
//			Q.add(u);
//		}
//		Node r=current.exploreRight();
//		if(r!=null ) {
//			if(r.isGoal()) {  
//				goal=r;
//				break;
//			}
//			Q.add(r);
//		}
//		Node d=current.exploreDown();
//		if(d!=null ) {
//			if(d.isGoal()) {  
//				goal=d;
//				break;
//			}
//			Q.add(d);
//		}
//		if(l!=null) {
//			lev[l.getDepth()]++;
//		}
//		if(u!=null) {
//			lev[u.getDepth()]++;
//		}
//		if(r!=null) {
//			lev[r.getDepth()]++;	
//		}
//		if(d!=null) {
//			lev[d.getDepth()]++;
//		}
//	}
////	for (int j = 0; j < lev.length; j++) {
////		System.out.println(j+"  ="+lev[j]);
////	}
//	long end=System.currentTimeMillis();
//	time=end-start;
//	System.out.println("time= "+time/1000.0+" in seconds");
//	if(goal!=null) {
//		return goal.path().substring(0, goal.path().length()-1)+"\n num:"+(Q.size()+hm.size())+"\n cost: "+goal.getCost();
//	}
//	saveToFile(toTime, openList, goal, (Q.size()+hm.size()));
//	return "no path";
//}