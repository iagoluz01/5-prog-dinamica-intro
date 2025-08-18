import java.util.Arrays;

public class Fibonacci {
    // Contador para chamadas/iterações
    static class Counter {
        public long count = 0;
    }

    // FIBO-REC
    public static long fiboRec(int n, Counter counter) {
        counter.count++;
        if (n <= 1) return n;
        return fiboRec(n - 1, counter) + fiboRec(n - 2, counter);
    }

    // FIBO (iterativo)
    public static long fibo(int n, Counter counter) {
        if (n <= 1) return n;
        long[] f = new long[n + 1];
        f[0] = 0;
        f[1] = 1;
        for (int i = 2; i <= n; i++) {
            counter.count++;
            f[i] = f[i - 1] + f[i - 2];
        }
        return f[n];
    }

    // MEMOIZED-FIBO
    public static long memoizedFibo(int n, Counter counter) {
        long[] f = new long[n + 1];
        Arrays.fill(f, -1);
        return lookupFibo(f, n, counter);
    }

    public static long lookupFibo(long[] f, int n, Counter counter) {
        counter.count++;
        if (f[n] >= 0) return f[n];
        if (n <= 1) f[n] = n;
        else f[n] = lookupFibo(f, n - 1, counter) + lookupFibo(f, n - 2, counter);
        return f[n];
    }

    public static void main(String[] args) {
        int[] ns1 = {4, 8, 16, 32};
        int[] ns2 = {128, 1000, 10000};
        System.out.printf("%-15s |", "Algoritmo");
        for (int n : new int[]{4, 8, 16, 32, 128, 1000, 10000}) {
            System.out.printf(" n=%-6d |", n);
        }
        System.out.println();
        System.out.println("---------------------------------------------------------------------------------------------");

        // FIBO-REC
        System.out.printf("%-15s |", "FIBO-REC");
        for (int n : new int[]{4, 8, 16, 32, 128, 1000, 10000}) {
            if (n > 32) {
                System.out.printf("   N/A    |");
                continue;
            }
            Counter c = new Counter();
            long start = System.nanoTime();
            long res = fiboRec(n, c);
            double elapsed = (System.nanoTime() - start) / 1e6;
            System.out.printf(" %d (%d, %.2fms) |", res, c.count, elapsed);
        }
        System.out.println();

        // FIBO
        System.out.printf("%-15s |", "FIBO");
        for (int n : new int[]{4, 8, 16, 32, 128, 1000, 10000}) {
            Counter c = new Counter();
            long start = System.nanoTime();
            long res = fibo(n, c);
            double elapsed = (System.nanoTime() - start) / 1e6;
            System.out.printf(" %d (%d, %.2fms) |", res, c.count, elapsed);
        }
        System.out.println();

        // MEMOIZED-FIBO
        System.out.printf("%-15s |", "MEMOIZED-FIBO");
        for (int n : new int[]{4, 8, 16, 32, 128, 1000, 10000}) {
            Counter c = new Counter();
            long start = System.nanoTime();
            long res = memoizedFibo(n, c);
            double elapsed = (System.nanoTime() - start) / 1e6;
            System.out.printf(" %d (%d, %.2fms) |", res, c.count, elapsed);
        }
        System.out.println();
    }
}
