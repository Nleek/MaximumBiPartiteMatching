/**
 * <h1>DGraphEdges Class<\h1>
 * <h2>Methods to create a directed graph represented as an edge list</h2>
 * <h2>Also contains methods to read/write from/to a file</h2>
 * @author Olivia Anastassov and Nikki Kirk
 * @version 1.0
 * @since 5/9/2021
 */

package Src;

import java.util.*;
import java.io.*;
public class DGraphEdges{
    public int nrVertices;
    public int nrLeftVertices;
    public int nrRightVertices;
    public ArrayList<DEdge> edges;
    public int nrEdges;
    public DEdge[] listOfEdges;

//------------------------------------- 
// Constructors 
//-------------------------------------
    public DGraphEdges(){
        this.nrVertices = 0;
        this.edges = new ArrayList<DEdge>();
    }
    public DGraphEdges(int nrVertices){
        this.nrVertices = nrVertices;
        this.edges = new ArrayList<DEdge>();
    }
    public DGraphEdges(int nrVertices, DEdge[] listOfEdges){
        this.nrVertices = nrVertices;
        this.listOfEdges = listOfEdges;
    }
    public DGraphEdges(int nrVertices, int nrEdges){
        this.nrVertices = nrVertices;
        this.nrEdges = nrEdges;
    }

//-------------------------------------
// Getters
//-------------------------------------
//      getNrVertices
//-------------------------------------
    /**
     * Method used to get the number of vertices in the graph
     * @return - number of vertices
     */
    public int getNrVertices(){
        return nrVertices;
    }

    /**
     * Method to return the list of edges in a graph
     * @return edges
     */
    public DEdge[] getEdges(){
        return listOfEdges;
    }

//-------------------------------------
// Setters
//-------------------------------------
//      setNrVertices
//-------------------------------------
    /**
     * Method used to set the number of vertices in the graph
     */
    public void setNrVertices(int nrVertices){
        this.nrVertices = nrVertices;
    }

    /**
     * Method used to set the number of vertices on the left 
     * side of the bipartite graph
     */
    public void setNrLeftVertices(int nrLeftVertices){
        this.nrLeftVertices = nrLeftVertices;
    }

    /**
     * Method used to set the number of vertices on the right
     * side of the bipartite graph
     */
    public void setNrRightVertices(int nrRightVertices){
        this.nrRightVertices = nrRightVertices;
    }

    /**
     * Method to set edges in a graph
     * @param edges
     */
    public void setEdges(DEdge[] listOfEdges){
        this.listOfEdges = listOfEdges;
    }

//-------------------------------------
// Serialize, Parse and Print
//-------------------------------------
//      toMathString
//      toString
//      printDGraph 
//-------------------------------------
    /**
     * Method used to convert graph to a string representation
     * @return - graph as a string
     */
    public String toString(){
        String string = "" + nrVertices;
        for(DEdge edge : edges){
            string += "\n" + (edge.getVertex1()) + " " + (edge.getVertex2());
        }
        return string;
    }
    
    /**
     * Method used to convert graph to a Mathamatica string
     * (Method derived from Sivan Nachum)
     * @return - graph as a Mathamatica compatible string
     */
    public String toMathString(){
        String string = "{\n" + nrVertices + ",\n{\n";
        boolean firstElem = true;
        for(DEdge edge : edges){
            if(!firstElem){
                string += ",";
            }
            else{
                firstElem = false;
            }
            string += "{" + (edge.getVertex1()+1) + "," + (edge.getVertex2()+1) + "}," + (edge.getWeight());
        }
        string += "\n}\n}\n";
        return string;
    }

    /**
     * Method used to print the graph to the console
     */
    public void printDGraph(){
        System.out.println(this.toString());
    }

    /**
     * Method used to parse data from a not-perfectly-formatted text file
     * @param line - current line to parse as a string
     * @param myReader - scanner 
     * @return - returns line as a string
     */
    private String skipEmptyLines(String line, Scanner myReader){
        while (myReader.hasNextLine()){
            if (line.length() == 0){
                line = myReader.nextLine();
            }
            else{
                String[] tokens = line.split(" ");
                if (tokens.length > 0){
                    return line;
                }
            }
        }
        return "";
    }

    /**
     * Method used to parse the number of vertices in the graph
     * @param line - current line to parse as a string
     * @param myReader - scanner
     * @return - returns number of vertices as a string
     */
    private String parseNrVerts(String line, Scanner myReader){
        line = skipEmptyLines(line, myReader); 
        if (line.length() == 0){
            // reached the end of file without nr vertices 
            // must init the graph as empty, somehow
            // must close the file 
            myReader.close();
            return "";
        } 
        // Set left-hand vertices
        int nrLeftVertices = Integer.parseInt(line);
        this.setNrLeftVertices(nrLeftVertices);

        // Set Right hand vertices
        line = myReader.nextLine();
        int nrRightVertices = Integer.parseInt(line);
        this.setNrRightVertices(nrRightVertices);

        // Set total number of vertices
        int nrV = nrLeftVertices + nrRightVertices;
        this.setNrVertices(nrV);

        line = myReader.nextLine();
        line = skipEmptyLines(line, myReader); 
        return line;
    }

    /**
     * Method used to parse data from a text file
     * @param myReader - scanner
     */
    private void parseFile(Scanner myReader){
        // The first line has the number of nrVertices
        String line = myReader.nextLine();
        line = parseNrVerts(line, myReader); 
        if (line.length() == 0){
            // reached the end of file without edges 
            // must init the graph as empty, somehow 
            // must close the file
            myReader.close();
            return;
        }

        // Now attempt to process the edges
        // Next lines till the end of file is one edge per line
        int nrEdges = 0;
        nrEdges++;
        String[] edgeString = line.split(" ");
        System.out.println(line);
        String u = edgeString[0];
        String v = edgeString[1];
        addEdge(Integer.parseInt(u), Integer.parseInt(v), 1); 
        line = myReader.nextLine();

        while (myReader.hasNextLine()){
            nrEdges++;
            edgeString = line.split(" ");
            System.out.println(line);
            u = edgeString[0];
            v = edgeString[1];
            addEdge(Integer.parseInt(u), Integer.parseInt(v), 1); 
            line = myReader.nextLine();
        }
        
        if(line.length() != 0){
            nrEdges++;
            edgeString = line.split(" ");
            System.out.println(line);
            u = edgeString[0];
            v = edgeString[1];
            addEdge(Integer.parseInt(u), Integer.parseInt(v), 1); 
        }
    }

//-------------------------------------
// I/O
//-------------------------------------
//      readFromTxtFile
//      writeToTextFile
//      writeToMathematicaFile 
//------------------------------------- 
    /**
     * Method used to read data from a text file
     * @param filename - name of the file to read from
     */
    public void readFromTxtFile(String filename){
        try{
            File graphFile = new File(filename); 
            Scanner myReader = new Scanner(graphFile); 
            parseFile(myReader);
            myReader.close();
        }   
        catch(FileNotFoundException e){
            System.out.println("ERROR: DGraphEdges, readFromTxtFile: file not found.");
            e.printStackTrace();
        }    
    }

    /**
     * Method used to write data to a text file
     * @param filename - name of the file to write to
     */
    public void writeToTextFile(String filename){
        try{
            FileWriter myWriter = new FileWriter(filename); 
            myWriter.write(toString());
            myWriter.close();
        } 
        catch(IOException e) { 
            System.out.println("An error occurred."); 
            e.printStackTrace();
        } 
    }

    /**
     * Method used to write data to a Mathematica compatible file
     * @param filename - name of the file to write to
     */
    public void writeToMathematicaFile(String filename){ 
        try{
            FileWriter myWriter = new FileWriter(filename); 
            myWriter.write(toMathString()); 
            myWriter.close();
        } 
        catch(IOException e){ 
            System.out.println("An error occurred."); 
            e.printStackTrace();
        } 
    }

//-------------------------------------
// Modifiers
//-------------------------------------
//      addVertices
//      addEdge
//-------------------------------------
    /**
     * Method used to add vertices to the graph
     * @param num - number of vertices to add to the graph
     */
    public void addVertices(int num){ 
        this.nrVertices += num;
    }

    /**
     * Method used to add an edge to the graph
     * @param u - first vertex
     * @param v - second vertex
     * @param weight - edge weight
     */
    public void addEdge(int u, int v, int weight){
        this.edges.add(new DEdge(u,v,weight));
    }
//------------------------------------- 
// Converters 
//------------------------------------- 
//      toMatrix
//------------------------------------- 
    /**
     * Method used to convert graph to a matrix representation
     * @return - graph as a DGraphMatrix
     */
    public DGraphMatrix toMatrix(){
        DGraphMatrix dGraphMatrix = new DGraphMatrix(nrVertices, nrLeftVertices, nrRightVertices);
        for(int i = 0; i < edges.size(); i++){
            dGraphMatrix.addEdge(edges.get(i).getVertex1(),edges.get(i).getVertex2(), edges.get(i).getWeight());
        }
        return dGraphMatrix;
    } 
}
