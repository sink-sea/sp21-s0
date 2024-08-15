package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;

import java.util.Objects;

import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
  // YOUR TESTS HERE
    @Test
    public void Test1() {
        AListNoResizing<Integer> alst = new AListNoResizing<>();
        BuggyAList<Integer> blst = new BuggyAList<>();
        int N = (int) 1e5;
        for (int i = 0; i < N; i += 1) {
            int flag = StdRandom.uniform(0, 3);
            switch (flag) {
                case 0: {
                    if (alst.size() < 1000) {
                        int x = StdRandom.uniform(0, 1000);
                        alst.addLast(x);
                        blst.addLast(x);
                    }
                } break;
                case 1: {
                    if (alst.size() >= 1) {
                        int index = StdRandom.uniform(0, alst.size());
                        assertTrue(Objects.equals(alst.get(index), blst.get(index)));
                    }
                } break;
                case 2: {
                    if (alst.size() >= 1) {
                        assertTrue(Objects.equals(alst.removeLast(), blst.removeLast()));
                    }
                } break;
            }
        }
    }
}
