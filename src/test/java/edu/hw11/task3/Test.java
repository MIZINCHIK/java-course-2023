package edu.hw11.task3;

public class Test {
    public long fib(int n) {
        long fst = 0;
        long snd = 1;
        int count = n;
        while (count > 0) {
            long tmp = snd;
            snd += fst;
            fst = tmp;
            count--;
        }
        return fst;
    }
}
