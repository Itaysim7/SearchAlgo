/**
 * This class represents a board game the board represent by matrix ,row,col 
 * matrix type of cell, row and col represent the place in the matrix of the empty cell
 * @author Itay Simhayev
 */
public class Board 
{
	public cell[][] mat;
	private int row;
	private int col;
	public Board(cell [][]temp)
	{
		mat=new cell[temp.length][temp[0].length];
		for(int i=0;i<temp.length;i++) //copy the matrix
		{
			for(int j=0;j<temp[i].length;j++)
			{
				if(temp[i][j].getColor()==-1)//the place of the empty cell
				{
					this.row=i;
					this.col=j;
				}
				mat[i][j]=temp[i][j];
			}
		}
	}
	public Board copy()
	{
		Board newB=new Board(mat);
		return newB;
	}
	public int getRow() 
	{
		return this.row;
	}
	public int getCol() 
	{
		return this.col;
	}
	 /**
	   * The function will create left matrix
	   * @param mat- the matrix of the curtain node 
	   */
	  public Board createLeft() 
	  {
		  cell [][]matLeft=copyMat(mat);
		  cell tempCell=matLeft[row][col];
		  matLeft[row][col]=matLeft[row][col+1];
		  matLeft[row][col+1]=tempCell;
		  Board left=new Board(matLeft);
		  return left;
	  }
	  /**
	   * The function will create up matrix
	   * @param mat- the matrix of the curtain node 
	   */
	  public Board createUp() 
	  {
		  cell [][]matUp=copyMat(mat);
		  cell tempCell=matUp[row][col];
		  matUp[row][col]=matUp[row+1][col];
		  matUp[row+1][col]=tempCell;
		  Board up=new Board(matUp);
		  return up;
	  }
	  /**
	   * The function will create right matrix
	   * @param mat- the matrix of the curtain node 
	   */
	  public Board createRight() 
	  {
		  cell [][]matRight=copyMat(mat);
		  cell tempCell=matRight[row][col];
		  matRight[row][col]=matRight[row][col-1];
		  matRight[row][col-1]=tempCell;
		  Board right=new Board(matRight);
		  return right;
	  }
	  /**
	   * The function will create down matrix
	   * @param mat- the matrix of the curtain node 
	   */
	  public Board createDown() 
	  {
		  cell [][]matDown=copyMat(mat);
		  cell tempCell=matDown[row][col];
		  matDown[row][col]=matDown[row-1][col];
		  matDown[row-1][col]=tempCell;
		  Board down=new Board(matDown);
		  return down;
	  }
	  /**
	   * The function will create unique String of the board by the value in the cell
	   * @return unique String
	   */
	  public String uniqeString() 
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
	  /**
	   * The function will return discrepancy between the goal mat and the current mat
	   * @param mat- matrix of the current node 
	   * @return discrepancy between the state
	   */
	  public int heuristicFunction() 
	  {
		  int count=0;
		  int rowPlace=0;int colPlace=0;
		  for(int i=0;i<mat.length;i++)
		  {
			  for(int j=0;j<mat[i].length;j++)
			  {
				  if(mat[i][j].getNum()!=0)//if this is the empty block 
				  {
					  rowPlace=(mat[i][j].getNum()-1)/mat[i].length;
					  colPlace=(mat[i][j].getNum()-1)%mat[i].length;
					  count=count+((Math.abs(rowPlace-i))+(Math.abs(colPlace-j)))*mat[i][j].getColor();
				  }
			  }
		  }
		  return count;
	  }
	  /**
	   * The function will return if the node is the goal node
	   * @param mat- matrix of the node 
	   */
	  public boolean isAns() 
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
	  //////////////////////////private///////////////////
	  /**
	   * The function will copy the matrix
	   * @param mat- matrix of the node 
	   */
	  private  cell[][] copyMat(cell[][]mat) 
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
	  
}