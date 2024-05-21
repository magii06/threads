import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.List;
public class Main {

    public static void main(String[] args) {
        PriorityBlockingQueue<Product> products= new PriorityBlockingQueue<>(100, new ProductCom());

        try {
            ProductThread t1 = new ProductThread("CKThread", "CK.txt", products);
            ProductThread t2 = new ProductThread("GuessThread", "guess.txt", products);
            ProductThread t3 = new ProductThread("TrussardiThread", "trussardi.txt", products);
            t1.start();
            t2.start();
            t3.start();

            List<Thread> threads= new ArrayList<>();
            threads.add(t1);
            threads.add(t2);
            threads.add(t3);

            for(Thread thread : threads){
                thread.join();
            }
        }catch (Exception exception){
            System.out.println("Thre was an error");
            System.exit(1);
        }
        File file= new File("result.txt");
        if(file.exists()){

            System.out.println("File exists");
            System.exit(1);
        }

        PrintWriter output= null;
        try{
            output= new PrintWriter(file);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }

        Iterator<Product> it=products.iterator();
        while(it.hasNext()){
            output.println(it.next()+" ");
        }

        output.close();
    }

}