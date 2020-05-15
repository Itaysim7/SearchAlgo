/**
 * This class represents a cell in matrix with two fields  num, color.
 * num represent the number in the cell and the color represent the color of the cell
 * 0 for green,1 for black and 2 for red. 
 * @author Itay Simhayev
 */
public class cell 
{
	private int color;
	private int num;
	public cell(int col,int n) 
	{
		this.color=col;
		this.num=n;
	}
	public int getColor()
	{
		return this.color;
	}
	public int getNum()
	{
		return this.num;
	}
}