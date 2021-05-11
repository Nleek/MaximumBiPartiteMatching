/**
 * <h1>MainMBM Class<\h1>
 * <h2>Driver code for the program</h2>
 * @author Olivia Anastassov and Nikki Kirk
 * @version 1.0
 * @since 5/9/2021
 */

package Src;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.Graphics2D;

public class MainMBM extends Frame{
	public static GraphicalGraph gGraph;
    private static final long serialVersionUID = 1L;
	private static int IMAGESIZEX = 400;
	private static int IMAGESIZEY = 500;
	private static int PLOTRANGE = 100;
	private static int SCALE = IMAGESIZEX/PLOTRANGE;
	public static ArrayList<DEdge> pairs;

//------------------------------------- 
// Constructors 
//-------------------------------------
	public MainMBM(){
		super("Graph Demonstration");
		prepareGUI();
	}

	public void prepareGUI(){
		setSize(IMAGESIZEX,IMAGESIZEY);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent windowEvent){
				System.exit(0);
			}        
		});
	}

//------------------------------------- 
// Drawing Method
//-------------------------------------
	@Override
	public void paint(Graphics g) {	
		// in order to use the Point2D object
		Graphics2D g2 = (Graphics2D) g;	
		gGraph.draw(g2);	
	}

//------------------------------------- 
// Main Driver
//-------------------------------------
    public static void main(String[] args) {
		//-----------------------------
		// Input and output files
		// taken from the command line
		//-----------------------------
		String inputFilePath = args[0];
		String inputGraphicsFilePath = args[1];
		String outputFileFolder = args[2];
        
		if (args.length < 3){	
			return;
		}

		String absInputFilePath = ProjectUtils.makeAbsoluteInputFilePath(inputFilePath);
		String absInputGrfFilePath = ProjectUtils.makeAbsoluteInputFilePathWithExt(inputFilePath,"grf");
		String absOutputGrfFilePath = ProjectUtils.makeAbsoluteOutputFilePathWithExt(inputFilePath,outputFileFolder,"grf");
        String absOutputTreFilePath = ProjectUtils.makeAbsoluteOutputFilePathWithExt(inputFilePath,outputFileFolder,"mbm");
		
		ProjectUtils.printDebug("======  STEP 1: READ DGRAPH FROM TXT FILE");
		DGraphEdges dGraph = new DGraphEdges();
		dGraph.readFromTxtFile(inputFilePath);
		ProjectUtils.printDebug("......  DONE STEP 1");

		ProjectUtils.printDebug("======  STEP 2: PRINT DGRAPH ");
		dGraph.printDGraph();
		ProjectUtils.printDebug("......  DONE STEP 2");

		ProjectUtils.printDebug("======  STEP 3: CONVERT TO ADJACENCY Matrix ");
		DGraphMatrix dGraphMatrix = dGraph.toMatrix();
		ProjectUtils.printDebug("......  DONE STEP 3");

		ProjectUtils.printDebug("======  STEP 4: PRINT ADJACENCY Matrix ");
		dGraphMatrix.print();
		ProjectUtils.printDebug("......  DONE STEP 4");

		ProjectUtils.printDebug("======  STEP 5: COMPUTE Maximum Bipartit Matching");
		MBMDataStructures MBMDS = dGraphMatrix.fordFulkerson();
		MBMDS.printResidual();
		ProjectUtils.printDebug("......  DONE STEP 5");

		ProjectUtils.printDebug("======  STEP 6: PRINT Maximum Bipartit Matching Max Flow");
        MBMDS.printMaxFlow();
		ProjectUtils.printDebug("......  DONE STEP 6");

		ProjectUtils.printDebug("======  STEP 7: WRITE Maximum Bipartit Matching Max Flow TO MATHEMATICA FILE");
        MBMDS.writeMBMToMathematicaFile(absOutputTreFilePath);
		ProjectUtils.printDebug("......  DONE STEP 7");

		ProjectUtils.printDebug("======  STEP 8: GET MATCHING EDGES");
        pairs = MBMDS.getPairs();
		ProjectUtils.printDebug("......  DONE STEP 8");

		ProjectUtils.printDebug("======  STEP 1.5: CREATE AND READ GEOMETRIC GRAPH FROM TXT FILE");		
        gGraph = new GraphicalGraph(inputGraphicsFilePath);	
		gGraph.setPairs(pairs);
        ProjectUtils.printDebug("......  DONE STEP 1.5");
	
		ProjectUtils.printDebug("======  STEP 9: RESCALE AND PRINT GRAPH THROUGH JAVA GRAPHICS");	
		gGraph.rescalePoints(SCALE);
		MainMBM frame = new MainMBM();
		frame.setVisible(true);	
		ProjectUtils.printDebug("......  DONE STEP 9");
	}
}
