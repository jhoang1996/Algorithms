package assignment2;

import java.util.*;

public class Algorithms_Assignment2 {

	public static void main(String[] args) {
		
		System.out.println("Enter the number of rows");
		Scanner console = new Scanner(System.in);
		int row = console.nextInt();
		
		System.out.println("Enter the number of columns");
		int column = console.nextInt();			
				
		System.out.println("Enter the map matrix (enter each row: hit return after each row)");		
		int map[][] = new int[row][column];
		for (int i = 0; i < row;  i++)
			for (int j = 0; j < column; j++)
				map[i][j] = console.nextInt();
			
		
		ArrayList<Airdrop> airdrops = Assignment2.findProgram(map, row, column);
										
	}
	
}
