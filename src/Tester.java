import java.util.Arrays;
import java.util.Random;

public class Tester {

    static int passed = 0;
    static int failed = 0;
    static final Random RND = new Random(123);

    public static void main(String[] args) {
        testMergeSort();
        testQuickSort();
        testDeterministicSelect();
        testClosestPair();

        System.out.println("PASSED: " + passed + "   FAILED: " + failed);
        if (failed > 0) System.exit(1);
    }

    static void testMergeSort() {
        System.out.println("MergeSort vs Arrays.sort()");
        for (int[] a : sortingTestCases()) {
            int[] expected = a.clone();
            Arrays.sort(expected);
            int[] actual = a.clone();
            new MergeSorter().sort(actual);
            check("MergeSort n=" + a.length, Arrays.equals(expected, actual));
        }
    }

    static void testQuickSort() {
        System.out.println("QuickSort vs Arrays.sort()");
        for (int[] a : sortingTestCases()) {
            int[] expected = a.clone();
            Arrays.sort(expected);
            int[] actual = a.clone();
            new QuickSorter().sort(actual);
            check("QuickSort n=" + a.length, Arrays.equals(expected, actual));
        }
    }

    static int[][] sortingTestCases() {
        return new int[][]{
                {},                                  
                {42},                                
                {5, 5, 5, 5, 5},                      
                {1, 2, 3, 4, 5},                      
                {5, 4, 3, 2, 1},                      
                randomArray(1000),
                randomArray(5000),
                duplicateHeavyArray(2000)
        };
    }

    static int[] randomArray(int n) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = RND.nextInt(100000) - 50000;
        return a;
    }

    static int[] duplicateHeavyArray(int n) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = RND.nextInt(5);
        return a;
    }


    static void testDeterministicSelect() {
        System.out.println(" DeterministicSelect vs Arrays.sort (100 random trials) ");
        boolean allOk = true;
        for (int trial = 0; trial < 100; trial++) {
            int n = 1 + RND.nextInt(500);
            int[] a = randomArray(n);
            int k = RND.nextInt(n);

            int[] sorted = a.clone();
            Arrays.sort(sorted);
            int expected = sorted[k];

            int actual = new DeterministicSelector().select(a.clone(), k);
            if (actual != expected) {
                allOk = false;
                System.out.println("  MISMATCH trial=" + trial + " n=" + n + " k=" + k
                        + " expected=" + expected + " actual=" + actual);
            }
        }
        check("DeterministicSelect (100 trials)", allOk);
    }


    static void testClosestPair() {
        System.out.println("ClosestPair vs brute force (n <= 2000)");
        boolean allOk = true;
        int[] sizes = {2, 3, 4, 10, 50, 200, 1000, 2000};
        for (int n : sizes) {
            Point[] pts = new Point[n];
            for (int i = 0; i < n; i++) {
                pts[i] = new Point(RND.nextDouble() * 1000, RND.nextDouble() * 1000);
            }
            double expected = ClosestPairSolver.bruteForce(pts);
            double actual = new ClosestPairSolver().closest(pts);
            if (Math.abs(expected - actual) > 1e-9) {
                allOk = false;
                System.out.println("  MISMATCH n=" + n + " expected=" + expected + " actual=" + actual);
            }
        }
        check("ClosestPair (brute force cross-check)", allOk);
    }


    static void check(String name, boolean ok) {
        if (ok) {
            passed++;
            System.out.println("  [PASS] " + name);
        } else {
            failed++;
            System.out.println("  [FAIL] " + name);
        }
    }
}
