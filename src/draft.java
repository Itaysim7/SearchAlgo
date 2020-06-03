import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class draft 
{
	static boolean printTimeRunning=true;
	static boolean printOpenList=true;
	static int countVertices=1;
	static String algo;
	///////////////input function/////////////////
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
	   * The function will return the solution path for ida*, DFBnb algorithms
	   * @param s- Stack type of vertex 
	   * @return String 
	   */
	  public static String reversePath(Stack<vertex> s) 
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
		 * The function will write or print to the screen the result of the algorithm
		 * @param result- vertex of the answer
		 * @return totalTime- the time that the algorithm solve the problem 
		 */
		public static void printResult(vertex result,long totalTime) 
		{
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
  /**
   * The function will run dfid algorithm to find the goal node
   * @param start-the node start
   */
  public static vertex dfid(vertex start) 
  {
	  boolean find=false;
	  vertex result=null;
	  for(int i=1;i<10000&&!find;i++)
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
	  if(current.b.isAns())
		  return current;
	  if(depth==0)
		  return null;
	  h.put(current.b.uniqeString(),current);
	  boolean isCutOff=false;
	  move lastStep=current.getLastStep();
	  int row=current.b.getRow();int col=current.b.getCol();
	  //for each legal operator of start
	  //operator left
	  if(lastStep!=move.Left&&col<current.b.mat[0].length-1&&current.b.mat[row][col+1].getColor()!=0) 
	  {
		  Board matLeft=current.b.createLeft();
		  if(!h.containsKey(matLeft.uniqeString()))
		  {
			  vertex left=new vertex(matLeft,current.getCost()+current.b.mat[row][col+1].getColor(),move.Right,current.getPath()+""+matLeft.mat[row][col].getNum()+"L-");
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
	  if(lastStep!=move.Up&&row<current.b.mat.length-1&&current.b.mat[row+1][col].getColor()!=0) 
	  {
		  Board matUp=current.b.createUp();
		  if(!h.containsKey(matUp.uniqeString()))
		  {
			  vertex up=new vertex(matUp,current.getCost()+current.b.mat[row+1][col].getColor(),move.Down,current.getPath()+""+matUp.mat[row][col].getNum()+"U-");
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
	  if(lastStep!=move.Right&&col>0&&current.b.mat[row][col-1].getColor()!=0) 
	  {
		  Board matRight=current.b.createRight();
		  if(!h.containsKey(matRight.uniqeString()))
		  {
			  vertex right=new vertex(matRight,current.getCost()+current.b.mat[row][col-1].getColor(),move.Left,current.getPath()+""+matRight.mat[row][col].getNum()+"R-");
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
	  if(lastStep!=move.Down&&row>0&&current.b.mat[row-1][col].getColor()!=0) 
	  {
		 Board matDown=current.b.createDown();
		  if(!h.containsKey(matDown.uniqeString()))
		  {
			  vertex down=new vertex(matDown,current.getCost()+current.b.mat[row-1][col].getColor(),move.Up,current.getPath()+""+matDown.mat[row][col].getNum()+"D-");
			  countVertices++;
			  vertex result=dfs(down,depth-1,h);
			  if(result==null)
				  isCutOff=true;
			  else
				  if(result.getCost()>=0)
					  return result;
		  }
	  }
	  h.remove(current.b.uniqeString());
	  if(isCutOff)
		  return null;
	  return new vertex(current.b,-1,current.getLastStep(),current.getPath());
  }

  
  public static vertex AStar(vertex start) 
  { 
	  PriorityQueue<vertex> pq=new PriorityQueue <vertex>(new vertex_Comperator());
	  pq.add(start);
	  HashMap<String, vertex> openList= new HashMap<String, vertex>();
	  HashMap<String, vertex> closeList= new HashMap<String, vertex>();
	  while(!pq.isEmpty())
	  {
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
			  if(!closeList.containsKey(keyString)&&!openList.containsKey(keyString))
			  {
				  pq.add(op);
				  openList.put(keyString,op);
			  }
			  else
				  if(openList.containsKey(keyString)&&openList.get(keyString).getCostH()>current.getCostH())
				  {
					  openList.replace(keyString, openList.get(keyString), op);
					  pq.remove(openList.get(keyString));
					  pq.add(op);
				  }
		  }
	  }
	  return null;
  }
  public static vertex IDA(vertex start) 
  { 
	  vertex result=null;
	  Stack<vertex> stack=new Stack<vertex>();
	  HashMap<String, vertex> hMap= new HashMap<String, vertex>();
	  int trash=start.getCostH();
	  while(trash<10000)
	  {
		  int minf=Integer.MAX_VALUE;
		  start.setOut(false);
		  stack.push(start);
		  hMap.put(start.b.uniqeString(), start);
		  while(!stack.empty())
		  {
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
					  if(!continueToNextOp) //if this is the goal
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
  
  public static vertex DFBnB(vertex start) 
  { 
	  vertex result=null;
	  Stack<vertex> stack=new Stack<vertex>();stack.push(start);
	  HashMap<String, vertex> hMap= new HashMap<String, vertex>();hMap.put(start.b.uniqeString(), start);
	//  int trash=Integer.MAX_VALUE;
	  int trash=200;
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
  public static void main(String[] args)throws Exception 
  { 
	  cell [][] numberCells=readFromFile();
	  vertex start=new vertex(numberCells,0,move.None,"");
	  switch(algo)
		{//case for algorithm
			case "BFS":
			{
				long startTime= System.currentTimeMillis();
				vertex result=bfs(start);
				long totalTime= System.currentTimeMillis()-startTime;
				printResult(result,totalTime);
				break;
			}
			case "DFID":
			{
				long startTime= System.currentTimeMillis();
				vertex result=dfid(start);
				long totalTime= System.currentTimeMillis()-startTime;
				printResult(result,totalTime);
				break;
			}
			case "A*":
			{
				long startTime= System.currentTimeMillis();
				vertex result=AStar(start);
				long totalTime= System.currentTimeMillis()-startTime;
				printResult(result,totalTime);
				break;
			}
			case "IDA*":
			{
				long startTime= System.currentTimeMillis();
				vertex result=IDA(start);
				long totalTime= System.currentTimeMillis()-startTime;
				printResult(result,totalTime);

				break;
			}
			case "DFBnB":
			{
				long startTime= System.currentTimeMillis();
				vertex result=DFBnB(start);
				long totalTime= System.currentTimeMillis()-startTime;
				printResult(result,totalTime);
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
