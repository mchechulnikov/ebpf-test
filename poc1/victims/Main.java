public class Main {
    public static int myFunction(int param) {
        System.out.println("Function called with param: " + param);
        return 42;
    }

    public static void main(String[] args) {
        int i = 0;
        while (i < 5) {
            myFunction(i);
            i++;
            try {
                Thread.sleep(1000); // Sleep for 1 second (1000 milliseconds)
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
