/**
 * <h1>DEdge Class<\h1>
 * <h2>Methods for a directed edge represented as a pair of vertices (u,v) and a weight</h2>
 * @author Olivia Anastassov and Nikki Kirk
 * @version 1.0
 * @since 5/9/2021
 */

package Src;

public class DEdge{
    private int u;
    private int v;
    private int weight;

//------------------------------------- 
// Constructors 
//-------------------------------------
    public DEdge(int u, int v){
        this.u = u; this.v = v; this.weight = 1; 
    }
    public DEdge(int u, int v, int weight){
        this.u = u; this.v = v; this.weight = weight;
    }
    public DEdge(DEdge e){
        this.u = e.getVertex1();
        this.v = e.getVertex2();
        this.weight = e.getWeight();
    }

//------------------------------------- 
// Getters 
//-------------------------------------
    /**
     * Method used to get the first vertex
     * @return - int value of vertex u
     */
    public int getVertex1(){
        return u;
    }

    /**
     * Method used to get the second vertex
     * @return - int value of vertex v
     */
    public int getVertex2(){
        return v;
    }

    /**
     * Method used to get the edge weight
     * @return - int value of weight
     */
    public int getWeight(){
        return weight;
    }
//------------------------------------- 
// Testers
//-------------------------------------
    /**
     * Method compares u and v to check if it is a self loop
     * @param edge 
     * @return - returns true if u and v are equal
     */
    public boolean equals(DEdge edge){
        if(this.u == edge.u && this.v == edge.v){
            return true;
        }
        return false;
    }

     /**
     * Method checks if an edge is a loop to itself
     * Example: [3,3]
     * @return Returns true if edge is a self loop
     */
    public Boolean isSelfLoop(){
        if(this.u == this.v){
            return true;
        }
        return false;
    }
}