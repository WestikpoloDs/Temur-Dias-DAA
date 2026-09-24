A. Project Overview

Purpose. This project implements and analyzes four classic divide-and-conquer algorithms, comparing their theoretical time/space complexity against measured performance on inputs of varying size and structure.

Implemented algorithms:

MergeSort
QuickSort (randomized)
Deterministic Select (Median-of-Medians)
Closest Pair of Points
B. Algorithm Analysis

1. MergeSort

How it works: Recursively splits the array in half, sorts each half, merges with a single linear pass. Subarrays ≤16 elements finish with Insertion Sort; one reusable auxiliary buffer is allocated once per call.
Complexity: Time O(n log n) in all cases; space O(n) auxiliary.
Recurrence: T(n) = 2T(n/2) + O(n). Master Theorem, a=2, b=2, f(n)=O(n)=Θ(n^log_b(a)) -> Case 2 -> T(n) = O(n log n).

2. QuickSort

How it works: Random pivot, in-place Lomuto partition, recurses into the smaller side, loops (iterates) into the larger side in the same stack frame.
Complexity: Expected O(n log n); worst case O(n^2) (astronomically unlikely with randomization).
Recurrence: Expected: T(n)=2T(n/2)+O(n) -> Case 2 -> O(n log n). Worst: T(n)=T(n−1)+O(n) -> o(n^2).

3. Deterministic Select (Median-of-Medians)

How it works: Groups of 5, sorts each group, recursively finds median of group medians as pivot, partitions in place, recurses only into the side containing target index k.
Complexity: O(n) worst case.
Recurrence: T(n) =< T(n/5) + T(7n/10) + O(n). Doesn't fit Master Theorem (unequal subproblems), but satisfies Akra–Bazzi: 1/5 + 7/10 = 9/10 < 1 -> work contracts by a constant factor each level -> T(n) = O(n).

4. Closest Pair of Points

How it works: Pre-sort by x and y, recursively split by x-midpoint, recurse both halves, combine via a "strip" scan (points within current best distance d of the dividing line), checking each point against a bounded number of y-neighbors.
Complexity: O(n log n).
Recurrence: T(n) = 2T(n/2) + O(n) -> Case 2 -> O(n log n).
C. Experimental Results

Include (from your own real Java run, via Experiment.java → results/results.csv):

A table of execution time (ms) per algorithm × input size × input type.
A table of max recursion depth per algorithm × input size.
Two plots: time vs n (log-log) and recursion depth vs n — generate with docs/plots/plot_results.py after running java Main --bench.
D. Discussion

Do the results match theoretical complexity?
Yes in shape: MergeSort/QuickSort/ClosestPair recursion depth should grow as O(log n); Select's should stay flatter. Execution time should roughly track n*log(n) for the O(n log n) algorithms.

How does input structure affect performance?

MergeSort: input-independent (always full merge work).
QuickSort: randomized pivot makes it robust to sorted/reverse-sorted input; heavy duplicates increase comparisons but recursion depth still stays O(log n) thanks to smaller-first recursion.
Select: largely input-independent by design.
ClosestPair: runtime depends mainly on n, not point arrangement.

Why does smaller-first recursion help QuickSort?
The recursive call only ever handles a sub-problem <= n/2 (by definition of "smaller half"), so call-stack depth after k recursive calls is at most k, and k <= log₂(n). This bounds stack depth to O(log n) even on unlucky splits - it protects against stack overflow, not against O(n^2) worst-case time (that's what randomization is for).

Why does Median-of-Medians guarantee O(n)?
Grouping into 5s and taking the median of medians guarantees the pivot eliminates a constant fraction (>=3/10) of the array every call, regardless of input — no arrangement can force a bad pivot. The Akra–Bazzi condition (coefficients summing to <1) means total work across all levels is a geometric series dominated by the top level: O(n).

Why is divide-and-conquer Closest Pair faster than O(n^2) for large inputs?
Brute force checks all C(n,2) = O(n^2) pairs. D&C only compares nearby points: the "combine" step is O(n) per level (bounded-neighbours packing argument), giving O(n log n) total - e.g. at n=1,000,000, n^2 approximately 10^12 vs n·log₂n approximately 2×10^7, a five-order-of-magnitude difference.

What practical factors affect performance (JVM, cache, GC, etc.)?

JIT warmup (JVM interprets before compiling hot methods to native code).
Garbage collection (MergeSort's aux buffer, ClosestPair's array copies create garbage).
Cache locality (primitive int[] scanning is cache-friendly; Point[]/HashSet-based splitting has worse locality).
Autoboxing (avoided here via primitive arrays).
Branch prediction (QuickSorts data-dependent branches are harder to predict than MergeSort's regular merge loop).

E. Reflection

Actually i had problems with understanding all of this methods and with weak Java skills because this language is little bit hard for me. 


    F.Screenshots
1.Tests

<img width="633" height="504" alt="Снимок экрана 2026-09-23 114917" src="https://github.com/user-attachments/assets/acbd08e2-3253-4d2f-b1d3-e20134cf9953" />












2.Main

<img width="894" height="94" alt="Снимок экрана 2026-09-23 114852" src="https://github.com/user-attachments/assets/380e4279-9214-4b62-a51a-e2e3cb9f87f7" />










3.Results

<img width="871" height="881" alt="Снимок экрана 2026-09-23 114834" src="https://github.com/user-attachments/assets/5f774043-23ee-4d93-8682-e6b094d30c0f" />

Also graphics

1.

<img width="1200" height="900" alt="depth_vs_n" src="https://github.com/user-attachments/assets/fa5e7c3c-8fbe-4d68-b6a6-a3c211f65484" />

2.

<img width="1200" height="900" alt="time_vs_n" src="https://github.com/user-attachments/assets/62651fc9-04d7-4e3c-af8c-50098c3d941e" />
