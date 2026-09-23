import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws Exception {
        demoMergeSort();
        demoQuickSort();
        demoSelect();
        demoClosestPair();

        if (args.length > 0 && args[0].equals("--bench")) {
            Experiment.runAll("results/results.csv");
        } else {
            System.out.println("\n(Run with --bench to execute the full experiment suite and produce results.csv)");
        }
    }

    static void demoMergeSort() {
        int[] a = {9, 3, 7, 1, 8, 2, 5, 4, 6, 0};
        MergeSorter ms = new MergeSorter();
        ms.sort(a);
        System.out.println("MergeSort:            " + Arrays.toString(a)
                + "  [comparisons=" + ms.comparisons + ", maxDepth=" + ms.maxDepth + "]");
    }

    static void demoQuickSort() {
        int[] a = {9, 3, 7, 1, 8, 2, 5, 4, 6, 0};
        QuickSorter qs = new QuickSorter();
        qs.sort(a);
        System.out.println("QuickSort:             " + Arrays.toString(a)
                + "  [comparisons=" + qs.comparisons + ", maxDepth=" + qs.maxDepth + "]");
    }

    static void demoSelect() {
        int[] a = {9, 3, 7, 1, 8, 2, 5, 4, 6, 0};
        DeterministicSelector ds = new DeterministicSelector();
        int k = 4; 
        int result = ds.select(a.clone(), k);
        System.out.println("DeterministicSelect:   k=" + k + " -> " + result
                + "  [comparisons=" + ds.comparisons + ", maxDepth=" + ds.maxDepth + "]");
    }

    static void demoClosestPair() {
        Point[] pts = {
                new Point(0, 0), new Point(3, 4), new Point(1, 1),
                new Point(9, 9), new Point(1.05, 1.05), new Point(-3, -4)
        };
        ClosestPairSolver cp = new ClosestPairSolver();
        double d = cp.closest(pts);
        System.out.println("ClosestPair:           min distance = " + d
                + "  [comparisons=" + cp.comparisons + ", maxDepth=" + cp.maxDepth + "]");
    }
}
