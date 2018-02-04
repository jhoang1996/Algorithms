 /*
 * Class to implement Stable Matching algorithms.
 */

//Justin Hoang
//jah7399

import java.util.*;

public class Assignment1 {

	private static int N;
	private static int matchedCount;
	private static Integer[] studentAndMatchedProfessor;  // Array to keep track students and their matched professors
	private static boolean[] professorMatched;  // Used to indicate whether the professor has been matched
	
	// Part1: Implement a Brute Force Solution
    public static ArrayList<Integer> stableMatchBruteForce(Preferences preferences) {
    	
	   	System.out.println("\n ----- Brute Force Solution -----");
	   	
    	ArrayList<Integer> professorsStableMatch = new ArrayList<Integer>();
    	ArrayList<ArrayList<Integer>> professorPreferences = preferences.getProfessors_preference();
    	ArrayList<ArrayList<Integer>> studentPreferences = preferences.getStudents_preference();
    	
    	for(int i=0;i<preferences.getNumberOfProfessors();i++)
    	{
    		professorsStableMatch.add(-1);
    		
			for(int j=0;j<preferences.getNumberOfStudents();j++)
    		{
				int studentNum = (professorPreferences.get(i).get(j)) - 1;
				if(studentPreferences.get(studentNum).get(0) == (i+1))
	    		{
	    			professorsStableMatch.set(i,(studentNum+1));
	    			break;
	    		}
    			
    		}
    	}
    	System.out.println("\nStable Match Brute Force: " + professorsStableMatch);
     
		return professorsStableMatch;
   }

    
    //=================================================================================    
    // This function is used to check if a student prefers the available professor over
    // his currently assigned professor
 	private static boolean morePreference(Integer currentMatchedProfessor, int freeProfessor, 
 			int studentIndex, Preferences preference ) {
 		
 		// Loop through the student's list of preferred professor
 		for (int i = 0; i < N; i++) {
 			
 			// Get the professor
 			int preferredProfessor = preference.getStudents_preference().get(studentIndex).get(i);
 			
 			// If the free professor is more preferred
 			if (preferredProfessor == freeProfessor)
 				return true;
 			
 			// If the currently matched professor is more preferred
 			if (preferredProfessor == (currentMatchedProfessor))
 				return false;
 		}
 		return false;				
 	}

    //=================================================================================     	
 	// Get professor index
 	private static int profIndexOf(Integer currentPref){
 		for (int i = 0; i < N; i++)
 			if ((i+1) == currentPref)
 				return i;
 		return -1;
 	}

    //=================================================================================    
 	// get student index
 	private static int studentIndexOf(Integer integer) {
 		for (int i = 0; i < N; i++)
 			if ((i+1) == (integer))
 				return i;
 		return -1;		
 	}
 	   
 	//=================================================================================
 	// Part2: Implement Gale-Shapley Algorithm

 	public static ArrayList<Integer> stableMatchGaleShapley(Preferences preferences) {
	   
	   	System.out.println("\n ----- Gale-Shapley method -----");
	   	ArrayList<Integer> profStudentPref = new ArrayList<Integer>();
	   	
	   	N = preferences.getNumberOfProfessors();
		matchedCount = 0;
		professorMatched = new boolean[N];
		studentAndMatchedProfessor = new Integer[N];
				
		// Loop until all professors have found matches
		while (matchedCount < N) {
		 	int free;
			
			// Find the first professor that has not been matched
			for (free = 0; free < N; free++)
				if (!professorMatched[free]) 
					{ 
						break;
					}
			
			// Actual professor = index + 1
			int freeProfessor = free + 1;
			
			// Loop through the students
			for (int i = 0; i < N && !professorMatched[free]; i++) {
				 			
				// Get free professor's preferred student
				int student = preferences.getProfessors_preference().get(free).get(i); 
				
				// Get the index of the current professor's preferred student
				int studentIndex = studentIndexOf(student);				

				
				// If the student has not been matched to a professor yet
				if (studentAndMatchedProfessor[studentIndex] == null)
				{
					// Assign free professor to current free student
					studentAndMatchedProfessor[studentIndex] = freeProfessor;
					professorMatched[free] = true;
					matchedCount++;
				}
				else {
					
					// Get professor ID that has already been matched to current student
					Integer currentMatchedProfessor = studentAndMatchedProfessor[studentIndex];
										
					// See if the student prefers the free professor better than the matched one.
					if (morePreference(currentMatchedProfessor, freeProfessor, studentIndex, preferences))
					{
						// Assign the more preferred professor to the student
						studentAndMatchedProfessor[studentIndex] = freeProfessor;
						
						// The current free professor is now matched
						professorMatched[free] = true;
						
						// The previously matched professor is no longer matched
						professorMatched[profIndexOf(currentMatchedProfessor)] = false;
					}
				}
			}			
		}
	
		// Initialize array of professor-student 
		for (int i = 0; i < N; i++) {
			profStudentPref.add(-1);		
		}
		
		// Loop through the list of student-professor matches
		for (int studentIdx = 0; studentIdx < N; studentIdx++) {
			
			// Get matched professor index of student i		
			int professorIdx = profIndexOf(studentAndMatchedProfessor[studentIdx]);
			
			// Assign the student index to the professor
			profStudentPref.set(professorIdx, studentIdx);
		}
	   
	  System.out.println("\nStable Match Gale-Shapley: "+ profStudentPref);
	  return profStudentPref;	   
   }
   
    public static ArrayList<Integer> stableMatchGaleShapleyStudents(Preferences preferences) {
 	   
 	   System.out.println("\n ----- Gale-Shapley method -----");	   
 	   ArrayList<Integer> studentsProfMatch = new ArrayList<Integer>();
 	   int currPref;
 	   for(int i = 0;i<preferences.getNumberOfStudents();i++){
 		   currPref = (preferences.getStudents_preference().get(i).get(0)-1);
 		   if(!studentsProfMatch.contains(currPref))
 			   studentsProfMatch.add(currPref);
 		   else
 		   {
 			   for(int j = 0;j<preferences.getNumberOfProfessors();j++)
 			   {
 				   currPref = (preferences.getStudents_preference().get(i).get(j)-1);
 				   if(!studentsProfMatch.contains(currPref))
 				   {
 					   studentsProfMatch.add(currPref);
 					   continue;
 				   }
 			   }
 		   }
 	   }
 	   System.out.println("\nStudent Stable Match Gale-Shapley: "+ studentsProfMatch);
 	   return studentsProfMatch;
    }
 	

//============================================================
    // Part3: Matching with Costs
   public static ArrayList<Cost> stableMatchCosts (Preferences preferences) {
	   
	   System.out.println("\n\n ----- Professor Optimal -----");
	   ArrayList<Integer> profStudentsMatch = new ArrayList<Integer>();
	   profStudentsMatch = stableMatchGaleShapley(preferences);
	   ArrayList<Cost> costArr = new ArrayList<Cost>();
	   Cost cost;
	   int curPref;
	   
	   for(int i = 0 ; i < preferences.getNumberOfProfessors(); i++)
	   {
		   int profIndex = 0, studentIndex = 0;
		   curPref = profStudentsMatch.get(i);
		   
		   // matched student & professor
		   int student = profStudentsMatch.get(i) + 1;   // student index + 1
		   int professor = i + 1;  // professor index + 1
		   
		   // Get where student is ranked on professor's list  	   
		   while(preferences.getProfessors_preference().get(i).get(profIndex) != student)		   
		   {
			   profIndex++;
		   }
		
		   // Get where professor is ranked on student's list
		   while(preferences.getStudents_preference().get(curPref).get(studentIndex) != professor)
		   {
			   studentIndex++;
		   }
				   
		  cost = new Cost(i, profStudentsMatch.get(i), profIndex , studentIndex);
		  costArr.add(cost);
		   
	   }
	   System.out.println("Professor Optimal");
	   for (Cost costItem : costArr) {
		   System.out.println(costItem.getIndexOfProfessor()+", "+costItem.getIndexOfStudent()+", "+costItem.getCostToProfessor()+", "+costItem.getCostToStudent());
	   }
	   return costArr;
   }
  
   public static ArrayList<Cost> stableMatchCostsStudent (Preferences preferences) {
	   
	   System.out.println("\n\n ----- Student Optimal -----");
	   
	   ArrayList<Integer> profStudentsMatch = new ArrayList<Integer>();
	   profStudentsMatch = stableMatchGaleShapleyStudents(preferences);
	   ArrayList<Cost> costArr = new ArrayList<Cost>();
	   Cost cost;
	   int curPref;
	   
	   for(int i = 0 ;i<preferences.getNumberOfStudents(); i++)
	   {
		   int profIndex = 0, studentIndex = 0;
		   curPref = profStudentsMatch.get(i);
		   
		   while(preferences.getStudents_preference().get(i).get(studentIndex) != (curPref+1))
		   {
			   studentIndex++;
		   }
		   
		   while(preferences.getProfessors_preference().get(curPref).get(profIndex) != (i+1))
		   {
			   profIndex++;
		   }
		   
		  cost = new Cost( curPref, i, profIndex, studentIndex);
		  costArr.add(cost);
		   
	   }
	   System.out.println("Student Optimal");
	   for (Cost costItem : costArr) {
		   System.out.println(costItem.getIndexOfStudent()+", "+costItem.getIndexOfProfessor()+", "+costItem.getCostToStudent()+", "+costItem.getCostToProfessor());
	   }
	   return costArr;
   }
          
}
