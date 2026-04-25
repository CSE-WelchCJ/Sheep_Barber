/*
 * Written by Chris Welch CSCE 146-007
 */

import java.io.File;
import java.util.*;
public class SheepShearingSimulator {
	
	// Declare objects for use throughout
	public static Scanner keyboard = new Scanner(System.in);
	public static SheepScheduler s = new SheepScheduler();

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		printGreeting();
		do
		{
			// if file fails to be read, skips running sim and printing result
			if(readFile())
			{
				runSim();
				printSchedule();
			}
		}while(goAgain());
		System.out.println("Exiting...");
		System.exit(0);
	} // close main
	
	// method for greeting
	public static void printGreeting()
	{
		System.out.print("Welcome to the sheep shearing simulator and schedule printer\n");
	}
	
	// method for fileRead.  Takes in user input for filename, checks for existence, then forwards filename to next method
	public static boolean readFile()
	{
		System.out.println("Please enter the filename to be read into memory: ");
		String fileName = keyboard.nextLine();
		if(fileName.length() == 0)
		{
			System.out.println("No entry was made, returning to menu.");
			return false;
		}
		File f = new File(fileName);
		if(f.exists())
		{
			s.readFile(fileName);
			System.out.println("File loaded.");
			return true;
		}
		else
		{
			System.out.println("The filename you entered does not exist in the project folder.");
			return false;
		}
	}
	
	// method to print final data
	public static void printSchedule()
	{
		s.printSchedule();
	}

	// method to run sim clock loop
	public static void runSim()
	{
		s.runSim();
	}
	
	// Method to determine if user wants to repeat the program
	public static boolean goAgain()
	{
		System.out.println("\nEnter \'y\' to load a new sheep shearing file.");
		String response = keyboard.nextLine();
		if(response.equalsIgnoreCase("y"))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
} // close class
