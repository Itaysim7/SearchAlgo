
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class task3 {

    public static void main(String[] args) 
    {
    	List<Integer> example = new ArrayList<Integer>();

    	Scanner myObj = new Scanner(System.in);  // Create a Scanner object
		System.out.println("Enter number of grades you will insert");
    	int numOfGrades=myObj.nextInt();
    	for(int i=0;i<numOfGrades;i++)
    	{
    		System.out.println("Enter grade number "+(i+1));
        	int grade=myObj.nextInt();
    		example.add(grade);
    	}
		example.stream().filter(num -> num >=60&&num<=100).map(num->(100-num)).forEach(System.out::println);
    }
}

