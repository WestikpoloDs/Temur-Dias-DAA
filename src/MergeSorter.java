public class MergeSorter {

    public static final int CUTOFF = 16;

    public long comparisons = 0;
    public long calls = 0;
    public int maxDepth = 0;

    private int[] aux;

    public void sort(int[] a) {
        comparisons = 0;
        calls = 0;
        maxDepth = 0;
        if (a.length < 2) return;
        aux = new int[a.length];
        sort(a, 0, a.length - 1, 0);
    }

    private void sort(int[] a, int lo, int hi, int depth) {
        calls++;
        if (depth > maxDepth) maxDepth = depth;

        if (hi - lo + 1 <= CUTOFF) {
            insertionSort(a, lo, hi);
            return;
        }

        int mid = lo + (hi - lo) / 2;
        sort(a, lo, mid, depth + 1);
        sort(a, mid + 1, hi, depth + 1);
        merge(a, lo, mid, hi);
    }

    private void insertionSort(int[] a, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++) {
            int key = a[i];
            int j = i - 1;
            while (j >= lo) {
                comparisons++;
                if (a[j] > key) {
                    a[j + 1] = a[j];
                    j--;
                } else {
                    break;
                }
            }
            a[j + 1] = key;
        }
    }

    private void merge(int[] a, int lo, int mid, int hi) {
        System.arraycopy(a, lo, aux, lo, hi - lo + 1);
        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid) {
                a[k] = aux[j++];
            } else if (j > hi) {
                a[k] = aux[i++];
            } else {
                comparisons++;
                if (aux[i] <= aux[j]) {
                    a[k] = aux[i++];
                } else {
                    a[k] = aux[j++];
                }
            }
        }
    }
}
