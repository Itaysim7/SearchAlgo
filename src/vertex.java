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
	public cell[][] mat;
	private int rowEmpty=0;
	private int colEmpty=0;
	private int cost;
	private move lastStep;
	private String path;
	public vertex(cell [][]temp,int cost,move last,String path)
	{
		lastStep=last;
		mat=new cell[temp.length][temp[0].length];
		this.cost=cost;
		for(int i=0;i<temp.length;i++) //copy the matrix
		{
			for(int j=0;j<temp[i].length;j++)
			{
				if(temp[i][j].getColor()==-1)//the place of the empty cell
				{
					this.rowEmpty=i;
					this.colEmpty=j;
				}
				mat[i][j]=temp[i][j];
			}
		}
		this.path=path;
	}
	public int getRowEmpty() 
	{
		return this.rowEmpty;
	}
	public int getColEmpty() 
	{
		return this.colEmpty;
	}
	public int getCost() 
	{
		return this.cost;
	}
	public move getLastStep() 
	{
		return this.lastStep;
	}
	public String getPath() 
	{
		return this.path;
	}
}