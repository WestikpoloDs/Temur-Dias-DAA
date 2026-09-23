public class DeterministicSelector {

    public static final int GROUP_SIZE = 5;

    public long comparisons = 0;
    public int maxDepth = 0;

    public int select(int[] a, int k) {
        comparisons = 0;
        maxDepth = 0;
        int idx = selectIndex(a, 0, a.length - 1, k, 0);
        return a[idx];
    }

    private int selectIndex(int[] a, int lo, int hi, int k, int depth) {
        if (depth > maxDepth) maxDepth = depth;
        while (true) {
            if (lo == hi) return lo;

            int pivotIndex = medianOfMediansIndex(a, lo, hi, depth);
            pivotIndex = partition(a, lo, hi, pivotIndex);

            if (k == pivotIndex) {
                return pivotIndex;
            } else if (k < pivotIndex) {
                hi = pivotIndex - 1;
            } else {
                lo = pivotIndex + 1;
            }
        }
    }

    private int medianOfMediansIndex(int[] a, int lo, int hi, int depth) {
        int n = hi - lo + 1;
        if (n <= GROUP_SIZE) {
            insertionSort(a, lo, hi);
            return lo + (n - 1) / 2;
        }

        int numGroups = (n + GROUP_SIZE - 1) / GROUP_SIZE;
        for (int g = 0; g < numGroups; g++) {
            int groupLo = lo + g * GROUP_SIZE;
            int groupHi = Math.min(groupLo + GROUP_SIZE - 1, hi);
            insertionSort(a, groupLo, groupHi);
            int medianIdx = groupLo + (groupHi - groupLo) / 2;
            swap(a, lo + g, medianIdx);
        }

        int mid = lo + (numGroups - 1) / 2;
        return selectIndex(a, lo, lo + numGroups - 1, mid, depth + 1);
    }

    private int partition(int[] a, int lo, int hi, int pivotIndex) {
        int pivotValue = a[pivotIndex];
        swap(a, pivotIndex, hi);
        int store = lo;
        for (int i = lo; i < hi; i++) {
            comparisons++;
            if (a[i] < pivotValue) {
                swap(a, store, i);
                store++;
            }
        }
        swap(a, store, hi);
        return store;
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

    private void swap(int[] a, int i, int j) {
        if (i == j) return;
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }
}

