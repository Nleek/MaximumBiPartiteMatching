/**
 * <h1>MainMMBM<\h1>
 * <h2>Main driver program for Maximum Bipartite Matching</h2>
 * @author Olivia Anastassov and Nikki Kirk
 * @version 1.0
 * @since 5/2/2021
 */

import java.io.*;

public class MainMBM {
	final static boolean DEBUG = true; 

	/**
	 * Method used to create the input file path
	 * @param inputFilePath - input path entered by the user
	 * @return - input path as a string
	 */
	private static String makeAbsoluteInputFilePath(String inputFilePath){
		File input = new File(inputFilePath);
		String absInputFilePath = "";
		try{
			absInputFilePath= input.getCanonicalPath();
		} 
		catch(IOException e){
			System.out.println("ERROR: cound not run getCanonicalPath on file "+inputFilePath);
		}
		return absInputFilePath;
	}

	/**
	 * Method used to create the input file path with a specific extension
	 * @param inputFilePath - input path entered by the user
	 * @param ext - extension to be added to the path
	 * @return - input path with extension as a string
	 */
	private static String makeAbsoluteInputFilePathWithExt(String inputFilePath, String ext){
		File input = new File(inputFilePath);
		String inputFileFolder = input.getParent();
		String fileName = input.getName();
		String name = fileName.split("\\.")[0];
		// make output file path with extension
		String newInputFilePath = inputFileFolder + "/" + name + "." + ext;
		File newInput= new File(newInputFilePath);
		String absNewInputFilePath = "";
		try{
			absNewInputFilePath = newInput.getCanonicalPath();
		} 
		catch(IOException e){
			System.out.println("ERROR: cound not run getCanonicalPath on file "+newInputFilePath);
		}
		return absNewInputFilePath;
	}	

	/**
	 * Method used to create the output file path with a specific extension
	 * @param inputFilePath - input path entered by the user
	 * @param outputFileFolder - output folder entered by the user
	 * @param ext - extension to be added to the path
	 * @return - output path with extension as a string
	 */
	private static String makeAbsoluteOutputFilePathWithExt(String inputFilePath, String outputFileFolder, String ext){

		File input = new File(inputFilePath);
		String parentPath = input.getParent();
		String fileName = input.getName();
		String name = fileName.split("\\.")[0];
		// make output file path with extension
		String outputFilePath = outputFileFolder + "/" + name + "." + ext;
		File output= new File(outputFilePath);
		String absOutputFilePath = "";
		try{
			absOutputFilePath = output.getCanonicalPath();
		} 
		catch(IOException e){
			System.out.println("ERROR: cound not run getCanonicalPath on file "+outputFilePath);
		}
		return absOutputFilePath;
	}
	
	/**
	 * Method used to copy a file
	 * @param inputFilePath - input file path entered by the user
	 * @param outputFilePath - output file path entered by the user
	 * @throws IOException
	 */
	private static void copyFileUsingStream(String inputFilePath, String outputFilePath) throws IOException {
		File source = new File(inputFilePath);
		File dest = new File(outputFilePath);
		InputStream is = null;
	    OutputStream os = null;
	    try{
	        is = new FileInputStream(source);
	        os = new FileOutputStream(dest);
	        byte[] buffer = new byte[1024];
	        int length;
	        while ((length = is.read(buffer)) > 0) {
	            os.write(buffer, 0, length);
	        }
	    } 
		finally{
	        is.close();
	        os.close();
	    }
	}

	/**
	 * Method used to print debugging statements to the system
	 * @param statement - debugging statement to print
	 */
	public static void printDebug(String statement){
		if(DEBUG){
			System.out.println(statement);
		}
	}

	/**
	 * Main method that runs the program
	 * @param args - String array of argments
	 * argument0 should be the path of the input file
	 * argument1 should be the path of the output file
	 */
    public static void main(String[] args) {
        String inputFilePath = args[0];
		String outputFileFolder = args[1];

		if (args.length < 2){	
			return;
		}
		
		String absInputFilePath = makeAbsoluteInputFilePath(inputFilePath);
		String absInputGrfFilePath = makeAbsoluteInputFilePathWithExt(inputFilePath,"grf");
		String absOutputGrfFilePath = makeAbsoluteOutputFilePathWithExt(inputFilePath,outputFileFolder,"grf");
        String absOutputTreFilePath = makeAbsoluteOutputFilePathWithExt(inputFilePath,outputFileFolder,"mbm");
		
		// read graph
		printDebug("======  STEP 1: READ DGRAPH FROM TXT FILE");
		DGraphEdges dGraph = new DGraphEdges();
		dGraph.readFromTxtFile(absInputFilePath);
		printDebug("......  DONE STEP 1");

		printDebug("======  STEP 2: PRINT DGRAPH ");
		dGraph.printDGraph();
		printDebug("......  DONE STEP 2");

		printDebug("======  STEP 3: CONVERT TO ADJACENCY Matrix ");
		DGraphMatrix dGraphMatrix = dGraph.toMatrix();
		printDebug("......  DONE STEP 3");

		printDebug("======  STEP 4: PRINT ADJACENCY Matrix ");
		dGraphMatrix.print();
		printDebug("......  DONE STEP 4");

		printDebug("======  STEP 5: COMPUTE Maximum Bipartit Matching");
		MBMDataStructures MBMDS = dGraphMatrix.fordFulkerson();
		//MBMDS.printResidual();
		printDebug("......  DONE STEP 5");

		printDebug("======  STEP 6: PRINT Maximum Bipartit Matching Max Flow");
        System.out.println(MBMDS.maxFlow);
		printDebug("......  DONE STEP 6");

		printDebug("======  STEP 7: WRITE Maximum Bipartit Matching Max Flow TO MATHEMATICA FILE");
        MBMDS.writeMBMToMathematicaFile(absOutputTreFilePath);
		printDebug("......  DONE STEP 7");
	
		// printDebug("======  STEP 8: COPY GRF TO OUTPUT FOLDER"); //ask professor if we need to custome make grf files
		// try {
		// 	copyFileUsingStream(absInputGrfFilePath,absOutputGrfFilePath);
		// }  
		// catch (IOException e) {
        //     System.out.println("ERROR: IOException.");
        //     e.printStackTrace();
		// }
		// printDebug("......  DONE STEP 8");

	}
}
