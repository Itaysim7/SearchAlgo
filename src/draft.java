import java.io.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class draft 
{
	static boolean printTimeRunning=true;
	static boolean printOpenList=true;
	static int countVertices=1;
	static String algo;
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
				  {
					  mat[i][j]=new cell(-1,0);
					  str=str.substring(place+1); 
				  }
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
	   * @param cost - the cost from the node start to the goal 
	   * @param time - time for the algorithm to find the goal node
	   */	  
	 public static cell[][] readFromFile()throws Exception 
	 {
		 File file = new File("input.txt"); 
		 BufferedReader br=new BufferedReader(new FileReader(file)); 
		 algo=br.readLine();//first line will tell us witch algorithm we need to use
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
		  int row=Integer.parseInt(sizeMatrix.substring(0,placeX));
		  int col=Integer.parseInt(sizeMatrix.substring(placeX+1,sizeMatrix.length()));
		  int colorsCells[]=new int[row*col];
		  for(int i=0;i<colorsCells.length;i++)
			  colorsCells[i]=1;
		  String blackList=br.readLine();//five line will tell us the format of the matrix
		  fillColors(colorsCells,0,blackList.substring(6));//fill the color 0 for black in the matrix
		  String redList=br.readLine();//five line will tell us the format of the matrix
		  fillColors(colorsCells,30,redList.substring(4));//fill the color 30 for red in the matrix
		  cell numberCells[][]=new cell[row][col];
		  fillNumber(numberCells,colorsCells,br);
		  return numberCells;
	 }
	  /**
	   * The function will write to a file  the output of the algorithm
	   * @param path - path from the node start to the goal start
	   * @param cost - the cost from the node start to the goal 
	   * @param time - time for the algorithm to find the goal node
	   */	  
  public static void writeToFile(String path,int cost,float time)
  {
	  try 
	  {  
		FileWriter fw = new FileWriter("outPut.txt");
		PrintWriter outs = new PrintWriter(fw);
		if(path.length()>0)
			outs.println(path);
		else
			outs.println("no path");
		outs.println("Num: "+countVertices);
		if(cost!=-1)
			outs.println("Cost: "+cost);
		if(printTimeRunning)
			outs.println(time+ " secondes");
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
   * The function will write to a file  the output of the algorithm
   * @param path - path from the node start to the goal start
   * @param cost - the cost from the node start to the goal 
   * @param time - time for the algorithm to find the goal node
   */	  
	public static void printToScreen(String path,int cost,float time)
	{
		if(path.length()>0)
			System.out.println(path);
		else
			System.out.println("no path");
		System.out.println("Num: "+countVertices);
		if(cost!=-1)
			System.out.println("Cost: "+cost);
		if(printTimeRunning)
			System.out.println(time+ " secondes");
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
   * The function will return discrepancy between the goal mat and the current mat
   * @param mat- matrix of the current node 
   * @return discrepancy between the state
   */
  public static int heuristicFunction(cell[][]current) 
  {
	  int count=0;
	  int rowPlace=0;int colPlace=0;
	  for(int i=0;i<current.length;i++)
	  {
		  for(int j=0;j<current[i].length;j++)
		  {
			  if(current[i][j].getNum()!=0)//if this is the empty block 
			  {
				  rowPlace=(current[i][j].getNum()-1)/current[i].length;
				  colPlace=(current[i][j].getNum()-1)%current[i].length;
				  count=count+((Math.abs(rowPlace-i))+(Math.abs(colPlace-j)))*current[i][j].getColor();
			  }
		  }
	  }
	  return count;
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
			  result=result+mat[i][j].getNum()+",";
		  }
	  }
	  return result;
  }
  ///////////////////algorithms////////////////////////////
  /**
   * The function will run bfs algorithm to find the goal node
   * @param start-the node start
   */
  public static vertex bfs(vertex start)
  {
	  Queue<vertex> queue = new LinkedList<>();
	  queue.add(start);
	  vertex goal=null;
	  HashMap<String, vertex> verString= new HashMap<String, vertex>();
	  while(!queue.isEmpty()&&goal==null)
	  {
		  vertex temp=queue.remove();
		  verString.put(uniqeString(temp.mat), temp);
		  move lastStep=temp.getLastStep();
		  int row=temp.getRowEmpty();int col=temp.getColEmpty();
		  if(lastStep!=move.Left&&col<temp.mat[0].length-1&&temp.mat[row][col+1].getColor()!=0) //step left
		  {
			  countVertices++;
			  cell [][]matLeft=createLeft(temp.mat,row,col);
			  if(isAns(matLeft))
			  {
				  goal=new vertex(matLeft,temp.getCost()+temp.mat[row][col+1].getColor(),move.Right,temp.getPath()+""+matLeft[row][col].getNum()+"L");
			  }
			  else
			  {
				  vertex left=new vertex(matLeft,temp.getCost()+temp.mat[row][col+1].getColor(),move.Right,temp.getPath()+""+matLeft[row][col].getNum()+"L-");
				  queue.add(left);
			  }
		  }
		  if(lastStep!=move.Up&&row<temp.mat.length-1&&temp.mat[row+1][col].getColor()!=0&&goal==null) //step up
		  {
			  countVertices++;
			  cell [][]matUp=createUp(temp.mat,row,col);
			  if(isAns(matUp))
			  {
				  goal=new vertex(matUp,temp.getCost()+temp.mat[row+1][col].getColor(),move.Down,temp.getPath()+""+matUp[row][col].getNum()+"U");
			  }
			  else
			  {
				  vertex up=new vertex(matUp,temp.getCost()+temp.mat[row+1][col].getColor(),move.Down,temp.getPath()+""+matUp[row][col].getNum()+"U-");
				  queue.add(up);  
			  }
		  }
		  if(lastStep!=move.Right&&col>0&&temp.mat[row][col-1].getColor()!=0&&goal==null) //step right
		  {
			  countVertices++;
			  cell [][]matRight=createRight(temp.mat,row,col);
			  if(isAns(matRight))
			  {
				  goal=new vertex(matRight,temp.getCost()+temp.mat[row][col-1].getColor(),move.Left,temp.getPath()+""+matRight[row][col].getNum()+"R");
			  }
			  vertex right=new vertex(matRight,temp.getCost()+temp.mat[row][col-1].getColor(),move.Left,temp.getPath()+""+matRight[row][col].getNum()+"R-");
			  queue.add(right);
		  }
		  if(lastStep!=move.Down&&row>0&&temp.mat[row-1][col].getColor()!=0&&goal==null) //step up
		  {
			  countVertices++;
			  cell [][]matDown=createDown(temp.mat,row,col);
			  if(isAns(matDown))
			  {
				  goal=new vertex(matDown,temp.getCost()+temp.mat[row-1][col].getColor(),move.Up,temp.getPath()+""+matDown[row][col].getNum()+"D"); 
			  }
			  else
			  {
				  vertex down=new vertex(matDown,temp.getCost()+temp.mat[row-1][col].getColor(),move.Up,temp.getPath()+""+matDown[row][col].getNum()+"D-"); 
				  queue.add(down); 
			  }
		  }
	  }
	  return goal;
  }
  /**
   * The function will run dfid algorithm to find the goal node
   * @param start-the node start
   */
  public static vertex dfid(vertex start) 
  {
	  boolean find=false;
	  vertex result=null;
	  for(int i=1;i<1000&&!find;i++)
	  {
//		  countVertices=1;
		  HashMap<String, vertex> verString= new HashMap<String, vertex>();
		  result=dfs(start,i,verString);
		  if(result!=null) 
			  find=true;
	  }
	  if(result!=null&&result.getCost()>=0)
		  return result;
	  else
		  System.out.println("not found");
	  return null;
  }
  /**
   * The function will run dfs limited algorithm to find the goal node
   * @param current- current node 
   * @param depth- the level of the search
   * @param h- hash map that save the open list 
   */
  public static vertex dfs(vertex current,int depth,HashMap<String, vertex> h) 
  {
	  if(isAns(current.mat))
		  return current;
	  if(depth==0)
		  return null;
	  h.put(uniqeString(current.mat),current);
	  boolean isCutOff=false;
	  move lastStep=current.getLastStep();
	  int row=current.getRowEmpty();int col=current.getColEmpty();
	  //for each legal operator of start
	  //operator left
	  if(lastStep!=move.Left&&col<current.mat[0].length-1&&current.mat[row][col+1].getColor()!=0) 
	  {
		  cell [][]matLeft=createLeft(current.mat,row,col);
		  if(!h.containsKey(uniqeString(matLeft)))
		  {
			  vertex left=new vertex(matLeft,current.getCost()+current.mat[row][col+1].getColor(),move.Right,current.getPath()+""+matLeft[row][col].getNum()+"L-");
			  countVertices++;
			  vertex result=dfs(left,depth-1,h);
			  if(result==null)
				  isCutOff=true;
			  else
				  if(result.getCost()>=0)
					  return result;
		  }
	  }
	  //operator up
	  if(lastStep!=move.Up&&row<current.mat.length-1&&current.mat[row+1][col].getColor()!=0) 
	  {
		  cell [][]matUp=createUp(current.mat,row,col);
		  if(!h.containsKey(uniqeString(matUp)))
		  {
			  vertex up=new vertex(matUp,current.getCost()+current.mat[row+1][col].getColor(),move.Down,current.getPath()+""+matUp[row][col].getNum()+"U-");
			  countVertices++;
			  vertex result=dfs(up,depth-1,h);
			  if(result==null)
				  isCutOff=true;
			  else
				  if(result.getCost()>=0)
					  return result;
		  }
	  }
	  //operator right
	  if(lastStep!=move.Right&&col>0&&current.mat[row][col-1].getColor()!=0) 
	  {
		  cell [][]matRight=createRight(current.mat,row,col);
		  if(!h.containsKey(uniqeString(matRight)))
		  {
			  vertex right=new vertex(matRight,current.getCost()+current.mat[row][col-1].getColor(),move.Left,current.getPath()+""+matRight[row][col].getNum()+"R-");
			  countVertices++;
			  vertex result=dfs(right,depth-1,h);
			  if(result==null)
				  isCutOff=true;
			  else
				  if(result.getCost()>=0)
					  return result;
		  }
	  }
	  //operator down
	  if(lastStep!=move.Down&&row>0&&current.mat[row-1][col].getColor()!=0) 
	  {
		  cell [][]matDown=createDown(current.mat,row,col);
		  if(!h.containsKey(uniqeString(matDown)))
		  {
			  vertex down=new vertex(matDown,current.getCost()+current.mat[row-1][col].getColor(),move.Up,current.getPath()+""+matDown[row][col].getNum()+"D-");
			  countVertices++;
			  vertex result=dfs(down,depth-1,h);
			  if(result==null)
				  isCutOff=true;
			  else
				  if(result.getCost()>=0)
					  return result;
		  }
	  }
	  h.remove(uniqeString(current.mat));
	  if(isCutOff)
		  return null;
	  return new vertex(current.mat,-1,current.getLastStep(),current.getPath());
  }

  
  public static vertex AStar(vertex start) 
  { 
	  PriorityQueue<vertex> pq=new PriorityQueue <vertex>(new vertex_Comperator());
	  HashMap<String, vertex> openList= new HashMap<String, vertex>();
	  HashMap<String, vertex> closeList= new HashMap<String, vertex>();
	  pq.add(start);
	  while(!pq.isEmpty())
	  {
		  vertex current=pq.poll();
		  openList.remove(uniqeString(current.mat));
		  if(isAns(current.mat))
			  return current;
		  closeList.put(uniqeString(current.mat), current);
		  move lastStep=current.getLastStep();
		  int row=current.getRowEmpty();int col=current.getColEmpty();
		  //for each legal operator of start
		  //operator left
		  if(lastStep!=move.Left&&col<current.mat[0].length-1&&current.mat[row][col+1].getColor()!=0) 
		  {
			  cell [][]matLeft=createLeft(current.mat,row,col);
			  String keyString=uniqeString(matLeft);
			  if(!closeList.containsKey(keyString)&&!openList.containsKey(keyString))
			  {
				  vertex left=new vertex(matLeft,current.getCost()+current.mat[row][col+1].getColor(),current.getCost()+current.mat[row][col+1].getColor()+heuristicFunction(matLeft),move.Right,current.getPath()+""+matLeft[row][col].getNum()+"L-");
				  countVertices++;
				  pq.add(left);
				  openList.put(keyString, left);
			  }
			  else
				  if(openList.containsKey(keyString)&&openList.get(keyString).getCostH()>current.getCostH())
				  {
					  vertex left=new vertex(matLeft,current.getCost()+current.mat[row][col+1].getColor(),current.getCost()+current.mat[row][col+1].getColor()+heuristicFunction(matLeft),move.Right,current.getPath()+""+matLeft[row][col].getNum()+"L-");
					  openList.replace(keyString, openList.get(keyString), left);
					  pq.remove(openList.get(keyString));
					  pq.add(left);
				  }
		  }
		  //operator up
		  if(lastStep!=move.Up&&row<current.mat.length-1&&current.mat[row+1][col].getColor()!=0) 
		  {
			  cell [][]matUp=createUp(current.mat,row,col);
			  String keyString=uniqeString(matUp);
			  if(!closeList.containsKey(keyString)&&!openList.containsKey(keyString))
			  {
				  vertex up=new vertex(matUp,current.getCost()+current.mat[row+1][col].getColor(),current.getCost()+current.mat[row+1][col].getColor()+heuristicFunction(matUp),move.Down,current.getPath()+""+matUp[row][col].getNum()+"U-");
				  countVertices++;
				  pq.add(up);
				  openList.put(keyString, up);
			  }
			  else
				  if(openList.containsKey(keyString)&&openList.get(keyString).getCostH()>current.getCostH())
				  {
					  vertex up=new vertex(matUp,current.getCost()+current.mat[row+1][col].getColor(),current.getCost()+current.mat[row+1][col].getColor()+heuristicFunction(matUp),move.Down,current.getPath()+""+matUp[row][col].getNum()+"U-");
					  openList.replace(keyString, openList.get(keyString), up);
					  pq.remove(openList.get(keyString));
					  pq.add(up);
				  }

		  }
		  if(lastStep!=move.Right&&col>0&&current.mat[row][col-1].getColor()!=0) //step right
		  {
			  cell [][]matRight=createRight(current.mat,row,col);
			  String keyString=uniqeString(matRight);
			  if(!closeList.containsKey(keyString)&&!openList.containsKey(keyString))
			  {
				  vertex right=new vertex(matRight,current.getCost()+current.mat[row][col-1].getColor(),current.getCost()+current.mat[row][col-1].getColor()+heuristicFunction(matRight),move.Left,current.getPath()+""+matRight[row][col].getNum()+"R-");
				  countVertices++;
				  pq.add(right);
				  openList.put(keyString, right);
			  }
			  else
				  if(openList.containsKey(keyString)&&openList.get(keyString).getCostH()>current.getCostH())
				  {
					  vertex right=new vertex(matRight,current.getCost()+current.mat[row][col-1].getColor(),current.getCost()+current.mat[row][col-1].getColor()+heuristicFunction(matRight),move.Left,current.getPath()+""+matRight[row][col].getNum()+"R-");
					  openList.replace(keyString, openList.get(keyString), right);
					  pq.remove(openList.get(keyString));
					  pq.add(right);
				  }
		  }
		  if(lastStep!=move.Down&&row>0&&current.mat[row-1][col].getColor()!=0) //step down
		  {
			  cell [][]matDown=createDown(current.mat,row,col);
			  String keyString=uniqeString(matDown);
			  if(!closeList.containsKey(keyString)&&!openList.containsKey(keyString))
			  {
				  vertex down=new vertex(matDown,current.getCost()+current.mat[row-1][col].getColor(),current.getCost()+current.mat[row-1][col].getColor()+heuristicFunction(matDown),move.Up,current.getPath()+""+matDown[row][col].getNum()+"D-");
				  countVertices++;
				  pq.add(down);
				  openList.put(keyString, down);
			  }
			  else
				  if(openList.containsKey(keyString)&&openList.get(keyString).getCostH()>current.getCostH())
				  {
					  vertex down=new vertex(matDown,current.getCost()+current.mat[row-1][col].getColor(),current.getCost()+current.mat[row-1][col].getColor()+heuristicFunction(matDown),move.Up,current.getPath()+""+matDown[row][col].getNum()+"D-");
					  openList.replace(keyString, openList.get(keyString), down);
					  pq.remove(openList.get(keyString));
					  pq.add(down);
				  }
		  }
	  }
	  return null;
  }
  public static void main(String[] args)throws Exception 
  { 
	  cell [][] numberCells=readFromFile();
	  switch(algo)
		{//case for algorithm
			case "BFS":
			{
				vertex start=new vertex(numberCells,0,move.None,"");
				long startTime= System.currentTimeMillis();
				vertex result=bfs(start);
				long totalTime= System.currentTimeMillis()-startTime;
				if(result!=null)
				{
					if(!printOpenList) 
						writeToFile(result.getPath(),result.getCost(),totalTime/1000F);
					else
						printToScreen(result.getPath(),result.getCost(),totalTime/1000F);
				}
				else
				{
					if(!printOpenList) 
						writeToFile("",-1,totalTime/1000F);
					else
						printToScreen("",-1,totalTime/1000F);;
				}
				break;
			}
			case "DFID":
			{
				vertex start=new vertex(numberCells,0,move.None,"");	
				long startTime= System.currentTimeMillis();
				vertex result=dfid(start);
				long totalTime= System.currentTimeMillis()-startTime;
				if(result!=null)
				{
					if(!printOpenList) 
						writeToFile(result.getPath().substring(0,result.getPath().length()-1),result.getCost(),totalTime/1000F);
					else
						printToScreen(result.getPath().substring(0,result.getPath().length()-1),result.getCost(),totalTime/1000F);
				}
				else
				{
					if(!printOpenList) 
						writeToFile("",-1,totalTime/1000F);
					else
						printToScreen("",-1,totalTime/1000F);;
				}
				break;
			}
			case "A*":
			{
				vertex start=new vertex(numberCells,0,move.None,"");
				long startTime= System.currentTimeMillis();
				vertex result=AStar(start);
				long totalTime= System.currentTimeMillis()-startTime;
				if(result!=null)
				{
					if(!printOpenList) 
						writeToFile(result.getPath().substring(0,result.getPath().length()-1),result.getCost(),totalTime/1000F);
					else
						printToScreen(result.getPath().substring(0,result.getPath().length()-1),result.getCost(),totalTime/1000F);
				}
				else
				{
					if(!printOpenList) 
						writeToFile("",-1,totalTime/1000F);
					else
						printToScreen("",-1,totalTime/1000F);;
				}
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




//public static void printme(cell[][]mat) 
//{
//	  for(int i=0;i<mat.length;i++) 
//	  {
//		  for(int j=0;j<mat[i].length;j++)
//		  {
//			  System.out.print(mat[i][j].getNum()+",");
//		  }
//		  System.out.println();
//	  }
//	  System.out.println();
//}
