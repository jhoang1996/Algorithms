/**
 * Header
 * <firstName surName>
 * <eid>
 * <uniqueId> (Your section id)
 * Please fill inside < >  and do not remove < >.
 */

/**
 * Class to implement Assignment2 solution
 * findProgram method should be implemented.
 * Please do not include any main methods.
 */
package assignment2;

import java.util.*;

public class Assignment2 implements Comparator<Airdrop>{
	static int currentDepth=0, sum=0;
	static ArrayList<Airdrop> airdrops = new ArrayList<Airdrop>();
	static Airdrop airDrop;
	
	public int compare(Airdrop a, Airdrop b) {
	     int c1 = (new Integer(a.getRow()+a.getColumn())).compareTo((b.getRow()+b.getColumn()));
	     return c1 == 0 ? new Integer(a.getRow()).compareTo(b.getRow()) : c1;
	 }
	
	
	// Implement this function
	public static ArrayList<Airdrop> findProgram(int[][] map, int row, int column) {
		//ArrayList<Airdrop> airdrops = new ArrayList<Airdrop>();
		boolean[][] visitedBoolArr  = new boolean[row][column];
		for(int i=0;i<row;i++)
		{
			for(int j=0;j<column;j++)
			{
				if(map[i][j]>0 && visitedBoolArr[i][j] == false){
					currentDepth=0;
					sum=0;
					int size = getRegionSize(map,visitedBoolArr, i, j);
					airDrop = new Airdrop(i,j,sum);
					if(airdrops.contains(airDrop) == false){
						airdrops.add(airDrop);
					}
				}
			}
		}

		Collections.sort(airdrops, new Assignment2());
		
		for (Airdrop airDrop : airdrops) {
			System.out.println("Airdrop at Region(" + airDrop.getRow()+","+airDrop.getColumn()+") with size "+airDrop.getSize());
		}
		return airdrops; 
	}
	
	public static int getRegionSize(int[][] n, boolean[][] bool, int i, int j)
	{
		if (i >= 0 && i < n.length && j >= 0 && j < n[0].length
				&& bool[i][j] != true && n[i][j] != 0) {
			currentDepth++;
			sum+=n[i][j];
			// Mark the status of the cell for backtracking purpose
			bool[i][j] = true;

			// left traversal
			getRegionSize(n, bool, i - 1, j);
			// right traversal
			getRegionSize(n, bool, i + 1, j);

			// top traversal
			getRegionSize(n, bool, i, j - 1);
			// bottom traversal
			getRegionSize(n, bool, i, j + 1);		
	}	
	return currentDepth;

	}
		
}
