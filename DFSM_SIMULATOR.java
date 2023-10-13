//Names: Christian Laviolette && Christian Arellano
//Date: 10/09/2023
//Class: CS41000-001 Automat and Computability
//Assignment: DFSM Simulator




import java.util.Scanner;

import java.io.File;

public class DFSM_SIMULATOR 
{
    private static Scanner fileScanner;
    private static Scanner inputScanner = new Scanner(System.in);
    private static int stateCount;
    private static int[] finalStates;
    private static int symbolCount;
    private static char[] symbols;
    private static int[][] transitionTable;
    
    
    //function to read the states from dfsm_input 
    private static void buildTransitionTable() 
    {

        transitionTable = new int[stateCount][symbolCount];

        for (int i = 0; i < stateCount; i++) 
        {

           for (String transition : fileScanner.nextLine().replaceAll(" ", "").replaceAll("\\),\\(", " ").replaceAll("\\(", "").replaceAll("\\)", "").split(" ")) 
{

                String[] parts = transition.split(",");
                transitionTable[Integer.parseInt(parts[0])][symbolIndex(parts[1].charAt(0))] = Integer.parseInt(parts[2]);

            }
        }
    }
    //load the dfsm_input.txt file
   private static void loadDFSM() 
{
    fileScanner = null;
    try 
    {
        fileScanner = new Scanner(new File("page43_every_a_followed_by_b.txt"));

        String[] symbolArray = fileScanner.nextLine().replaceAll(" ", "").split(",");
        symbolCount = symbolArray.length;
        symbols = new char[symbolCount];

        for (int i = 0; i < symbolCount; i++) 
        {
            symbols[i] = symbolArray[i].charAt(0);
        }

        stateCount = Integer.parseInt(fileScanner.nextLine());

        String[] stateArray = fileScanner.nextLine().replaceAll(" ", "").split(",");
        finalStates = new int[stateArray.length];

        for (int i = 0; i < stateArray.length; i++) 
        {
            finalStates[i] = Integer.parseInt(stateArray[i]);
        }

        buildTransitionTable();
    } 
    catch (Exception e) 
    {
        System.out.println("could not load DFSM file: " + e.getMessage());
    }
    finally 
    {
        if (fileScanner != null) 
        {
            fileScanner.close();
        }
    }
}
    
    
    
    //function to find the index of input symbol
    private static int symbolIndex(char symbol) 
    {

        for (int i = 0; i < symbolCount; i++) 
        {

            if (symbols[i] == symbol) 
            {
                return i;
            }
        }

        return -1;
    }

    


    

    //function toaccept or reject the input string
    private static boolean validateInput(String input) 
    {

        int currentState = 0;
        for (char c : input.toCharArray()) 
        {
            int index = symbolIndex(c);
            if (index == -1) 
            {
                return false;
            }

            currentState = transitionTable[currentState][index];
        }

        for (int state : finalStates) 
        {
            if (currentState == state) 
            {
                return true;
            }
        }

        return false;
    }




    

    //main function
    public static void main(String[] args) 
    {
        loadDFSM();

        String continueChoice = "yes";
        while (continueChoice.equalsIgnoreCase("yes")) 
        {
            System.out.print("Enter String to be Evsluated: ");
            String input = inputScanner.nextLine();
            System.out.println(validateInput(input) ? "Accepted" : "Rejected");
            System.out.print(" continue? (yes/no): ");
            continueChoice = inputScanner.nextLine();
        }
    }
}