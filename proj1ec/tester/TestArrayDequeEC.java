package tester;

import static org.junit.Assert.*;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import student.StudentArrayDeque;

import java.net.Inet4Address;

public class TestArrayDequeEC {
    @Test
    public void Test1() {
        StudentArrayDeque<Integer> lst1 = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> lst2 = new ArrayDequeSolution<>();
        String message = new String();
        while (true) {
            int flag = StdRandom.uniform(0, 7);
            if (flag == 0) {
                int x = StdRandom.uniform(0, 10000);
                lst1.addFirst(x);
                lst2.addFirst(x);
                message += "addFirst(" + x + ")\n";
            } else if (flag == 1) {
                int x = StdRandom.uniform(0, 10000);
                lst1.addLast(x);
                lst2.addLast(x);
                message += "addLast(" + x + ")\n";
            } else if (flag == 2) {
                if (!lst2.isEmpty()) {
                    message += "removeFirst()\n";
                    Integer res1 = lst1.removeFirst();
                    Integer res2 = lst2.removeFirst();
                    assertEquals(message, res2, res1, 0);
                }
            } else if (flag == 3) {
                if (!lst2.isEmpty()) {
                    message += "removeLast()\n";
                    Integer res1 = lst1.removeLast();
                    Integer res2 = lst2.removeLast();
                    assertEquals(message, res2, res1, 0);
                }
            } else if (flag == 4) {
                message += "isEmpty()\n";
                assertEquals(message, lst2.isEmpty(), lst1.isEmpty());
            } else if (flag == 5) {
                message += "size()\n";
                assertEquals(message, lst2.size(), lst1.size(), 0);
            } else {
                if (!lst2.isEmpty()) {
                    int idx = StdRandom.uniform(0, lst2.size());
                    message += "get(" + idx + ")\n";
                    Integer res1 = lst1.get(idx);
                    Integer res2 = lst2.get(idx);
                    assertEquals(message, res2, res1);
                }
            }
        }
    }
}
