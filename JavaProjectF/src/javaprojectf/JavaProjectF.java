/**
 * Programmer Name: Rayaan Khan
 * Date: 2020-10-14
 * Project: JavaProjectF.java
 * Program Name: Slot Machine Game
 */

package javaprojectf;
import java.io.*;
import java.util.Scanner;
import java.util.Random;
import static java.lang.System.out; // shorten output statement

public class JavaProjectF {
        
    static public Scanner scanS = new Scanner(System.in); // String Scanner
    static public Random randomNumbers = new Random();  // Global Num Generator
    
    public static void main(String[] args) throws IOException, 
            InterruptedException {
        
        String[] names = {"","","","","","","","","",""}; // store names array
        int[] tokens = new int[10]; // stores tokens into parallel array with ^

        boolean trapOne; // error Trap
        boolean newUser = false; // decide which if statement to use
        boolean gameStarted; // used too break once game ends
        String playerName; // store players name
        int moveNum = 0; // move trough textFile and save index for other uses

        // open text file to read
        File file = new File("SlotScores.txt");

        // open file to read from
        File myFile = new File(file.getAbsolutePath());
        Scanner readFile = new Scanner(myFile);    
        
        System.out.println(myFile);
        
        // Introduction
        out.println("welcome to the slot machine! Are you a"
                + "\n\n1) Returning User"
                + "\n2) New user");
        
        do { // error trap loop
            
            out.print("\nEnter Option: ");
            String UserType = scanS.nextLine();
        
            trapOne = false; // error trap
            switch (UserType.toLowerCase()) {
                case "1":
                case "returning user":
                    newUser = false; // returning user for if statement
                    break;
                case "2":
                case "new user":
                    newUser = true; // new User makes true for if statement
                    break;
                default:
                    out.println("Invalid Input try again");
                    trapOne = true; // loops again for re-entry
                    break;
            }
        } while (trapOne == true);
        
        if (newUser == true) {
            
            while (readFile.hasNext()) {// While file has a new line do:
                String moveLine = readFile.nextLine(); // move to next line
                String[] namePart = moveLine.split(", ");// split string into 2
                names[moveNum] = namePart[0]; // store names
                tokens[moveNum] =  Integer.parseInt(namePart[1]);// token values              
                
                if ("".equals(names[moveNum])) // If name array is empty, break
                    break;
                moveNum++; // add one
                
                if (moveNum == 10) { // if an account limit is reached
                    out.println("Unfortunetly we have reached the max"
                            + "users possible, try again later");
                    readFile.close(); // close file to avoid any errors
                    System.exit(0); // shuts down program
                }
            }
            
            out.print("\nPlease enter your name: ");
            names[moveNum] = scanS.nextLine().toLowerCase(); // name in lowercase
            tokens[moveNum] = 50; // give new user 50 tokens
            slotGame(names, tokens, moveNum); // calls method to play game
        } // if conditional ends
        
        else {
                     
            while (readFile.hasNext()) {// While file has a new line do:
                String line = readFile.nextLine(); // Read the nextLine  
                String[] splitingN = line.split(", "); // split ln into two parts
                names[moveNum] = splitingN[0];  // appends name to array
                tokens[moveNum] = Integer.parseInt(splitingN[1]); // token to array
                
                if ("".equals(names[moveNum])) // If name array is empty, break
                    break;   
                
                out.println(" " + (moveNum+1) + ")" + splitingN[0]); 
                // Prints out the name in the .txt file ^^
                moveNum++; // add one  
            }
            
            do { // error Trap loop
                
                gameStarted = false; // for logic
                out.println("\nPlease select an account");
                playerName = scanS.nextLine().toLowerCase(); // player input
                
                /*
                for-loop uses sequential search too compare the playerName
                aka the key with the names in the array, and starts the game if 
                found and if not, it restarts the loop.
                */
                for (int i = 0; i < names.length; i++) {
                    
                    if (names[i].equals(playerName)) {
                        moveNum = i; // sets moveNum = i to match index value                                      
                        out.println("starting game!");
                        slotGame(names, tokens, moveNum); // call game
                        gameStarted = true; // to help with prinitng if no match                                     
                    }
                } 
                
                // if game did not start means error and loops to beginning Q.
                if (gameStarted == false)  
                    out.println("name did not match let's try that again");                   
            } while (gameStarted == false);                         
        } //  else conditonal ends
            
        // opening up file to update with new arrays
        File fileRead = new File("SlotScores.txt");

        // finds the file directy that String DestinationFile is at
        FileWriter fWriter = new FileWriter(file.getAbsolutePath());
        PrintWriter outputFile = new PrintWriter(fWriter);
        
        for (int printing = 0; printing < names.length; printing++) {
            outputFile.println(names[printing] + ", " + tokens[printing]);        
        } // cycles through the arrays from beginning to print updated info
        
        readFile.close(); // closing files from input
        outputFile.close(); // closing files from outputting (saves SlotScores)
    }
    
    /**
     * Method Name: slotGame
     * Description: Runs the slot game, & stores new info into array
     * @param names  //array where names are stored   ↓  (parallel)↓
     * @param tokens  //array where tokens are stored ^ (parallel) ^
     * @param indexValue  //originally moveNum, keep track of next blank value
     * @throws InterruptedException  //to add time delays 
     */
    public static void slotGame (String names[], int tokens[], int indexValue) 
            throws InterruptedException {
        
        String userAnswer; // if player wants to play again or not
        String[] obj = new String[3]; // to store the 3 objects spun        
        String[] slotGameObjects = {"Cherries", "Oranges", "Plums", "Bells", 
            "Melons", "Bars"}; // objects the slot uses
        
        int matches = 0; // for determining number of matches  
        boolean playAgain;
        
        out.print("\nWelcome too SLOT");
        out.print("\t\t\t\t\t\t\tYou Have " + tokens[indexValue] + " tokens\n");
        out.println("\nIt costs 10 tokens to roll the slot machine."
                + "\nIf two items match you win 20 tokens"
                + "\nIf all items match you win 100 tokens!");
        
        do { // loop so player can play again
            
            boolean endTrap = false; // error trap for ending, reset no infinite
            playAgain = false; // resets to avoid infinite loop
            
            out.println("\npress enter to begin\n");
            scanS.nextLine(); // waits for enter
            
            tokens[indexValue] -= 10; // subtracts 10 tokens
            if (tokens[indexValue] < 0) // so total token cannot go below 0
                tokens[indexValue] = 0;
            
            /*
            Prints out the 3 objects, using a random number generator to print
            out random objects.
            */
            for (int i = 0; i < obj.length; i++) {
                obj[i] = slotGameObjects[randomNumbers.nextInt(6)]; 
                out.print(obj[i] + " "); // prints object
                Thread.sleep(1000);
            }

            // this for-loop compares the number of matches in obj[] and saves them
            for (int i = 0; i < 2; i++) {
                if(obj[i].equals(obj[i+1])) // compares too next object in array
                    matches++; // adds 1
            }
            
            switch (matches) { // prints appropriate message and adds tokens
                case 0:
                    out.print("\nYou had no matches :(");                
                    out.print("\t\t\t\t\t\t\tYou Have " + tokens[indexValue] 
                            + " tokens"); 
                    break;
                case 1:
                    out.print("\nCongrats you win 50 tokens!");
                    tokens[indexValue] += 100; // adds 100 tokens                    
                    out.print("\t\t\t\t\t\t\tYou Have " + tokens[indexValue] 
                            + " tokens"); 
                    break;
                case 2:
                    out.print("\nCongrats you win 100 tokens!");
                    tokens[indexValue] += 100; // adds 100 tokens                    
                    out.print("\t\t\t\t\t\tYou Have " + tokens[indexValue] 
                            + " tokens");   
                    break;
            }

            do { // asks user if they want to play again or not
            
                out.println("\nWould you like to play again?"
                    + "\n1) yes"
                    + "\n2) no");
            
                userAnswer = scanS.nextLine().toLowerCase();
            
                if ("1".equals(userAnswer) || "yes".equals(userAnswer))
                    playAgain = true; // runs game again
                
                else if ("2".equals(userAnswer) || "no".equals(userAnswer)) {
                    System.out.println("Have a good day!");
                    break; // goes back to main method to output
                }
                
                else { // invalid input
                    System.out.println("Invalid Input Try Again");
                    endTrap = true; // repeat question
                    Thread.sleep(500); // wait .5 seconds 
                } 
                
            } while (endTrap == true); // error trapping loop ends
        } while (playAgain == true); // play again loop ends
    } // Method ends
} // class ends
