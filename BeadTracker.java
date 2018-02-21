/**
 * BeadTracker.java
 * ------------------------------------
 * Finds `Blob` objects and tracks their displacements through a number of frames.
 * 
 * Takes 4 command-line arguments:
 * int P, minimum number of pixels in each Bead;
 * double tau, the minimum luminance of each pixel to be added to a Blob;
 * double delta, maximum distance between time-step
 *                      to be considered for tracking;
 * file-path of the frames to be tracked through
 * 
 * Makes use of BlobFinder.java and Blob.java.
 * 
 * Compilation:  javac-introcs BeadTracker.java
 * Execution:    java-introcs BeadTracker
 * 
 * Example executions:
 * % java-introcs BeadTracker 25 180.0 25.0 run_1\*.jpg
 */
public class BeadTracker{
    public static void main(String[] args){
        int  P = Integer.parseInt(args[0]);
        double tau = Double.parseDouble(args[1]);
        double delta = Double.parseDouble(args[2]);
        BlobFinder bf = new BlobFinder(new Picture(args[3]), tau);
        Blob[] oldBeads = bf.getBeads(P);

        for (int i = 4; i < args.length; i++){
            BlobFinder newBF = new BlobFinder(new Picture(args[i]), tau);
            Blob[] beads = newBF.getBeads(P);
            for (Blob b : beads){              //
                double closest = -1.0;
                if (b == null){ continue; }
                for (Blob b2 : oldBeads){
                    if (b2 == null){ continue; }
                    double temp = b.distanceTo(b2);
                    
                    if (closest == -1.0){ closest = temp;}
                    if (temp < delta){
                        if (temp < closest){ closest = temp; }
                    }
                }
                if (closest > 0 && closest < delta) StdOut.printf("%6.4f\r\n", closest);
            }
            StdOut.println();
            oldBeads = beads;
        }
    }
}