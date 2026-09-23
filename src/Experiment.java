import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;


public class Experiment {

    private static final Random RND = new Random(42);

    private static final int[] SORT_SIZES = {100, 500, 1000, 5000, 10000, 50000, 100000, 500000};
    private static final int[] SELECT_SIZES = {100, 1000, 10000, 100000, 500000};
    private static final int[] CLOSEST_SIZES = {100, 500, 1000, 2000, 5000, 10000, 50000};

    private static final String[] TYPES = {"random", "sorted", "reverse", "duplicates"};

    public static void main(String[] args) throws IOException {
        runAll("results/results.csv");
    }

    public static void runAll(String csvPath) throws IOException {
        try (PrintWriter out = new PrintWriter(new FileWriter(csvPath))) {
            out.println("algorithm,input_type,n,time_ns,max_depth,metric_name,metric_value");

            System.out.println("Running MergeSort experiments...");
            for (String type : TYPES) {
                for (int n : SORT_SIZES) {
                    int[] a = generate(n, type);
                    MergeSorter ms = new MergeSorter();
                    long t0 = System.nanoTime();
                    ms.sort(a);
                    long t1 = System.nanoTime();
                    out.println(String.join(",", "MergeSort", type, String.valueOf(n),
                            String.valueOf(t1 - t0), String.valueOf(ms.maxDepth),
                            "comparisons", String.valueOf(ms.comparisons)));
                }
            }

            System.out.println("Running QuickSort experiments...");
            for (String type : TYPES) {
                for (int n : SORT_SIZES) {
                    int[] a = generate(n, type);
                    QuickSorter qs = new QuickSorter();
                    long t0 = System.nanoTime();
                    qs.sort(a);
                    long t1 = System.nanoTime();
                    out.println(String.join(",", "QuickSort", type, String.valueOf(n),
                            String.valueOf(t1 - t0), String.valueOf(qs.maxDepth),
                            "comparisons", String.valueOf(qs.comparisons)));
                }
            }

            System.out.println("Running DeterministicSelect experiments...");
            for (String type : TYPES) {
                for (int n : SELECT_SIZES) {
                    int[] a = generate(n, type);
                    DeterministicSelector ds = new DeterministicSelector();
                    int k = n / 2;
                    long t0 = System.nanoTime();
                    ds.select(a, k);
                    long t1 = System.nanoTime();
                    out.println(String.join(",", "DeterministicSelect", type, String.valueOf(n),
                            String.valueOf(t1 - t0), String.valueOf(ds.maxDepth),
                            "comparisons", String.valueOf(ds.comparisons)));
                }
            }

            System.out.println("Running ClosestPair experiments...");
            for (int n : CLOSEST_SIZES) {
                Point[] pts = generatePoints(n);
                ClosestPairSolver cp = new ClosestPairSolver();
                long t0 = System.nanoTime();
                cp.closest(pts);
                long t1 = System.nanoTime();
                out.println(String.join(",", "ClosestPair", "random", String.valueOf(n),
                        String.valueOf(t1 - t0), String.valueOf(cp.maxDepth),
                        "comparisons", String.valueOf(cp.comparisons)));
            }
        }
        System.out.println("Done. Results written to " + csvPath);
    }

    static int[] generate(int n, String type) {
        int[] a = new int[n];
        switch (type) {
            case "random":
                for (int i = 0; i < n; i++) a[i] = RND.nextInt(1_000_000);
                break;
            case "sorted":
                for (int i = 0; i < n; i++) a[i] = i;
                break;
            case "reverse":
                for (int i = 0; i < n; i++) a[i] = n - i;
                break;
            case "duplicates":
                for (int i = 0; i < n; i++) a[i] = RND.nextInt(10); 
                break;
            default:
                throw new IllegalArgumentException("Unknown type: " + type);
        }
        return a;
    }

    static Point[] generatePoints(int n) {
        Point[] pts = new Point[n];
        for (int i = 0; i < n; i++) {
            pts[i] = new Point(RND.nextDouble() * 100000, RND.nextDouble() * 100000);
        }
        return pts;
    }
}
