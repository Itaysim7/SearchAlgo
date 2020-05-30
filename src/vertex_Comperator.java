
import java.util.Comparator;

public class vertex_Comperator implements Comparator<vertex> 
{
	public vertex_Comperator() {;}
	@Override
	public int compare(vertex o1, vertex o2) 
	{
		int dp = o1.getCostH() - o2.getCostH();
		return dp;
	}
}