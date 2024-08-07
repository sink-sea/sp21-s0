/** Class that prints the Collatz sequence starting from a given number.
 *  @author sink-sea
 */
public class Collatz {

    /** Buggy implementation of nextNumber! */
    /* Next number: 
     * @param n
     * @return n % 2 ? 3 * n + 1 : n / 2; 
     */
    public static int nextNumber(int n) {
        if (n % 2 == 0) {
            return n / 2;
        } else {
            return 3 * n + 1;
        }
    }

    public static void main(String[] args) {
        int n = 5;
        System.out.print(n + " ");
        while (n != 1) {
            n = nextNumber(n);
            System.out.print(n + " ");
        }
        System.out.println();
    }
}

