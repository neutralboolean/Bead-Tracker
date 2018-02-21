/*
 * Avogadro.java
 * ------------------------------------------
 * Takes input (in the form of displacements) from standard input to
 * estimates Boltzmann's constant and Avogadro's number.
 * 
 * Compilation: javac-introcs Avogadro.java
 * Execution:   java-introcss Avogadro
 * 
 * Example executions:
 * % java-introcs Avogadro < displacements-run_1.txt
 * % java-introcs BeadTracker 25 180.0 25.0 run_1\*.jpg | java Avogadro
 */
public class Avogadro{
    //physics constants
    static final double ABSOLUTETEMP = 297.0;
    static final double WATERVISCOSITY = 9.135 * Math.pow(10, -4);
    static final double BEADRADIUS = 0.5 * Math.pow(10, -6);
    static final double GASCONSTANT = 8.31457;
    static final double PIXCONVERT = (0.175 * Math.pow(10, -6 ));
    
    public static void main(String[] args){
        double[] dArray = StdIn.readAllDoubles();
        double D = 0.0;
        double j = 0.0;
        for (double d : dArray){
            double r = d * PIXCONVERT;
            D = D + (r * r);
            j = j + 1.0;
        }
        D = ( D/ (2 * j) );
        double boltzmann = (D * 6 * Math.PI * WATERVISCOSITY * BEADRADIUS) /
            ABSOLUTETEMP;
        double avogadro = GASCONSTANT / boltzmann;
        
        StdOut.printf("Boltzmann constant = %6.4e\r\n", boltzmann);
        StdOut.printf("Avogadro's number  = %6.4e\r\n", avogadro);
    }
}