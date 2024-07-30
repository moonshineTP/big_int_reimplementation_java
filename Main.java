import java.util.Scanner;
import mainPack.bigNum;

public class Main {
    public static void main(String[] args){
        Scanner TP = new Scanner(System.in);
        String a = TP.nextLine();
        String b = TP.nextLine();
        bigNum u = new bigNum();
        u.setALL(a);
        bigNum v = new bigNum();
        v.setALL(b);
        long startTime = System.nanoTime();
        System.out.println(bigNum.product(u, v).getNUM());
        long endTime = System.nanoTime();
        System.out.printf("Time taken: %.12f seconds",(endTime - startTime) / 1000000000f); 
        TP.close();
    }
}
