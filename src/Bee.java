import java.util.Scanner;
import java.util.ArrayList;

public class Bee {
    public Bee(int count , int size_of_forest, int width , int height){ //конструктор
        this.count_of_groups = count;
        this.forest = new int[size_of_forest][size_of_forest];
        this.forest[height][width] = 1;
        this.height = height;
        this.width = width;
        this.size_of_forest = size_of_forest;
    }
    public void print_forest(){
        for(int i = 0 ; i < forest.length; i++){
            for(int j = 0 ; j < forest.length; j++){
                System.out.print(forest[i][j] + " ");
            }
            System.out.println();
        }
    }
    private volatile int count_of_groups; // кількість груп бджіл
    private volatile int[][] forest; // згенерований ліс (матриця)
    private volatile int height;
    private volatile int width;
    private volatile int size_of_forest;
    private ArrayList<Thread> threads = new ArrayList<Thread>();
    public void creat_threads(){
        for (int k = 0; k < count_of_groups; k++) {
            Thread t = new Thread() {
                public void run() {
                    try {
                        for (int i = 0; i < size_of_forest; i++) {
                            int row = next();
                            if (!isInterrupted()) {
                                for (int j = 0; j < size_of_forest; j++) {
                                    System.out.println(Thread.currentThread().getName()+ " " + row + " " + j );
                                    if (forest[row][j] == 1) {
                                        System.out.println("Found!" + row + " " + j + " " + Thread.currentThread().getName());
                                    }
                                }
                            } else {
                                throw new InterruptedException();
                            }
                        }
                    } catch(InterruptedException e){
                        Thread.currentThread().interrupt();
                    }
                }
            };
            threads.add(t);
            t.start();
        }
    }
    private int count = -1;
    public int next(){
        count++;
        if( count < size_of_forest){
            return count;
        }
        else{
            for(Thread thread : threads)
            {
                thread.interrupt();
            }
            return 0;
        }
    }
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.println("Enter the number of groups of bees:");
        int count = in.nextInt();
        System.out.println("Enter the size of the forest:");
        int size = in.nextInt();
        in.close();
        //генеруємо рандомне розміщення Вінні Пуха
        int width = (int)(Math.random()*(size));
        int height = (int)(Math.random()*(size));
        Bee test = new Bee(count , size, width, height);
        test.print_forest();
        test.creat_threads();

    }

}

