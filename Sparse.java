//Guanchen Liu
//1521926
//gliu13@ucsc.edu
import java.util.Scanner;
import java.io.*;
public class Sparse {
    public static void main(String[] args) throws IOException{
        String Output="";
        int siz = 0;
        int num1 = 0;
        int num2 = 0;
        int row = 0;
        int column = 0;
        double x = 0;
        if(args.length !=2){
            System.out.println("Please enter 2 file!!!");
            System.exit(1);
        }
        //File I/O
        Scanner sc = new Scanner(new File(args[0]));
        PrintWriter out = new PrintWriter(new FileWriter(args[1]));
        if(sc.hasNextLine()){
            String line = sc.nextLine()+" ";
            String[] temp = line.split("\\s+");
            siz = Integer.parseInt(temp[0]);
            num1 = Integer.parseInt(temp[1]);
            num2 = Integer.parseInt(temp[2]);

        }
        Matrix A = new Matrix(siz);
        Matrix B = new Matrix(siz);
        //Put stuff in to Matrix A
        for(int i = 0; i < num1; i++){
            if(sc.hasNextInt()){
                row = sc.nextInt();
                column = sc.nextInt();
            }
            if(sc.hasNextDouble()){
                x = sc.nextDouble();
                A.changeEntry(row,column,x);
            }
        }
        //Put stuff in to Matrix B
        for(int i = 0; i < num2; i++){
            if(sc.hasNextInt()){
                row = sc.nextInt();
                column = sc.nextInt();
            }
            if(sc.hasNextDouble()){
                x = sc.nextDouble();
                B.changeEntry(row,column,x);
            }
        }
        //Print the out put
        Output += "A has " + A.getNNZ() + " non-zero entries:\n";
        Output += A.toString()+"\n";
        Output += "B has " + B.getNNZ() + " non-zero entries:\n";
        Output += B.toString()+"\n";
        Output += "(1.5)*A =\n";
        Output += A.scalarMult(1.5).toString()+"\n";
        Output += "A+B =\n";
        Output += A.add(B).toString() + "\n";
        Output += "A+A =\n";
        Output += A.add(A).toString() + "\n";
        Output += "B-A =\n";
        Output += B.sub(A).toString() + "\n";
        Output += "A-A =\n";
        Output += A.sub(A).toString() + "\n";
        Output += "Transpose(A) =\n";
        Output += A.transpose().toString() + "\n";
        Output += "A*B =\n";
        Output += A.mult(B).toString() + "\n";
        Output += "B*B =\n";
        Output += B.mult(B).toString() + "\n";
        out.println(Output);
        sc.close();
        out.close();
    }
}
