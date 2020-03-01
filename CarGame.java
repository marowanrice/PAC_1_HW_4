// CarGame.java
// Rowan Rice
// October 2, 2019

/* 

Car Game is a program that allows the user to move a car around a 
20 x 20 board.  The car has three attributes: color, ignition state,
and position (x, y).  The color and starting position of the car will
be set randomly at the start of the program; the ignition will always
start in the off position.  Then the program gives the user the 
choice of turning on the ignition, moving the car, or quitting the 
game as the program moves along.

The program catches errors at each menu and when a user enters
a movement that would push the car out of bounds

*/

import java.util.Scanner;
import java.lang.Math;

public class CarGame
{

	/* ~~~~~~~~CONSTANTS~~~~~~~~ */
	public static final char RED = 'R';
	public static final char BLACK = 'B';
	public static final char GREEN = 'G';
	public static final char WHITE = 'W';
	public static final char SILVER = 'S';

	public static final char QUIT_SELECTION = 'Q';
	public static final char IGNITION_SELECTION = 'I';
	public static final char MOVE_SELECTION = 'M';

	public static final char MOVE_VERT = 'V';
	public static final char MOVE_HORIZ = 'H';

	public static final int COLUMNS = 20;
	public static final int ROWS = 20;
	public static final int START = 1;


	/* ~~~~~~~~MAIN METHOD~~~~~~~~ */
	public static void main (String [] args)
	{
		// initialize the car attributes:
		char color = assignColor();
		int xPos = randomizePosition();
		int yPos = randomizePosition();
		boolean ignition = false; //ignition off to start

		// initialize the loop variable
		boolean checkGameOn = true;

		Scanner input = new Scanner(System.in);

		char mainInput; // variable for main menu entry
		char moveInput; // variable for move menu entry
		int moveUnits; // variable for number spaces to move
		boolean checkMvmtDir; // movement menu error check variable

		System.out.println("Welcome to the Car Game!");
		reportState(xPos, yPos, color, ignition);

		while (checkGameOn == true)
		{
			printMainMenu();
			mainInput = input.next().charAt(0);

			switch (mainInput)
			{
				case QUIT_SELECTION:
				{
					checkGameOn = false;
					System.out.println("\nThanks for playing!");
					reportState(xPos, yPos, color, ignition);
					break;
				}

				case IGNITION_SELECTION:
				{
					ignition = ignitionSwitch(ignition);
					reportState(xPos, yPos, color, ignition);
					break;
				}

				case MOVE_SELECTION:
				{
					checkMvmtDir = false;
					while (!checkMvmtDir)
					{
						printMvmtMenu();
						moveInput = input.next().charAt(0);

						switch (moveInput)
						{
							case MOVE_HORIZ:
							{
								System.out.println("Enter a movement distance: ");
								moveUnits = input.nextInt();
								xPos = moveHorizontally(xPos, moveUnits, ignition);
								reportState(xPos, yPos, color, ignition);
								checkMvmtDir = true;
								break;
							}

							case MOVE_VERT:
							{
								System.out.print("\nEnter a movement distance: ");
								moveUnits = input.nextInt();
								yPos = moveVertically(yPos, moveUnits, ignition);
								reportState(xPos, yPos, color, ignition);
								checkMvmtDir = true;
								break;
							}

							default:
							{
								System.out.println("Error: Select a valid direction");
								break;
							}
						}
					}				
					break;
				}

				default: // default in the main game loop (main menu input)
				{
					System.out.println("Error: Incorrect input. Try again.");
					break;
				}
			}

		}

	}

	/* ~~~~~~~~METHODS~~~~~~~~ */

	// this method assigns a random color to the car when it's called at start
	// returns a char (which represents a color)	
	public static char assignColor()
	{
		int numToColor = (int)(Math.random()*5);
		char color;

		switch (numToColor)
		{
			case 0:
				color = RED;
				break;

			case 1:
				color = BLACK;
				break;

			case 2:
				color = GREEN;
				break;

			case 3: 
				color = WHITE;
				break;

			default:
				color = SILVER;
				break;
		}

		return color;
	}

	// this method gives a random number between 1 and 20
	// it's called to give the car a random position at start
	// called for both the horizontal and vertical positions
	public static int randomizePosition()
	{
		return ((int) (Math.random()*20)) + 1;
	}

	// prints the main menu
	public static void printMainMenu()
	{
		System.out.println("\nWhat would you like to do?");
		System.out.println("I: turn the ignition on/off");
		System.out.println("M: move the car");
		System.out.println("Q: quit this program");
		System.out.print("Input: ");
	}

	// flips the ignition switch (off to on or on to off)
	public static boolean ignitionSwitch(boolean currIgn)
	{
		return (!currIgn);
	}

	// prints the movement menu
	public static void printMvmtMenu()
	{
		System.out.println("\nIn which direction do you want to move the car?");
		System.out.println("H: Horizontal");
		System.out.println("V: Vertical");
		System.out.print("Direction: ");
	}

	// moves the car's x-position on the grid
	// only moves if ignition is on
	// if the user entered a value that would move the car out of bounds
	// (either before space 1 or past space 20), then the position is not 
	// updated and the current value is passed back
	public static int moveHorizontally (int xPos, int mvmtInput, boolean ignition)
	{
		int desiredPos = xPos + mvmtInput;

		if (ignition) // checks to see if ignition is on
		{
			if ((desiredPos <= COLUMNS) && (desiredPos >= START))
			{
				xPos = desiredPos; // only updates position if in bounds
			}
			else
			{
				System.out.println("Error: Out of bounds");
			}
		}
		else // if ignition is off, don't update the position
		{
			System.out.println("Error: Turn the ignition on");
		}

		return xPos;
	}

	// moves the car's y-position on the grid
	// only moves if ignition is on
	// if the user entered a value that would move the car out of bounds
	// (either before space 1 or past space 20), then the position is not 
	// updated and the current value is passed back
	public static int moveVertically (int yPos, int mvmtInput, boolean ignition)
	{
		int desiredPos = yPos + mvmtInput;

		if (ignition) // check to see if ignition is on 
		{
			if ((desiredPos <= ROWS) && (desiredPos >= START))
			{
				yPos = desiredPos; // only updates position if in bounds
			}
			else
			{
				System.out.println("Error: Out of bounds");
			}
		}
		else // if ignition is off, don't update the position
		{
			System.out.println("Error: Turn the ignition on");
		}

		return yPos;
	}

	// prints out the grid and the car's position in the grid 
	// arguments: all of the car's characteristics (position, color, ignition)
	// returns: nothing; prints to the screen 
	// calls other methods to translate the color and ignition into words before
	// printing the info (translateColorChar and translateIgnition)
	public static void reportState (int xPos, int yPos, char color, boolean ignition)
	{
			System.out.println("\nCar Information");
			System.out.println("Color: " + translateColorChar(color));
			System.out.println("Ignition: " + translateIgnition(ignition));
			System.out.println("Location: (" + xPos + ", " + yPos + ")\n");

			// this first loop will print all the rows up to (not including)
			// the row that the car is in 
			// uses constants to set dimensions
			for (int rows = START; rows < yPos; rows++)
			{
				for (int cols = START; cols <= COLUMNS; cols++)
				{
					System.out.print("- ");
				}
				System.out.println();
			}

			// second loop prints the row that the car is on up to 
			// (not including) the car position
			for (int carColumn = START; carColumn < xPos; carColumn++)
			{
				System.out.print("- ");
			}

			// print car
			System.out.print(color + " ");

			// third loop prints the the rest of the row that the car is on
			for (int colsRem = START; colsRem <= (COLUMNS - xPos); colsRem++)
			{
				System.out.print("- ");
			}
			System.out.println();

			// fourth loop prints all the rows below the car 
			for (int rowRem = START; rowRem <= (ROWS - yPos); rowRem++)
			{
				for (int cols = START; cols <= COLUMNS; cols++)
				{
					System.out.print("- ");
				}
				System.out.println();
			}
	}

	// translates the char that represents the car's color into a 
	// word to be printed for the user 
	public static String translateColorChar (char color)
	{
		switch (color)
		{
			case RED:
			{
				return "Red";
			}

			case BLACK:
			{	
				return "Black";
			}

			case GREEN:
			{	
				return "Green";
			}

			case WHITE:
			{	
				return "White";
			}

			default:
			{	
				return "Silver";
			}
		}
	}

	// translates the boolean that represents the car's ignition
	// state into a word to be printed for the user
	public static String translateIgnition (boolean ignition)
	{
		if (ignition)
		{
			return "On";
		}

		return "Off";
	}
	
}
