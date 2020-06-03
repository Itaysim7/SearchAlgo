
import java.util.Comparator;

public class vertex_Comperator implements Comparator<vertex> 
{
	public vertex_Comperator() {;}
	@Override
	public int compare(vertex o1, vertex o2) 
	{
		int dp = o1.getCostH() - o2.getCostH();
		if(dp!=0)
			return dp;
		else
			return (int)(o1.getTime() - o2.getTime());
	}
}