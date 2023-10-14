package edu.hw1;

public class Task7 {
    public static final int MSB = 1 << (Integer.SIZE - 1);

    private Task7() {
        throw new IllegalStateException();
    }

    public static int rotateLeft(int n, int shift) {
        int zeros = Integer.numberOfLeadingZeros(n);
        int nSize = Integer.SIZE - zeros;
        int mask = (zeros == 0) ? ~0 : ~(MSB >> (zeros - 1));
        int realShift = shift % nSize;
        return mask & ((n << realShift) | (n >>> (nSize - realShift)));
    }

    public static int rotateRight(int n, int shift) {
        int zeros = Integer.numberOfLeadingZeros(n);
        int nSize = Integer.SIZE - zeros;
        int mask = (zeros == 0) ? ~0 : ~(MSB >> (zeros - 1));
        int realShift = shift % nSize;
        return mask & ((n >>> realShift) | (n << (nSize - realShift)));
    }
}
