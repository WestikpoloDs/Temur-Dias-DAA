import java.util.Random;

public class QuickSorter {

    public long comparisons = 0;
    public long swaps = 0;
    public int maxDepth = 0;

    private final Random rnd = new Random();

    public void sort(int[] a) {
        comparisons = 0;
        swaps = 0;
        maxDepth = 0;
        if (a.length < 2) return;
        sort(a, 0, a.length - 1, 0);
    }

    private void sort(int[] a, int lo, int hi, int depth) {
        while (lo < hi) {
            if (depth > maxDepth) maxDepth = depth;

            int p = partition(a, lo, hi);
            int leftSize = p - lo;
            int rightSize = hi - p;

            if (leftSize < rightSize) {
                sort(a, lo, p - 1, depth + 1);
                lo = p + 1;     
            } else {
                sort(a, p + 1, hi, depth + 1);
                hi = p - 1;     
            }
  
        }
    }

    private int partition(int[] a, int lo, int hi) {
        int pivotIndex = lo + rnd.nextInt(hi - lo + 1);
        swap(a, pivotIndex, hi);
        int pivot = a[hi];

        int i = lo - 1;
        for (int j = lo; j < hi; j++) {
            comparisons++;
            if (a[j] < pivot) {
                i++;
                swap(a, i, j);
            }
        }
        swap(a, i + 1, hi);
        return i + 1;
    }

    private void swap(int[] a, int i, int j) {
        if (i == j) return;
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
        swaps++;
    }
}
