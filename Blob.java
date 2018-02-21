/*
 * Blob.java
 * ------------------------------------------------
 * Helper class that defines a Blob of `mass` pixels.
 * 
 * Compilation: javac-introcs Blob.java
 * Execution:   java-introcs Blob
 */
public class Blob{
    private int mass;         //number of pixels in this Blob
    public double sumOfMassX; //sum of the x-positions of all pixels
    public double sumOfMassY; //sum of the y-positions of all pixels
    
    //Blob constructor
    public Blob(){
        mass = 0;
        sumOfMassX = 0;
        sumOfMassY = 0;
    }
    
    //Adds pixel at position (i, j) to this Blob
    public void add(int i, int j){
        mass++;
        sumOfMassX = sumOfMassX + i;
        sumOfMassY = sumOfMassY + j;
    }
    
    //Returns the number of points in the Blob
    public int mass(){
        return mass;
    }
    
    //Returns the distance from this Blob to Blob `that`
    public double distanceTo(Blob b){   
        double centerMassX = (sumOfMassX/mass) - (b.sumOfMassX/b.mass() );
        double centerMassY = (sumOfMassY/mass) - (b.sumOfMassY/b.mass() );
        centerMassX = centerMassX * centerMassX;
        centerMassY = centerMassY * centerMassY;
        
        return Math.sqrt( (centerMassX + centerMassY) );
    }
    
    //Formats relevant information from the Blob and returns it as a string
    public String toString(){
        return String.format("%3d  (%8.4f,  %8.4f)", mass,
                             (sumOfMassX/mass), (sumOfMassY/mass) );
    }
    
    public static void main(String[] args){
        Blob b = new Blob();
        Blob p = new Blob();
        for( int i = 0; i < 10; i++)
        {b.add(i, 1);
        p. add(1, i);}
        
        StdOut.println(b);
        StdOut.println(p);
        StdOut.println();
        StdOut.println(b.distanceTo(p) );
    }
}