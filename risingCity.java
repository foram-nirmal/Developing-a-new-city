//package buildings;
import java.io.*;
import java.util.*;
//import buildings.RBT.Node;

public class risingCity {
    static minheap heapnode = new minheap(); //minheap class object
    static RBT rbtnode = new RBT(); //RBT class object
    static BufferedWriter out;
    static int count = 0;
    static boolean first = false; //if printing in range, set this variable to false initially

    public static void main(String[] args) throws IOException {
        String path = args[0];
        File f = new File(path); //input file
        Scanner sc = new Scanner(f);
        String operation[] = null; //putting the operations taken from input file into a string array
        minheapProp currentbldg = null; //building on which currently work is progressing
        int globalcounter = 0; //global counter maintained for consistency with input order
        int  localcounter = 0; // local counter for maintaining that no task is executed for more than 5 days, this is reset everytime it reaches 5
        String nextLine = null;
        boolean initiated = false; //boolean value stored to check if task has started or not
        File fout = new File("output_file.txt"); //output file
        PrintStream p = new PrintStream(fout);
        System.setOut(p);
        while(rbtnode.root != rbtnode.NIL || sc.hasNextLine()){ //keep on looping until either RBT becomes null, or there is no more input to be scanned from the input file
            if(currentbldg == null && initiated){
                if(heapnode.size != 0){
                    currentbldg = heapnode.arr[0];
                    heapnode.delete_heap();
                }
            }
            if(currentbldg != null){ //increase the local counter each time this condition is satisfied
                currentbldg.executed_time++;
                localcounter++;
            }
            if(nextLine != null ||sc.hasNextLine()){
                if(nextLine == null)
                    nextLine = sc.nextLine();
                operation = nextLine.split(": "); //split the operation into two parts - global counter variant and operation to be performed
                if(operation[0].equals(String.valueOf(globalcounter))) { //execute the command only once global counter equals the first field of every line
                    executeOperation(operation);
                    nextLine = null;
                    initiated = true;
                }
            }
            if(currentbldg != null && currentbldg.executed_time == currentbldg.total_time){ //when the work for a bldg is completed
                System.out.println("("+currentbldg.buildingNum+","+(globalcounter) +")");
                String s = "(" + currentbldg.buildingNum +","+globalcounter+ ")";
                rbtnode.RBT_remove(currentbldg.buildingNum); //remove from RBT
                localcounter = 0;
                currentbldg = null;
            }
            if(localcounter == 5 && currentbldg != null){ //only once current task is nothing or a bldg has completed maximum permissible 5 days at a stretch
                heapnode.insert_heap(currentbldg); //insert this currently worked upon bldg into the minheap
                localcounter = 0; //reset local counter to 0 since maximum permissible days of 5 is achieved
                currentbldg = null;
            }
            globalcounter++; //global counter needs to be updated regularly
        }
    }

    public static void insert(int n1, int n2) {
        RBT_node node = new RBT_node(n1,n2);
        RBT.Node rnode = rbtnode.new Node(node);
        rbtnode.RBT_insert(rnode);
        minheapProp mheap = new minheapProp(n1,n2);
        heapnode.insert_heap(mheap);
        node.correspondingMinHeapnode = mheap;
        mheap.correspondingRBTnode = node;
    }

    public static RBT.Node find(RBT.Node node, int n1) {
        if(node.node.buildingNum == n1 || node == rbtnode.NIL)
            return node;
        if(node.node.buildingNum < n1)
            return find(node.right,n1);
        return find(node.left,n1);
    }

    public static String executeOperation(String[] operation) throws IOException {
        if(operation[1].contains("Insert")) { //insert operation
            int n1=Integer.parseInt(operation[1].substring(operation[1].indexOf("(")+1, operation[1].indexOf(",")));
            int n2=Integer.parseInt(operation[1].substring(operation[1].indexOf(",")+1, operation[1].indexOf(")")));
            RBT.Node copy = find(rbtnode.root,n1);
            if(copy != rbtnode.NIL){
                System.out.println("Duplicate building number");
                System.exit(0);
            }
            else
                insert(n1,n2);
            return "";
        }
        if(operation[1].contains("Print") && operation[1].contains(",") ) { //print for ranges of bldgs
            int n1=Integer.parseInt(operation[1].substring(operation[1].indexOf("(")+1, operation[1].indexOf(",")));
            int n2=Integer.parseInt(operation[1].substring(operation[1].indexOf(",")+1, operation[1].indexOf(")")));
            int c = rangePrint(rbtnode.root,n1,n2);
            if (c==0)
                System.out.print("(0,0,0)");
            first = false;
            System.out.println();
        }
        if(operation[1].contains("Print") && !operation[1].contains(",") ) { //print for one bldg
            int n1=Integer.parseInt(operation[1].substring(operation[1].indexOf("(")+1, operation[1].indexOf(")")));
            printCommand(n1);
            System.out.println();
            return "";
        }
        return "";
    }

    private static String printCommand(int n1) {
        return print(n1);
    }

    private  static void print(int n1, int n2) throws  IOException{
        rangePrint(rbtnode.root,n1,n2);
    }

    public  static int rangePrint(RBT.Node node, int n1, int n2) {

        if (node == rbtnode.NIL)
            return count;
        if (n1 < node.node.buildingNum)
            rangePrint(node.left, n1, n2);

            if (n1 <= node.node.buildingNum && n2 >= node.node.buildingNum) {
                count = 1;
                if (first == false) {
                    String s = "(" + node.node.correspondingMinHeapnode.buildingNum + "," + node.node.correspondingMinHeapnode.executed_time + "," +
                            node.node.correspondingMinHeapnode.total_time + ")";
                    System.out.print("(" + node.node.correspondingMinHeapnode.buildingNum + "," + node.node.correspondingMinHeapnode.executed_time + "," +
                            node.node.correspondingMinHeapnode.total_time + ")");
                    first = true; //turning true for next set to be output with a comma
                } else {
                    String s = ",(" + node.node.correspondingMinHeapnode.buildingNum + "," + node.node.correspondingMinHeapnode.executed_time + "," +
                            node.node.correspondingMinHeapnode.total_time + ")";
                    System.out.print(",(" + node.node.correspondingMinHeapnode.buildingNum + "," + node.node.correspondingMinHeapnode.executed_time + "," +
                            node.node.correspondingMinHeapnode.total_time + ")");
                }

            }
            if (n2 > node.node.buildingNum)
                rangePrint(node.right, n1, n2);
            return count;
        }
        
    private static String print(int n1){
        RBT.Node node = find(rbtnode.root,n1);
        if(node != rbtnode.NIL){
            String s="("+node.node.correspondingMinHeapnode.buildingNum+","+node.node.correspondingMinHeapnode.executed_time +","+
                   node.node.correspondingMinHeapnode.total_time+")";
            System.out.print(s);
        }
        else
            System.out.print("(0,0,0)");
        return "";
    }
}
