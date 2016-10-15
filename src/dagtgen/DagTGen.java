package dagtgen;

import java.awt.Desktop;
import static java.awt.SystemColor.desktop;
import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 *
 * @author tusifpk
 */
public class DagTGen {
    public static double rand;
    public static int height=0;
//    public static long seed = System.currentTimeMillis();
//    static Random rand = new Random(seed); 

    @SuppressWarnings("empty-statement")
    public static void main(String[] args) throws IOException {
        long seed = System.currentTimeMillis();
        Random rand = new Random(seed);
        // TODO code application logic here
        int levels = 0;
        int width = 0;

        double wdag = 50;// Average Computation Cost of DAG, it is set randomly
        double minCompCost = 0; // Minimum computation cost according to formula wtask*(1-(beta/2))
        double maxCompCost = 0; // Maximum computation cost according to formula wtask*(1+(beta/2))

        int[] v = {10, 20, 40, 60, 80, 100}; //set of tasks,
        int[] m = {3, 5, 10, 15, 20, 25}; // set of machines
        double[] b = {0.1, 0.25, 0.5, 0.75, 1.0};// changes the range % of comp cost . Low % = Almost equal on all processors
        double[] a = {0.5, 1.0, 2.0}; // shape parameter
        // Comm to Comp Ratio :
        // Lower value of CCR means Comp intensive
        // Upper value of CCR means Comm intensive
        double[] ccr = {0.1, 0.5, 1.0, 5.0, 10.0};
        
        int[] nodeAtLevel;
        int[] anodeAtLevel;
        int sumOfWidthatLevels;

        /*  Rang Percentage of Comp Costs:
            Low percentage means that the expected comp cost
            of a task is almost same on any given processor.
         */
        double beta = b[4];
        double alpha = a[1];
        int mach = m[0];
        int task = v[1];
        int out_deg = 2;
        int heightOfDag = 0;
        int widthOfDag = 0;
        double comp_cost = wdag / ccr[4];
        double comm_cost = wdag * ccr[4];
        double testRand;
        System.out.println("tasks = " + task);
        System.out.println("machines = " + mach);
        System.out.println("wdag = " + wdag);
        System.out.println("out_deg = " + out_deg);
        System.out.println("alpha = " + alpha);
        System.out.println("beta = " + beta);
        System.out.println("CCR = " + ccr[3]);
        System.out.println("Comp Cost = " + comp_cost);
        System.out.println("Comm Cost = " + comm_cost);
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++");

        double[] wtask = new double[task];// Array of average comp cost of task
        // Calculating Average Comp Cost of each task
        //System.out.println("++++++Calculating average comp cost of each task++++++");
        //System.out.println("Results : ");
        for (int i = 0; i < task; i++) {
            //wtask[i] = (double)(1 + Math.random()*(2*wdag));
            wtask[i] = RandWithin(0.1, (2 * wdag));
//            System.out.println("Average Comp Cost of task_"+i+":\t"+wtask[i]);
        }

        // Calculating comp cost of each task on each machiner randomly
        //System.out.println("++++++Calculating comp cost of each task on each machine++++++");
        double[][] ECTMatrix = new double[task][mach];
        for (int j = 0; j < task; j++) {
            for (int k = 0; k < mach; k++) {
                minCompCost = wtask[j] * (1 - (beta / 2));
                maxCompCost = wtask[j] * (1 + (beta / 2));
                //System.out.println("Minimum Computation Cost of Task["+j+"] = "+ minCompCost);
                //System.out.println("Maximum Computation Cost of Task["+j+"] = "+ maxCompCost);
                ECTMatrix[j][k] = RandWithin(minCompCost, maxCompCost);
//                System.out.println("Comp cost of Task_"+j+" on machine "+k+" = "+ ECTMatrix[j][k]);
            }
        }

//        System.out.println("Calculation height of DAG or Number of Levels ");
//        heightOfDag = (int) Math.ceil(RandWithin(3, Math.sqrt(task)/alpha));
        System.out.println("Math.sqrt(task)/alpha) = " + Math.sqrt(task)/alpha);
//        testRand = rand.nextDouble()*15;
//        System.out.println(testRand);
//        heightOfDag = (int)(rand.nextDouble() + (Math.sqrt(task)/alpha));
        do{heightOfDag = (int)Math.abs((rand.nextDouble()*10)-5 + (Math.sqrt(task)/alpha));
          }while(heightOfDag<3); 
        nodeAtLevel = new int[heightOfDag];
        anodeAtLevel = new int[heightOfDag];
        //sumOfWidthatLevels = nodeAtLevel.
        System.out.println("heightOfDag = "+heightOfDag);
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++");
        
        for(int h=0;h<heightOfDag;h++){
//            System.out.println("##############FIRST ITERATION###############");
            if(h ==0 || h == (heightOfDag-1)){
                System.out.println("Level No : "+h+"\t width : 1");
                nodeAtLevel[h] = 1;
            }
            else{
                
                System.out.print("Level No : "+h+"\t width : ");
                do{widthOfDag = (int)Math.abs((rand.nextDouble()*10)-5 + (Math.sqrt(task)*alpha));
                }while(widthOfDag==1 || widthOfDag < 1);
                nodeAtLevel[h] = widthOfDag;
                System.out.println(widthOfDag);
//                System.out.println(" "+nodeAtLevel[h]);
            }
            
//else{do{widthOfDag = (int)Math.abs((rand.nextDouble()*10)-5 + (Math.sqrt(task)*alpha));}
//                }while(widthOfDag<1);
//                System.out.println("Level No : "+heightOfDag+"\t width : "+widthOfDag);}
 
//            if(h!=0 && h!= heightOfDag){
//                do{widthOfDag = (int)Math.abs((rand.nextDouble()*10)-5 + (Math.sqrt(task)*alpha));
//                }while(widthOfDag<1);
//                System.out.println("Level No : "+heightOfDag+"\t width : "+widthOfDag);
//            }
//            else{
//                    System.out.println("1st Level No : "+heightOfDag+"\t width : 1");
//                
//            }
                 
//            randomNumber(task);
        }
        System.out.println("tasks = "+task + " " + sumOfArray(nodeAtLevel));
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++");
//        for(int l=0;l<nodeAtLevel.length;l++){
//            if(l ==0 || l == (nodeAtLevel.length-1)){
//                System.out.println("nodeAtLevel : " + nodeAtLevel[l] + " = 1" );
//                anodeAtLevel[l] = 1;
//            }
//            else{
//                anodeAtLevel[l] = (int)(((double)nodeAtLevel[l]/(double)sumOfArray(nodeAtLevel))*task);
//            System.out.println("nodeAtLevel : " + nodeAtLevel[l] + " = " + anodeAtLevel[l]);
//                
//            }
//            
//        }
//        System.out.println("Sum of Array "+sumOfArray(anodeAtLevel));
        int test = 0;
        int addcounter = 0;
        int minuscounter = 0;
        int randomLevelSelection = 0;
        if (sumOfArray(nodeAtLevel) < task) {
            System.out.println("CASE 1 # LESSER");
            addcounter = task - (sumOfArray(nodeAtLevel));
//            System.out.println("Add loop counter : "+addcounter);    
            if (heightOfDag == 3) {
//                System.out.println("Height is 3");
                for (int i = 0; i < addcounter; i++) {
                    nodeAtLevel[1]++;
                }
            }
            else if(heightOfDag!=3){
//                System.out.println("Height is not 3 . Select "+addcounter+" random levels");
                for(int j=0;j<addcounter;j++){
                    randomLevelSelection = randomNumber(heightOfDag - 2);
//                    System.out.println(j+" random Level Selection : "+randomLevelSelection);
                    nodeAtLevel[randomLevelSelection]++;
                }
            }
           
            
        } else if (sumOfArray(nodeAtLevel) > task) {
            System.out.println("CASE 2 # GREATER");
            minuscounter = sumOfArray(nodeAtLevel)-task;
//            System.out.println("total deletion to be done = "+minuscounter);
            for(int s=0;s<minuscounter;s++){
//                do{randomLevelSelection = randomNumber(heightOfDag - 2);
//                }while(nodeAtLevel[randomLevelSelection]==1);
//                
//                System.out.println("Maximum nodes at level :"+maxIndex(nodeAtLevel)); 
//                System.out.println(s+" random Level Selection : "+randomLevelSelection);
                nodeAtLevel[maxIndex(nodeAtLevel)]--;
            }
        } else if (sumOfArray(nodeAtLevel) == task) {
            System.out.println("CASE 3 # ALL EQUAL");
            
        }
        
        
//
//        do{
//            for(int j=1;j<(nodeAtLevel.length-1);j++){
//            System.out.print("nodeAtLevel = "+nodeAtLevel[j]+" = ");
//            anodeAtLevel[j] = (int) Math.ceil(0.50*nodeAtLevel[j]);
//            System.out.print(" # anodeAtLevel = "+anodeAtLevel[j]);
//            nodeAtLevel[j] = nodeAtLevel[j] - anodeAtLevel[j];
//            System.out.println(" # nodeAtLevel = "+nodeAtLevel[j]);
//        }
//        }while(sumOfArray(anodeAtLevel)==(task-2));
        
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++");
        
        
//        System.out.println("Calculation width of DAG ");
//        widthOfDag = (int) Math.ceil(Math.sqrt(task) * alpha);
//        System.out.println("Math.sqrt(task) * alpha = "+Math.sqrt(task) * alpha);
//        widthOfDag = (int)(rand.nextDouble() + (Math.sqrt(task) * alpha));
//        do{widthOfDag = (int)Math.abs((rand.nextDouble()*10)-5 + (Math.sqrt(task)*alpha));
//        }while(widthOfDag<1);
//        System.out.println("widthOfDag = "+widthOfDag);
//        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++");
        
//        System.out.println("Distribute "+task+ " tasks on " +heightOfDag+" levels" );
//        System.out.println("At first and last level we have only one node each");
//        System.out.println("So number of level and nodes left are as under");
//        System.out.println("Number of nodes = "+(task-2));
//        System.out.println("Number of levels = "+(heightOfDag-2));
//        Covert double array "ECTMatrix" to Matrix
//        Matrix ECT = new Matrix(ECTMatrix);
//        ECT.show();
//        Depth/Height/No of levels of the DAG is randomly generated from uni-dist with
//        levels = (int)Math.ceil(RandWithin(3, Math.sqrt(v[4]/a[0])));
//        System.out.println("levels = "+ levels);
//        width = (int)Math.ceil(Math.sqrt(v[4])/a[0]);
//        System.out.println("width = "+ width);
//        Process p = Runtime.getRuntime()
//        .exec("rundll32 url.dll,FileProtocolHandler D:\\MyFiles\\Softwares\\briss-0.9\\briss-0.9\\briss-0.9.exe");
        
        for(int h=0;h<nodeAtLevel.length;h++){
            System.out.println("No of Nodes at Level "+h+" : "+nodeAtLevel[h]);
        }
        
        System.out.println("New Total : "+sumOfArray(nodeAtLevel));
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++");
//        
        int[][] levelNumOfNode = new int[task-1][task-1];
        int nodecounter = 0;
        for(int x=0;x<task;x++){
            for(int y=0;y<nodeAtLevel[x];y++){
                levelNumOfNode[x][y] = nodecounter++;
                System.out.println("levelNumOfNode["+x+"]["+y+"] = "+levelNumOfNode[x][y]);
            }
        }
        
        
        
        
        
//        int[][] nodeAtMlevels = new int[task][task];
//        for(int x=0;x<task;x++){
//            for(int y=0;y<task;y++){
//                if(x>=y){nodeAtMlevels[x][y] = 1;}
//                System.out.print(nodeAtMlevels[x][y]);
//            }
//        }
        
        
        System.out.println("Done.");
    }

    public static double RandWithin(double min, double max) {
        double num;
        num = min + Math.random() * ((max - min) + 1);
//        System.out.println(num);
        return num;
    }

    public static int randomNumber(int num){
        Random rn = new Random();
        int answer = rn.nextInt(num) + 1;
//        System.out.println(answer);
        return answer;
    }
    //‪C:\briss-0.9\briss-0.9.exe
    //Runtime.getRuntime().exec("‪C:\\briss-0.9\\briss-0.9.exe",null,new File("‪C:\\briss-0.9\\"));
    public static int sumOfArray(int[] A){
        int i = 0;
        int sum = 0;        // Create a separate integer to serve as your array indexer.
        while(i < A.length) {   // The indexer needs to be less than 10, not A itself.
           sum += A[i];   // either sum = sum + ... or sum += ..., but not both
           i++;}
        return sum;// You need to increment the index at the end of the loop.   }
    }
    
    public static int maxIndex(int[] array) {
        int largest = array[0], index = 0;
        for (int i = 1; i < array.length; i++) {
            if (array[i] >= largest) {
                largest = array[i];
                index = i;
            }
        }
        return index;
    }
}
