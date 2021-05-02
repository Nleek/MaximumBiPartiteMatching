/**
 * <h1>MBMDataStructures Class<\h1>
 * <h2>contains data structures used for Maximum Bipartite Matching</h2>
 * @author Olivia Anastassov and Nikki Kirk
 * @version 1.0
 * @since 5/2/2021
 */

import java.util.*;
import java.io.*;

public class MBMDataStructures{
    private int nrV;
    public int [] parent;
    public boolean [] visited;
    public int maxFlow;
    public int [][] residualGraph;//residualGraph
    public boolean foundPath;
    final static boolean DEBUG = false;

//------------------------------------- 
// Constructors 
//-------------------------------------
    public MBMDataStructures(int nrV){
        this.nrV = nrV;
        this.parent = new int [nrV+2];
        this.visited = new boolean[nrV+2];
        this.maxFlow = 0;
        this.residualGraph = new int [nrV+2][nrV+2];
        this.foundPath = false;
    }

//------------------------------------- 
// Utility/Printing
//-------------------------------------
//      toMathId
//      printDebug
//      printArrayInt
//      printArrayBoolean
//-------------------------------------
    /**
     * Method used to convert to indexing beginning with 1
     * (Used for the purposes of Mathematica)
     * @param n - int to convert
     * @return - returns int + 1
     */
    public static int toMathId(int n){
        return n+1;
    }

    /**
     * Method used to print debugging statement to the console
     * @param message - statement as a string
     */
    public static void printDebug(String message){
        if(DEBUG){
            System.out.println(message);
        }
    }

    /**
     * Method used to print int array to a Mathematica compatible array
     * @param array - the array to convert
     */
    public static void printArrayInt(int [] array){
        for(int i = 0; i < array.length; i++){
            printDebug(""+ toMathId(i)+ ": " + array[i]);
        }
    }

    /**
     * Method used to convert boolean array to a Mathematica compatible array
     * @param array - the array to convert
     */
    public static void printArrayBoolean(boolean [] array){
        for(int i = 0; i<array.length; i++){
            printDebug("" + toMathId(i) + ": " + array[i]);
        }
    }

    /**
     * Method used to print the residualGraph created
     * by running the fordFulkerson method
     */
    public void printResidual(){
        for(int i = 0; i< residualGraph.length; i++){
            for(int j = 0; j< residualGraph[i].length; j++){
                System.out.print(residualGraph[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * Method used to convert int maxFlow to a string
     * @return - maxFlow as a string
     */
    public String maxFlowToString(){
        StringBuilder output = new StringBuilder();
        output.append(maxFlow);
        return output.toString();
    }

//-------------------------------------
// Write To File
//------------------------------------- 
//      writeMBMToMathematicaFile
//-------------------------------------
    /**
     * Method used to write the maxFlow to a file
     * @param filename - name of the file to write to
     */
    public void writeMBMToMathematicaFile(String filename){
    try {
        FileWriter myWriter = new FileWriter(filename); 
        myWriter.write(maxFlowToString());
        myWriter.close();
    } 
    catch (IOException e) { 
        System.out.println("An error occurred."); 
        e.printStackTrace();
    } 
    }
}