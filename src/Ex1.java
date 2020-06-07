import java.io.*;

public class Ex1 
{
	static boolean printTimeRunning=true;
	static boolean printOpenList=true;
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
			  if(cells.charAt(0)==' ')
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
///////////////////////////////////////////////////////////////////////////
	  /**
	   * The function will write to a file  the output of the algorithm
	   * @param path - path from the node start to the goal start
	   * @param cost - the cost from the node start to the goal 
	   * @param time - time for the algorithm to find the goal node
	   */	  
  public static void writeToFile(String path,int cost,float time,int countVertices)
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
	 * The function will write or print to the screen the result of the algorithm
	 * @param result- vertex of the answer
	 * @return totalTime- the time that the algorithm solve the problem 
	 */
	public static void printResult(vertex result,long totalTime,int countVertices) 
	{
		if(result!=null)
			writeToFile(result.getPath().substring(0,result.getPath().length()-1),result.getCost(),totalTime/1000F,countVertices);
		else
			writeToFile("",-1,totalTime/1000F,countVertices);
	}
	/**
	 * The function will check if there is black cell not in his place 
	 * @param start- the start state of the vertex
	 * @return false if there is no solution
	 */
	public static boolean earlyCheck(cell [][] start) 
	{
		 for(int i=0;i<start.length;i++)
		  {
			  for(int j=0;j<start[i].length;j++)
			  {
				  if(start[i][j].getColor()==0&&start[i][j].getNum()!=i*start[i].length+j+1)
					  return false;
			  }
		  }
		 return true;
	}

  public static void main(String[] args)throws Exception 
  { 
	  cell [][] numberCells=readFromFile();
	  if(earlyCheck(numberCells))//if there is no solution don't continue
	  {
		  vertex start=new vertex(numberCells,0,move.None,"");
		  switch(algo)
			{//case for algorithm
				case "BFS":
				{
					long startTime= System.currentTimeMillis();
					SearchAlgorithm bfs=new BFS(start,printOpenList);
					vertex result=bfs.doAlgo();
					long totalTime= System.currentTimeMillis()-startTime;
					printResult(result,totalTime,bfs.getCountVertices());
					break;
				}
				case "DFID":
				{
					long startTime= System.currentTimeMillis();
					SearchAlgorithm dfid=new DFID(start,printOpenList);
					vertex result=dfid.doAlgo();
					long totalTime= System.currentTimeMillis()-startTime;
					printResult(result,totalTime,dfid.getCountVertices());
					break;
				}
				case "A*":
				{
					long startTime= System.currentTimeMillis();
					SearchAlgorithm a=new ASTAR(start,printOpenList);
					vertex result=a.doAlgo();
					long totalTime= System.currentTimeMillis()-startTime;
					printResult(result,totalTime,a.getCountVertices());
					break;
				}
				case "IDA*":
				{
					long startTime= System.currentTimeMillis();
					SearchAlgorithm ida=new IDA(start,printOpenList);
					vertex result=ida.doAlgo();
					long totalTime= System.currentTimeMillis()-startTime;
					printResult(result,totalTime,ida.getCountVertices());

					break;
				}
				case "DFBnB":
				{
					long startTime= System.currentTimeMillis();
					SearchAlgorithm dfbnb=new DFBnB(start,printOpenList);
					vertex result=dfbnb.doAlgo();
					long totalTime= System.currentTimeMillis()-startTime;
					printResult(result,totalTime,dfbnb.getCountVertices());
					break;
				}
				default :
				{
					throw new RuntimeException("There is not such algorithm");
				}
			} 
	  }
	  else
			printResult(null,0,1);


  } 
} 
