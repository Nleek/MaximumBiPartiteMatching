/**
 * <h1>GraphicalGraph Class<\h1>
 * <h2>Methods for drawing a graph using Java Graphics</h2>
 * @author Olivia Anastassov and Nikki Kirk
 * @author Ileana Streinu
 * @version 1.0
 * @since 5/11/2021
 */

package Src;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
import java.awt.geom.*;
import java.awt.*;
import java.awt.Stroke;
import java.awt.Graphics2D;

public class GraphicalGraph extends DGraphEdges{
	private Point2D[] points;
	private ArrayList<DEdge> pairs;
	private static int RANGE = 400;
    Color POINTCOLOR = Color.lightGray;
	int POINTRADIUS = 15;
	int POINTDIAM = 2 * POINTRADIUS;
	Color POINTIDCOLOR = Color.BLACK;
	Color POINTLABCOLOR = Color.WHITE;
	int IDFONTSIZE = 12;  
	Color EDGECOLOR = Color.GRAY;
	Color MATCHEDCOLOR = Color.ORANGE;
    Stroke DOTTEDSTROKE = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 3, 1 }, 0);
    Stroke STROKE = new BasicStroke(4.0f);
	
//------------------------------------- 
// Constructors 
//-------------------------------------	
    public GraphicalGraph(int nrVertices, int nrEdges){
        super(nrVertices, nrEdges);
        this.points = ProjectUtils.makeRandomPoints2D(RANGE, nrVertices);
    }

	public GraphicalGraph(int numVertices, DEdge[] listOfEdges){
        super(numVertices, listOfEdges);
        this.points = ProjectUtils.makeRandomPoints2D(RANGE, numVertices);
    }

    public GraphicalGraph(String absFilePath){
		super();
		try {
			File graphFile = new File(absFilePath);
			Scanner myReader = new Scanner(graphFile);
			parseFile(myReader);
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: GGraph, readFromTxtFile: file not found.");
			e.printStackTrace();
		}
    }

//-------------------------------------
// Getters
//-------------------------------------
//		getPoints
//-------------------------------------
	/**
	 * Method used to get the list of points
	 * @return Points2D[]
	 */
    public Point2D[] getPoints(){
        return points;
    }

	/**
	 * Method used to get the list of pairs
	 * @return ArrayList<DEdge>
	 */
	public ArrayList<DEdge> getPairs(){
		return pairs;
	}
	
//-------------------------------------
// Setters
//-------------------------------------
//		setPoints
//-------------------------------------
	/**
	 * Method used to set the class points
	 * @param points a list of points
	 */
    public void setPoints(Point2D[] points){
        this.points = points;
    }	

	/**
	 * Method used to set the class pairs
	 * @param pairs an ArrayList of matched pairs
	 */
	public void setPairs(ArrayList<DEdge> pairs){
        this.pairs = pairs;
		System.out.println(pairsToString());
    }	

	/**
     * Method used to convert ArrayList<DEdge> pairs to a string
     * @return pairs as a string
     */
    public String pairsToString(){
        String output = "";
        for(int i = 0; i < pairs.size(); i++){
            output = output + pairs.get(i).getVertex1() + " " + pairs.get(i).getVertex2();
            output = output + "\n";
        }
        return output;
    }
	
//-------------------------------------
// Modifiers
//-------------------------------------
//		rescalePoints
 //-------------------------------------
	/**
	 * Method used to rescale the points inside of the canvas
	 * @param scale value in which to rescale the points by
	 */
    public void rescalePoints(int scale){
		int nrPts = points.length;
		Point2D[] scaledPoints = new Point2D[nrPts];
		
		for (int i=0; i < nrPts; i++){
			Point2D newPoint = new Point2D.Double(points[i].getX()*scale, points[i].getY()*scale);
			scaledPoints[i]=newPoint;
		}
		
		this.points = scaledPoints;
    }	

//-------------------------------------
// Helpers
//-------------------------------------
//      pointToString
//      pointsToString
//      pointToMathString
//      pointsToMathString
//      edgeToString
//      edgesToString
//      edgeToMathString
//      edgesToMathString
//-------------------------------------	
	/**
	 * Method used to convert a single point to a string
	 * @param pt the point to convert
	 * @return point as a string
	 */
	public String pointToString(Point2D pt){
		int xs = (int) pt.getX() + 1;
		int ys = (int) pt.getY() + 1;
		String s = "" + xs + " " + ys + "";
		return s;
	}

	/**
	 * Method to convert mulitple points to a string
	 * @param pts list of points
	 * @return points as a string
	 * @see pointToString
	 */
	public String pointsToString(Point2D[] pts){
		String s = "";
		for (int i=0; i < pts.length; i++){
			s += pointToString(pts[i]) + "\n";
		}
		return s;
	}

	/**
	 * Method used to convert a single point to a Mathematica string
	 * @param pt the point to convert
	 * @return point as a Mathematica string
	 */
	public String pointToMathString(Point2D pt){
		int xs = (int) pt.getX() + 1;
		int ys = (int) pt.getY() + 1;
		String s = "{" + xs + ", " + ys + "}";
		return s;
	}

	/**
	 * Method used to convert mulitple points to a Mathematica string
	 * @param pts list of points
	 * @return points as a Mathematica string
	 * @see pointToMathString
	 */
	public String pointsToMathString(Point2D[] pts){
		int nrP = pts.length;
		String s="";
		for (int i = 0; i < nrP-1; i++){
			s += pointToMathString(pts[i]) + ",\n";
		}
		s += pointToMathString(pts[nrP-1]) + "\n";
		
		return "{\n" + s + "}\n";
	}
	
	/**
	 * Method used to convert a single edge to a string
	 * @param edge the edge to convert
	 * @return edge as a string
	 */
	public String edgeToString(DEdge edge){
		int v1 = ProjectUtils.toMathId(edge.getVertex1());
		int v2 = ProjectUtils.toMathId(edge.getVertex2());
		String s = "" + v1 + " " + v2 + "";
		return s;
	}

	/**
	 * Method used to convert multiple edges to a string
	 * @param edges list of edges
	 * @return edges as a string
	 * @see edgeToString 
	 */
	public String edgesToString(DEdge[] edges){
		String s="";
		for (int i = 0; i < edges.length; i++){
			s += edgeToString(edges[i]) + "\n";
		}
		return s;
	}

	/**
	 * Method used to convert an edge to a Mathematica string
	 * @param edge the edge to convert
	 * @return edge as a Mathematica string
	 */
	public String edgeToMathString(DEdge edge){
		int v1 = ProjectUtils.toMathId(edge.getVertex1());
		int v2 = ProjectUtils.toMathId(edge.getVertex2());
		String s = "{" + v1 + ", " + v2 + "}";
		return s;
	}

	/**
	 * Method used to convert multiple edges to a Mathematica string
	 * @param edges list of edges to convert
	 * @return edges as a Mathematica string
	 * @see edgeToMathString
	 */
	public String edgesToMathString(DEdge[] edges){
		int nrP = edges.length;
		String s="";
		for (int i=0; i < nrP-1; i++){
			s += edgeToMathString(edges[i]) + ",\n";
		}
		s += edgeToMathString(edges[nrP-1]) + "\n";
		
		return "{\n" + s + "}\n";
	}
	
//-------------------------------------
// Serialize, Parse and Print
//-------------------------------------
//      toString
// 		toMathString
//		print
//		printMath
//-------------------------------------

	/**
	 * Method used to convert the graph to a string
	 * @return a string representation of the graph
	 */
	public String toString(){
		int nrV = getNrVertices();
		DEdge[] edges = getEdges();
		int nrE = edges.length;
		
		String string = "";
		string += nrV +"\n";
		string += nrE +"\n";
		string += pointsToString(points);
		string += edgesToString(edges);

		return string;
	}

	/**
	 * Method used to convert the graph to a Mathematica string
	 * @return a Mathematica string representation of the graph
	 */
	public String toMathString(){
		String s;
		s = "{\n";
		s += pointsToMathString(points);
		s += ",\n";
		s += edgesToMathString(this.getEdges());
		s += "}\n";
		return s;
	}

	/**
	 * Method used to print the graph to the console
	 */
	public void print(){
		System.out.println(this.toString());
	}

	/**
	 * Method used to print the Mathematica string to the console
	 */
	public void printMath(){
		System.out.println(this.toMathString());
	}
	
//-------------------------------------
// Serialization and Print
//-------------------------------------
//      skipEmptyLines
//      parseFile
//-------------------------------------
	/**
	 * Method used to skip the blank lines while parsing a file
	 * @param myReader scanner
	 * @return a list of non-blank lines
	 */
	private String[] skipEmptyLines(Scanner myReader){
		String[] tokens = new String[0];
		while (myReader.hasNextLine()){
			String line = myReader.nextLine();
			if (line.length() == 0){
				line = myReader.nextLine();
			} 
            else{
				tokens = line.split(" ");
				if (tokens.length > 0){
					return tokens;
				}
			}
        }
		return tokens;
	}

	/**
	 * Method used to parse the contents of a file
	 * @param myReader a scanner
	 */
	private void parseFile(Scanner myReader){
		String[] tokens = skipEmptyLines(myReader);
		if(tokens.length == 0){
			return;
		}
		
		// The first non-empty line has the number of left vertices		
		int nrVLeft = Integer.parseInt(tokens[0]);
		this.setNrLeftVertices(nrVLeft);
		
		tokens = skipEmptyLines(myReader);
		if (tokens.length == 0) {
			return;
		}
		
		// The second non-empty line has the number of right vertices		
		int nrVRight = Integer.parseInt(tokens[0]);
		this.setNrRightVertices(nrVRight);

		//Set the total number of vertices
		int nrV = nrVLeft + nrVRight;

		tokens = skipEmptyLines(myReader);
		if (tokens.length == 0) {
			return;
		}

		int nrE = Integer.parseInt(tokens[0]);

		// Create list of points of size nrV
		this.points = new Point2D.Double[nrV];
		int crtPtId = 0;
			
		tokens = skipEmptyLines(myReader);
		while (tokens.length > 0 && crtPtId < nrV){
			String xs = tokens[0];
			String ys = tokens[1];
			int x = ProjectUtils.toJavaId(Integer.parseInt(xs));
			int y = ProjectUtils.toJavaId(Integer.parseInt(ys));
			Point2D pt = new Point2D.Double(x, y);
			points[crtPtId] = pt;
			tokens = skipEmptyLines(myReader);
			crtPtId++;
		}
		setPoints(points);
	
		// Parse edges	
		int crtEdgeId = 0;
		DEdge[] edges = new DEdge[nrE];
		while (tokens.length > 0 && crtEdgeId < nrE){
			String u = tokens[0];
			String v = tokens[1];
			int v1 = Integer.parseInt(u);
			int v2 = Integer.parseInt(v);
			DEdge edge = new DEdge(v1, v2);
			edges[crtEdgeId] = edge;
			tokens = skipEmptyLines(myReader);
			crtEdgeId++;
		}
		setEdges(edges);
		return;
	}
	
//-------------------------------------
// I/O
//-------------------------------------
// 		readFromTxtFile
//		writeToTextFile
//		writeToMathematicaFile
//-------------------------------------
	/**
	 * Method used to read the text from a file
	 * @param absFilePath the directory where the file can be found
	 * @return graph 
	 */
    public GraphicalGraph readFromTextFile(String absFilePath){
		// Read graph from txt file
		GraphicalGraph graph = new GraphicalGraph(0,0);

		try{
			File graphFile = new File(absFilePath);
			Scanner myReader = new Scanner(graphFile);
			graph.parseFile(myReader);
			myReader.close();
		} 
		catch(FileNotFoundException e) {
			System.out.println("ERROR: readFromFile: file not found.");
			e.printStackTrace();
		}
		return graph;
    }

	/**
	 * Method used to write the contents to a text file
	 * @param absFilePath the directory where the file is being written to
	 */
	public void writeToTextFile(String absFilePath){
		try{
			FileWriter fileWriter = new FileWriter(absFilePath);
			BufferedWriter myWriter = new BufferedWriter(fileWriter);
			myWriter.write(toString());
			myWriter.close();
		} 
        catch (IOException e){
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}

	/**
	 * Method used to write the contents to a graph file
	 * @param absFilePath the directory where the file is being written to
	 */
	public void writeToGrfFile(String absFilePath){
		try {
			FileWriter myWriter = new FileWriter(absFilePath);
			myWriter.write(toMathString());
			myWriter.close();
		} 
        catch (IOException e){
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}

//---------------------------------------
// Drawing	
//---------------------------------------
//      drawPoint
//      drawPoints
//      drawPointId
//      drawPointIds
//      drawPointLabel
//      drawPointLabels
//      drawEdge
//      drawEdges
//      draw
//---------------------------------------
	/**
	 * Method used to draw a single point on the canvas
	 * @param pt point being drawn
	 * @param g the canvas
	 * @return the canvas with the point drawn on it
	 */
	private Graphics2D drawPoint(Point2D pt, Graphics2D g){
		g.setColor(POINTCOLOR);
		g.fillOval((int)pt.getX()-POINTRADIUS,(int)pt.getY()-POINTRADIUS,POINTDIAM,POINTDIAM);
		return g;
	}	
	
	/**
	 * Method used to draw multiple points on the canvas
	 * @param pts list of points
	 * @param g the canvas
	 * @return the canvas with the points drawn on it
	 * @see drawPoint
	 */
	private Graphics2D drawPoints(Point2D[] pts, Graphics2D g){
		for (int i=0; i < pts.length; i++){
			drawPoint(pts[i],g);
		}
		return g;
	}
	
	/**
	 * Method used to draw the pointID on the canvas
	 * @param pt specific point
	 * @param id point's ID
	 * @param g the canvas
	 * @return the canvas with the point ID drawn on it
	 */
	private Graphics2D drawPointId(Point2D pt, int id, Graphics2D g){	
		g.drawString("" + id, (int)pt.getX()-IDFONTSIZE/4, (int)pt.getY()+IDFONTSIZE/4);  
		return g;
	}
	
	/**
	 * Method used to draw mutliple point IDs on the canvas
	 * @param pts list of points
	 * @param g the cnavas
	 * @return the canvas with the point IDs drawn on it
	 * @see drawPointId
	 */
	private Graphics2D drawPointIds(Point2D[] pts, Graphics2D g){
		g.setColor(POINTIDCOLOR);	
		Font font = new Font("Serif", Font.PLAIN, IDFONTSIZE);
		g.setFont(font);
		
		for (int i = 0; i < pts.length; i++){
			drawPointId(pts[i], i ,g);
		}
		return g;
	}
	
	/**
	 * Method used to draw the point label on the canvas
	 * @param pt a specific point
	 * @param id the points ID
	 * @param g the canvas
	 * @return the canvas with the point labels drawn on it
	 */
	private Graphics2D drawPointLabel(Point2D pt, int id, Graphics2D g){	
		g.drawString("" + id, (int)pt.getX()-IDFONTSIZE/4, (int)pt.getY()+IDFONTSIZE/4);  
		return g;
	}

	/**
	 * Method used to draw multiple point labels on the canvas
	 * @param pts list of points
	 * @param labels list of labels
	 * @param g the canvas
	 * @return the canvas with the point labels drawn on it
	 * @see drawPointLabel
	 */
	private Graphics2D drawPointLabels(Point2D[] pts, int[] labels, Graphics2D g){
		g.setColor(POINTLABCOLOR);	
		Font font = new Font("Serif", Font.PLAIN, IDFONTSIZE);
		g.setFont(font);
		
		for (int i=0; i < labels.length; i++){
			drawPointLabel(pts[i], labels[i] ,g);
		}
		return g;
	}
	
	/**
	 * Method used to draw an edge on the canvas
	 * @param pts list of points
	 * @param edge the edge to draw
	 * @param g the canvas
	 * @return the canvas with the edge drawn on it
	 */
	private Graphics2D drawEdge(Point2D[] pts, DEdge edge, Graphics2D g){
		int u = edge.getVertex1();
		int v = edge.getVertex2();
		
		Point2D pt1 = pts[u];
		Point2D pt2 = pts[v];
		
		g.drawLine((int) pt1.getX(), (int) pt1.getY(), (int) pt2.getX(), (int) pt2.getY());
		return g;
	}

	/**
	 * Method used to draw mutliple edges on the canvas.
	 * Changes color of the edge based on whether the edge is
	 * a matched pair or not.
	 * @param pts list of points
	 * @param edges list of edges to draw
	 * @param g the canvas
	 * @param pairs a list of matched edges
	 * @return the canvas with multiple edges drawn on it
	 * @see drawEdge
	 */
	private Graphics2D drawEdges(Point2D[] pts, DEdge[] edges, Graphics2D g, ArrayList<DEdge> pairs){
	    g.setStroke(STROKE);	
		int nrEdges = edges.length;
		
		for (int i = 0; i < nrEdges; i++){
			DEdge edge = edges[i];
			g.setColor(EDGECOLOR);
			int edgeU = edge.getVertex1();
			int edgeV = edge.getVertex2();

			for(int j = 0; j < pairs.size(); j++){
				int pairU = pairs.get(j).getVertex1();
				int pairV = pairs.get(j).getVertex2();

				//If the edge is a matched edge, change the edge color
				if((edgeU == pairU) && (edgeV == pairV)){
					g.setColor(MATCHEDCOLOR);
				}
			}
			g = drawEdge(pts,edge,g);
		}
		return g;
	}
	
	/**
	 * Method used to draw all objects on the canvas
	 * @param g the canvas
	 * @return the canvas with everything drawn on it
	 * @see drawEdges
	 * @see drawPoints
	 * @see drawPointIDs
	 * @see drawPointLabels
	 */
	public Graphics2D draw(Graphics2D g){
		Point2D[] points = getPoints();
		DEdge[] edges = getEdges();
		ArrayList<DEdge> pairs = getPairs();
		
		g = drawEdges(points,edges,g, pairs);
		g = drawPoints(points,g);		
		// g=drawPointLabels(pts,pointLabels,g);
		g = drawPointIds(points,g);
		return g;
	}
	
}

