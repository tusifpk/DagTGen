/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dagtgen;

/**
 *
 * @author Bhatti
 */
public class Matrix {
    private final int M;             // number of rows
    private final int N;             // number of columns
    private final double[][] data;   // M-by-N array
    public static double makespan=0.0;
    //rivate final static int k;
    // create M-by-N matrix of 0's
    public Matrix(int M, int N) {
        this.M = M;
        this.N = N;
        data = new double[M][N];
    }
    // create matrix based on 2d array
    public Matrix(double[][] data) {
        M = data.length;
        N = data[0].length;
        this.data = new double[M][N];
        for (int i = 0; i < M; i++)
            System.arraycopy(data[i], 0, this.data[i], 0, N);
    }
    public void setMakespan(double makespan){
        this.makespan = makespan;
    }
    // copy constructor
    private Matrix(Matrix A) { this(A.data); }
    // create and return a random M-by-N matrix with values between 0 and 1
    // create and return a random M-by-N matrix with values between 0 and 1
    public static Matrix random(int M, int N) {
        int i,j,k =0;
        Matrix A = new Matrix(M, N);
        for (i =0 ; i < M; i++){
            for (j = 0; j < N; j++){
                k = (int) (1 + Math.random()*20);
                if(k!=0)
                A.data[i][j] = k;
                else{A.data[i][j] = (int)(1 + Math.random()*20);}
            }
        }
        
        return A;
    }
    public static void nullMatrix(Matrix B){
        int i,j;
        for (i =0 ; i < B.M; i++){
            for (j = 0; j < B.N; j++){
                B.data[i][j] = 0;
            }
        }
        
    }
    //////////////////////////////////////
    //     MaxMin Algorithm 
    //////////////////////////////////////
    // Get specific row of the matrix
    public static double[][] getRow(Matrix A, int rowNo){
        double[][] row = new double[A.M][A.N];
        for(int i=0;i<A.N;i++){
            if(A.data[rowNo][i]!=0)
            {row[rowNo][i] = A.data[rowNo][i];
            }
            System.out.println(row[rowNo][i]);
        }
    return row;
    }
    // Get minimum of the specific row of the matrix
    public static double rowmin(Matrix A, int rowNo){
        double min = A.data[rowNo][0];
        double ram=0;
        for(int i=0;i<A.N;i++){
            if(A.data[rowNo][i]!=0){
                //min = A.data[i][j];
                ram = A.data[rowNo][i];
                if(ram <= min){min = ram;}
            }
        }
        return min;
    }
    // get maximum of the minimum of all the rows
    public static double max(Matrix A){
        int i,j;
        double max = 0;
        double check = 0;
        
        double[] minArray = new double[A.N];
        for(i=0;i<A.N;i++){
            minArray[i] = Matrix.rowmin(A, i);
            if(minArray[i]>max)
            {
                max = minArray[i];
            }
        }
        return max;
    }
    
    public static void maxmin(Matrix A){
        double[] m = new double[A.N];
        m[0] = 0;
        //A.show();
        Matrix C = new Matrix(A.M, A.N);
        C = Matrix.copy(A);
        //C.show();
       
        int M = A.M;
        int N = A.N;
        for(int a=0; a<M ; a++){
            double max = Matrix.max(A);
            System.out.println("Maximum value is : "+max);
            int task=0;
            int machine=0;
            double task_ect=0;
            for (int i = 0; i < M; i++){
                for (int j = 0; j < N; j++){
                    if(A.data[i][j] == max){
                        System.out.println("A["+(i+1)+"]"+"["+(j+1)+"]");
                        task = i+1;
                        machine = j+1;
                        };
                }
            }
            System.out.println("Executing task t"+task+" ---> m"+machine);
            //System.out.println();
            m[machine-1] = m[machine-1] + C.data[task-1][machine-1];
            System.out.println("Resultant ECT Matrix:");
            for (int i = 0; i < M; i++){
                for (int j = 0; j < N; j++){
                    if(i==task-1){A.data[i][j]=0;}
                    if((j==machine-1) & (i!=task-1) & (A.data[i][j]!=0)) {
                    A.data[i][j] = C.data[i][j] + max;}
                }
            }
            //A.show();
            for(int i=0;i<A.N;i++){
                System.out.println("Makespan of Machine m"+i+" : "+m[i]);
            }
        }   
    }
    
    
    //////////////////////////////////////
    //     MinMin Algorithm 
    //////////////////////////////////////
    public static double matrixMin(Matrix A){
        double min = 9999999.999999;
        //if(A.data[0][0]!= 0){min = A.data[0][0];}
        int M = A.M;
        int N = A.N;
        double ram=0;
        for (int i = 0; i < M; i++){
            for (int j = 0; j < N; j++){
                if(A.data[i][j]!=0){
                    //min = A.data[i][j];
                    ram = A.data[i][j];
                    if(ram <= min){min = ram;}
                }
            }
        }
        //System.out.println("=============================================");
        //System.out.print("Task with the minimum ECT is : " + min+" at ");
        return min;
    }
    public static void minmin(Matrix A){
        double[] m = new double[A.N];
        m[0] = 0;
        //A.show();
        Matrix C = new Matrix(A.M, A.N);
        C = Matrix.copy(A);
        int M = A.M;
        int N = A.N;
        for(int a=0; a<M ; a++){
            double min = matrixMin(A);
            int task=0;
            int machine=0;
            double task_ect=0;
            for (int i = 0; i < M; i++){
                for (int j = 0; j < N; j++){
                    if(A.data[i][j] == min){
                        //System.out.println("A["+(i+1)+"]"+"["+(j+1)+"]");
                        task = i+1;
                        machine = j+1;
                        };
                }
            }
            //System.out.println("Executing task t"+task+" ---> m"+machine);
            //System.out.println();
            m[machine-1] = m[machine-1] + C.data[task-1][machine-1];
            //System.out.println("Resultant ECT Matrix:");
            for (int i = 0; i < M; i++){
                for (int j = 0; j < N; j++){
                    if(i==task-1){A.data[i][j]=0;}
                    if((j==machine-1) & (i!=task-1) & (A.data[i][j]!=0)) {
                    A.data[i][j] = C.data[i][j] + min;}
                }
            }
            //A.show();
            double max = 0;
            for(int i=0;i<A.N;i++){
                if(m[i]>max){
                    max = m[i];
                    makespan = max;
                }
                //System.out.println("Makespan = "+max);
            }
        }   
    }
    ////////////////////////////////////////////////////////////////////////////////////
    public static Matrix copy(Matrix A){
        int i, j;
        Matrix B = new Matrix(A.M,A.N);
        for (i = 0; i < A.M; i++){
            for (j = 0; j < A.N; j++){
               
                    B.data[i][j] = A.data[i][j];
                }
        }
        return B;
    }
    // create and return the N-by-N identity matrix
    // swap rows i and j
    private void swap(int i, int j) {
        double[] temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }
    // create and return the transpose of the invoking matrix
    public Matrix transpose() {
        Matrix A = new Matrix(N, M);
        for (int i = 0; i < M; i++)
            for (int j = 0; j < N; j++)
                A.data[j][i] = this.data[i][j];
        return A;
    }
    // return C = A + B
    public Matrix plus(Matrix B) {
        Matrix A = this;
        if (B.M != A.M || B.N != A.N) throw new RuntimeException("Illegal matrix dimensions.");
        Matrix C = new Matrix(M, N);
        for (int i = 0; i < M; i++)
            for (int j = 0; j < N; j++)
                C.data[i][j] = A.data[i][j] + B.data[i][j];
        return C;
    }
    // return C = A - B
    public Matrix minus(Matrix B) {
        Matrix A = this;
        if (B.M != A.M || B.N != A.N) throw new RuntimeException("Illegal matrix dimensions.");
        Matrix C = new Matrix(M, N);
        for (int i = 0; i < M; i++)
            for (int j = 0; j < N; j++)
                C.data[i][j] = A.data[i][j] - B.data[i][j];
        return C;
    }
    // does A = B exactly?
    public boolean eq(Matrix B) {
        Matrix A = this;
        if (B.M != A.M || B.N != A.N) throw new RuntimeException("Illegal matrix dimensions.");
        for (int i = 0; i < M; i++)
            for (int j = 0; j < N; j++)
                if (A.data[i][j] != B.data[i][j]) return false;
        return true;
    }
    // return C = A * B
    public Matrix times(Matrix B) {
        Matrix A = this;
        if (A.N != B.M) throw new RuntimeException("Illegal matrix dimensions.");
        Matrix C = new Matrix(A.M, B.N);
        for (int i = 0; i < C.M; i++)
            for (int j = 0; j < C.N; j++)
                for (int k = 0; k < A.N; k++)
                    C.data[i][j] += (A.data[i][k] * B.data[k][j]);
        return C;
    }
    // return x = A^-1 b, assuming A is square and has full rank
    public Matrix solve(Matrix rhs) {
        if (M != N || rhs.M != N || rhs.N != 1)
            throw new RuntimeException("Illegal matrix dimensions.");
        // create copies of the data
        Matrix A = new Matrix(this);
        Matrix b = new Matrix(rhs);
        // Gaussian elimination with partial pivoting
        for (int i = 0; i < N; i++) {
            // find pivot row and swap
            int max = i;
            for (int j = i + 1; j < N; j++)
                if (Math.abs(A.data[j][i]) > Math.abs(A.data[max][i]))
                    max = j;
            A.swap(i, max);
            b.swap(i, max);
            // singular
            if (A.data[i][i] == 0.0) throw new RuntimeException("Matrix is singular.");
            // pivot within b
            for (int j = i + 1; j < N; j++)
                b.data[j][0] -= b.data[i][0] * A.data[j][i] / A.data[i][i];
            // pivot within A
            for (int j = i + 1; j < N; j++) {
                double m = A.data[j][i] / A.data[i][i];
                for (int k = i+1; k < N; k++) {
                    A.data[j][k] -= A.data[i][k] * m;
                }
                A.data[j][i] = 0.0;
            }
        }

        // back substitution
        Matrix x = new Matrix(N, 1);
        for (int j = N - 1; j >= 0; j--) {
            double t = 0.0;
            for (int k = j + 1; k < N; k++)
                t += A.data[j][k] * x.data[k][0];
            x.data[j][0] = (b.data[j][0] - t) / A.data[j][j];
        }
        return x;
   
    }
    // print matrix to standard output
    public void show() {
        System.out.println("ECT Matrix :");
        for (int i = 0; i < M; i++) {
            System.out.printf("t"+ (i)+" :");
            for (int j = 0; j < N; j++)
                System.out.printf("%9.6f ", data[i][j]);
            System.out.println();
        }
    }
    
    public static double avgCompCost(Matrix A, int taskId){
        double avg = 0;
        double sum = 0;
        for(int i=0;i<A.N;i++){
            sum = sum + A.data[taskId][i];
        }
        avg = sum/A.N;
        //System.out.println(avg);
        return avg;
    }
    
    public static void avgCompCostArray(Matrix A){
        double[] avgArray = new double[A.M];
        for(int i=0;i<A.M;i++){
            avgArray[i] = avgCompCost(A,i);
            System.out.println(avgArray[i]);
        }
        
    }
    
    public static void rankU(Matrix A, Matrix B){
        
        double avg = 0;
        double temp = 0;
        double max = 0;
        int index = A.M-1;
        double[] rank = new double[A.M];
        rank[index] = avgCompCost(A,index);
        
        // for upward rank calculation we will start index from
        // 8 to 0
        //System.out.println(rank[index]);
        for(int i=index-1;i>=0;i--)
        {
            for(int j=index;j>=0;j--)
            {
                if(B.data[i][j]!=0)
                {
                    //System.out.println(rank[j]);
                    rank[i] = avgCompCost(A,i) + B.data[i][j] + rank[j];
                    System.out.println("rank["+i+"]"+" = avgCompCost(A,"+i+")"+"B.data["+i+"]["+j+"]+" + " rank["+j+"]");
                    System.out.println(rank[i]+" = "+avgCompCost(A,i)+" + "+B.data[i][j]+" + "+rank[j]);
                }
                
            }
        }
        
        for(int k=0; k<rank.length; k++){
            System.out.print(rank[k]+" ");
        }
       
    }
}
