/**
 * Class to implement Stable Matching algorithms
 */

//package StableMatching;

import java.util.*;
import java.io.*;

public class Assignment1 {

	static ArrayList<ArrayList<Integer>> professorGroup = new ArrayList<ArrayList<Integer>>();
	static ArrayList<ArrayList<Integer>> studentGroup = new ArrayList<ArrayList<Integer>>();
	
    private static ArrayList<ArrayList<Integer>> professors_preference;
	private static ArrayList<ArrayList<Integer>> students_preference;

	// Part1: Implement a Brute Force Solution
    public static ArrayList<Integer> stableMatchBruteForce(Preferences preferences) {
		return null;
   }

    // Part2: Implement Gale-Shapley Algorithm
   public static ArrayList<Integer> stableMatchGaleShapley(Preferences preferences) {
	   return null;
   }

    // Part3: Matching with Costs
   public static ArrayList<Cost> stableMatchCosts(Preferences preferences) {
	   return null;
   }
   
   
   public static void main(String[] args) throws FileNotFoundException {
	   
	   // read name of input file from user
	   Scanner console = new Scanner(System.in);
	   System.out.print("Enter name of input file: ");
	   String filename = console.nextLine();
	   System.out.print("You enter: " + filename + "\n");

	   // open the input file and read
	   Scanner input = new Scanner(new File(filename));
	   
	   int numberOfProfessors = input.nextInt(); 
	   int numOfLinesProfPref = numberOfProfessors;
	   System.out.println("number professors " + numberOfProfessors); // for debug
	   
	   int numberOfStudents = input.nextInt(); 
	   int numOfLinesStudentPref = numberOfStudents;
	   System.out.println("number students " + numberOfStudents); // for debug
	   
	   Integer next_ProfPref[] = new Integer[numberOfProfessors];
	   Integer next_StudentPref[] = new Integer[numberOfStudents];
	   
	   while (input.hasNextInt()) { 	   
		   for (int i = 0; i < numOfLinesProfPref; i++ ) {
			   for (int j = 0; j < numberOfProfessors; j++) {
				   int next = input.nextInt();
				   next_ProfPref[j] = next;
//				   System.out.println("next_ProfPref " + next_ProfPref[j]); // for debug
			   }	
			   // add list of student preferences to the list of each professor
			   professorGroup.add(new ArrayList<Integer> (Arrays.asList(next_ProfPref)));
		   }
		   
		   for (int i = 0; i < numOfLinesStudentPref ; i++ ) {
			   for (int j = 0; j < numberOfStudents; j++) {
				   int next = input.nextInt();
				   next_StudentPref[j] = next;
//				   System.out.println("next_StudentPref " + next_StudentPref[j]); // for debug
			   }
			   // add list of teacher preferences to the list of each student 
				studentGroup.add(new ArrayList<Integer> (Arrays.asList(next_StudentPref)));												
		   }		   
		   
	   } // end while loop
	      
	   // call constructor Preferences
	   Preferences preferences = new Preferences(numberOfProfessors, numberOfStudents,
			                                                   professorGroup, studentGroup); 
	
	   ArrayList<ArrayList<Integer>> myListofProffessors_preferences;
	   ArrayList<ArrayList<Integer>> myListofStudents_preferences;	

	   myListofProffessors_preferences = preferences.getProfessors_preference();
	
	   System.out.println("");
	   System.out.println("PREFERENCE LISTS of PROFESSOR");
	   for (ArrayList<Integer> list : myListofProffessors_preferences ) {
		   System.out.println(list);
//		   	for (Integer i : list) {                    // for debug
//		   		System.out.print(i + "Professor -> ");  // for debug
//			}                                           // for debug
//			System.out.println();	                    // for debug
	   }			
			
	   myListofStudents_preferences = preferences.getStudents_preference();
	   System.out.println("");
	   System.out.println("PREFERENCE LISTS of STUDENT");		
	   for (ArrayList<Integer> list : myListofStudents_preferences ) {
			System.out.println(list);
//			for (Integer i : list) {                    // for debug
//				System.out.print(i + "Student -> ");    // for debug
//			}                                           // for debug
//			System.out.println();	                    // for debug
		}			
	
	}
}
   