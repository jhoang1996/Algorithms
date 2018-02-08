import java.util.ArrayList;

/**
 * Header
 * <Justin Hoang>
 * <jah7399>
 * <16500>
 * Please fill inside < >  and do not remove < >.
 */

public class Assignment3 {

	private int totalClasses;
	private int maxGrade;
	private static int [][]gradearray;
	public Assignment3() {
		this.totalClasses = 0;
		this.maxGrade = 0;
		this.gradearray = null;
	}

	public void initialize(int totalClasses, int maxGrade,  int[][] gradearray) {//These varaibles are set from TestAssignment3.java
		this.totalClasses = totalClasses;
		this.maxGrade = maxGrade;
		this.gradearray = gradearray;
	}


	public result[] compute(int totalHours, result[] studentoutput) {
		
		// Assuming the grade array has same number of columns. 
		// Get the column size of first row.
		int numHourColumns = gradearray[0].length;
		
		int[] bestNumHoursToSpent = new int[totalClasses];
		
		// best array to hold the best accumulated grades for the
		// the hours spent on the classes
		int [][] bestGrade = new int[totalClasses][numHourColumns];
		
		// Initial values for first class (class 0)
		int class0MaxGrade = 0;		
		bestNumHoursToSpent[0] = 0;
		
		// Initialize the first row of cumulative best grade array
		// to class 0 grades
		for (int hour = 0; hour < numHourColumns; hour++)
		{
			bestGrade[0][hour] = gradearray[0][hour];
			if (gradearray[0][hour] > class0MaxGrade)
			{
				class0MaxGrade = gradearray[0][hour];
				bestNumHoursToSpent[0] = hour;
			}
		}
		
		// Initialize the rest of bestGrade array
		for (int c = 1;  c < totalClasses; c++)
		{
			for (int hour = 0; hour < numHourColumns; hour++) 
			{
				bestGrade[c][hour] = -1;
			}
		}

		for (int c = 1;  c < totalClasses; c++)	
		{
			for (int hour = 0; hour < numHourColumns; hour++) 
			{
				int maxGrade = 0;
				
				int bestHours = 0;
				for (int h = 0; h <= hour; h++)
				{
					int grade = bestGrade[c-1][hour-h] + gradearray[c][h];
					
					if (grade > maxGrade){
						bestHours = h;
						maxGrade = grade;
					}
				}	
				
				bestNumHoursToSpent[c] = bestHours;
				bestGrade[c][hour] = maxGrade;
			}
		}
		
		int totalHoursSpent = 0;
		
		// Add up number of hours spent
		for (int c = 0; c < totalClasses; c++)
		{
			totalHoursSpent = totalHoursSpent + bestNumHoursToSpent[c];
		}

		// Compute number of hours not spent yet.
		int hoursLeft = totalHours - totalHoursSpent;
		
		// Loop through each hour left, until none left
		while (hoursLeft > 0)
		{
			int hour = hoursLeft;
			
			if (hour > numHourColumns) 
			{
				hour = numHourColumns - 1;
			}
			
			int classNo = 0;
			
			int maxGradeGained = 0;
			
			// find the biggest delta
			for (int c = 0; c < totalClasses; c++)
			{
				int gradeAtHour = gradearray[c][hour];
				
				int calculatedHour = bestNumHoursToSpent[c];
				
				int gradeAtCalculatedHour = gradearray[c][calculatedHour];
				
				int gradeGained = gradeAtHour - gradeAtCalculatedHour;
				
				if (gradeGained > maxGradeGained) {
					maxGradeGained = gradearray[c][hour];
					classNo = c;
				}
			}
			
			// Update the array with better value
			bestNumHoursToSpent[classNo] = hour;
			
			hoursLeft = hoursLeft - hour;
		}
		
		// Update output
		for (int c = 0;  c < totalClasses; c++)	
		{
			int bestHour = bestNumHoursToSpent[c];
			int bestScore = gradearray[c][bestHour];
					
			studentoutput[c].setHour(bestHour);
			studentoutput[c].setGrade(bestScore);
						
		}
		return studentoutput;
	}

}


/*  WRITE YOUR REPORT INSIDE THIS SECTION AS COMMENTED CODE
 * 

We solve the problem by breaking it into sub-problems.   The grade matrix contains the
grade the student will receive, based on the number of hours he spends studying for
each exam.  Using this matrix, we will find the best cumulative score the student can
achieve by spending J hours studying for first N exams.

We create a temporary 2-dimensional array Best[TC][TH], with TC is total number of
classes and TH is the hours. We will store the best cumulative scores in this array.

Let Best[N][J] represent the best cumulative score the student can achieve spending 
J hours on first N classes.  We loop through each class (row) and each hour (column) of
the grade matrix to compute the values for Best.  We calculate Best[N][J] by looking 
at Best[N-1][K] for each value of K.  We will also keep track K value that gives the 
best score for each class (row).   


The last value of array Best (Best[TC][TH]) represents the best score for studying all TC 
exams in TH hours.

We add up all K values saved for each class above and that is the number of hours spent
to get the above best score. 

But these two  values are based on the grade matrix, which limits the max number of hours 
can be spent on each class is 9 hours (in our test cases).    The above computation doesn’t
factor in the number of hours the student can have (20 hours total).    Subtracting the
sum of all saved Ks from 20 will give the number of hours the student still have left.  With 
these extra hours we then go back to our Best array and determine which class the student
can spend these extra hours on to give him the highest gain in score. We update the
result to reflect that.

 */
