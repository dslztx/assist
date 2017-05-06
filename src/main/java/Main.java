/**
 * @author dslztx
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        int i = 0;
        while (true) {
            System.out.println("hello world " + (i++));
            if (i == 1000000) {
                break;
            }
            Thread.sleep(5000L);
        }
    }
}
