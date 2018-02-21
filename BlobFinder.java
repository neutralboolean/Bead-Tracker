/*
 * BlobFinder.java
 * -------------------------------------------------
 * Helper class to find `Blob`s in a picture.
 * 
 * Takes 3 command-line arguments:
 * int P, minimum number of pixels in each Bead;
 * double tau, the minimum luminance of each pixel to be added to a Blob;
 * file-path of the frames to be tracked through
 * 
 * Makes use of Blob.java.
 * 
 * Compilation:   javac-introcs BlobFinder.java
 * Execution:     java-introcs BlobFinder
 * 
 * Example executions:
 * % java-introcs BlobFinder 25 180.0 run_1/frame00000.jpg
 */
public class BlobFinder{
    private double compareTau;   //minimum luminance to be added to a Blob   
    private int P;               //minimum pixels to become a Bead
    
    private Node blobList;       //linked-list of Blob objects
    private Node listHead;       //reference to first Node in blobList
    private int listCount;       //number of items in blobList
    
    Blob[] beadList;
    private boolean[][] marked;  //holds which pixels of `pic` have been marked
    
    Picture pic;
    
    //helper/wrapper class for Blob objects
    private class Node{
        Blob blob;
        Node next;
        
        Node(Blob b){
            blob = b;
        }
    }
    //constructor
    public BlobFinder(Picture picture, double tau){
        pic = picture;
        compareTau = tau;
        marked = new boolean[picture.width()][picture.height()];
        listCount = 0;
    }
    
    //Implements a recursive, depth-first search to add pixels to Blob
    private void dfs(Blob b, int row, int col){
        if ( row >= pic.height() || col >= pic.width()) { return; }
        else if( row < 0 || col < 0 ){ return; }
        
        if(marked[col][row] == false){
            marked[col][row] = true;
            double lum = Luminance.lum( pic.get(col, row) );
            if( lum >= compareTau ){
                b.add(col, row);
            }
            else{
                return;
            }
            
            dfs(b, row-1, col);
            dfs(b, row+1, col);
            dfs(b, row, col-1);
            dfs(b, row, col+1);
        }
    }
    
    //Finds blobs in the picture and populates `blobList`
    //Makes use of dfs()
    private void findBlobs(){
        //loops through each pixel of the picture
        for( int col = 0; col < pic.width(); col++){
            for ( int row = 0; row < pic.height(); row++){
                Blob b = new Blob();
                
                if(marked[col][row]) { continue; }
                
                dfs(b, row, col);
                if( b.mass() > 0 ){
                    if(listHead == null) {
                        listHead = new Node(b);
                        
                        blobList = listHead;
                        listCount++;
                    }
                    else{
                        blobList.next = new Node(b);
                        blobList = blobList.next;
                        listCount++;
                    }
                }
            }
        }
    }
    
    //Returns the number of blobs with mass greater than `P`
    //Makes use of findBlobs() if blobList is uninitialized
    private int countBeads(int P){
        int beads = 0;
        
        if(blobList == null) { findBlobs(); }

        Node iterator = listHead;
        
        while(iterator != null){
            if( iterator.blob.mass() >= P ) { beads++; }
            iterator = iterator.next;
        }
        
        return beads;
    }
    
    //Returns an array of `Blob`s with mass greater than `P` from the picture
    //Makes use of countBeads()
    public Blob[] getBeads(int P){
        //StdOut.println("In `getBeads()`");
        
        int count = countBeads(P);
        beadList = new Blob[count];
        
        int i = 0;
        for (Node b = listHead; b != null; b = b.next){
            if (b.blob.mass() >= P){
                beadList[i] = b.blob;
                i++;
            } 
        }

        return beadList;
    }
    
    public static void main(String[] args){
        int P = Integer.parseInt(args[0]);
        double tau = Double.parseDouble(args[1]);
        Picture picture = new Picture(args[2]);
        BlobFinder bf = new BlobFinder(picture, tau);
        
        StdOut.println();
        Blob[] b = bf.getBeads(P);
        StdOut.println(bf.countBeads(P) + " Beads:");
        for (Blob blob : b){
            StdOut.println(blob);
        }
        
        StdOut.println();
        
        StdOut.println(bf.listCount + " Blobs:");
        for(Node n = bf.listHead; n != null; n = n.next){
            StdOut.println(n.blob);
        }
    }
}