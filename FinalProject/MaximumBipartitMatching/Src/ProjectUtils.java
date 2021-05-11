/**
 * <h1>ProjectUtils Class<\h1>
 * <h2>Utility methods used throughout the package</h2>
 * @author Olivia Anastassov and Nikki Kirk
 * @version 1.0
 * @since 5/9/2021
 */

package Src;

import java.io.*;
import java.awt.geom.*;

public class ProjectUtils{
    static boolean debug = true; 

	/**
	 * Method used to print debugging statement to the console
	 * @param statement debugging statement to be printed to the console
	 */
	public static void printDebug(String statement){
		if(debug){
			System.out.println(statement);
		}
	}

	/**
     * Method used to convert to indexing beginning with 1
     * (Used for the purposes of Mathematica)
     * @param n - int to convert
     * @return - returns int + 1
     */
	public static int toMathId(int n){
        return n+1;
    }

	public static int toJavaId(int n){
        return n-1;
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
        for(int i = 0; i < array.length; i++){
            printDebug("" + toMathId(i) + ": " + array[i]);
        }
    }

	/**
	 * Method used to generate a random point for java graphics
	 * @param range max value
	 * @return
	 */
	public static Point2D makeRandomPoint2D(int range){
		int x = (int)(Math.random() * (range+1)); 
		int y = (int)(Math.random() * (range+1)); 
		Point2D pt = new Point2D.Double(x,y);
		
		return pt;
	}

	/**
	 * Method used to generate random points for java graphics
	 * @param range max value
	 * @return
	 */
	public static Point2D[] makeRandomPoints2D(int range, int numberOfPoints){
		Point2D[] pts = new Point2D[numberOfPoints];

		for (int i=0; i < numberOfPoints; i++){
			pts[i]=makeRandomPoint2D(range);
		}
		return pts;
	}

	/**
	 * Method used to create the input file path
	 * @param inputFilePath path for the input file
	 * @return input file path as a string
	 */
	public static String makeAbsoluteInputFilePath(String inputFilePath){
		File input = new File(inputFilePath);
		String absInputFilePath = "";
		try{
			absInputFilePath= input.getCanonicalPath();
		} catch(IOException e) {
			System.out.println("ERROR: cound not run getCanonicalPath on file "+inputFilePath);
		}
		return absInputFilePath;
	}

	/**
	 * Method used to create the input file path with a specific extension
	 * @param inputFilePath path for the input file
	 * @param ext specific extension to add to the file path
	 * @return input file path with specified extension as a string
	 */
	public static String makeAbsoluteInputFilePathWithExt(String inputFilePath, String ext){
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
	 * Method used to create the output file path with a specific extention
	 * @param inputFilePath path for the input file
	 * @param outputFileFolder path for the output folder
	 * @param ext specific extension to be added to the file path
	 * @return output file path with the specified extension as a string
	 */
	public static String makeAbsoluteOutputFilePathWithExt(String inputFilePath, String outputFileFolder, String ext){
		File input = new File(inputFilePath);
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
	 * @param inputFilePath path for the input file
	 * @param outputFilePath path for the output file
	 * @throws IOException
	 */
	public static void copyFileUsingStream(String inputFilePath, String outputFilePath) throws IOException {
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

	
}
