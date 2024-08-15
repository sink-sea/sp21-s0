package timingtest;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 */
public class TimeSLList {
    private static void printTimingTable(AList<Integer> Ns, AList<Double> times, AList<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    public static void main(String[] args) {
        timeGetLast();
    }

    public static void timeGetLast() {
        AList<Integer> Ns = new AList<>();
        AList<Double> times = new AList<>();
        AList<Integer> ops = new AList<>();

        int N = 500, M = 10000;
        for (int i = 0; i < 8; i += 1) {
            N *= 2;
            Ns.addLast(N);
            ops.addLast(M);

            SLList<Integer> lst = new SLList<>();
            for (int j = 0; j < N; j += 1) {
                lst.addLast(0);
            }
            Stopwatch sw = new Stopwatch();
            for (int j = 0; j < M; j += 1) {
                lst.getLast();
            }
            double time = sw.elapsedTime();
            times.addLast(time);
        }
        printTimingTable(Ns, times, ops);
    }

}
