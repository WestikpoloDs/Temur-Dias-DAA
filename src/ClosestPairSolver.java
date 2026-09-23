import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ClosestPairSolver {

    public long comparisons = 0;
    public long calls = 0;
    public int maxDepth = 0;

    public double closest(Point[] points) {
        comparisons = 0;
        calls = 0;
        maxDepth = 0;
        if (points.length < 2) return Double.POSITIVE_INFINITY;

        Point[] px = points.clone();
        Arrays.sort(px, Comparator.comparingDouble(p -> p.x));
        Point[] py = px.clone();
        Arrays.sort(py, Comparator.comparingDouble(p -> p.y));

        return closestRec(px, py, 0);
    }

    private double closestRec(Point[] px, Point[] py, int depth) {
        calls++;
        if (depth > maxDepth) maxDepth = depth;

        int n = px.length;
        if (n <= 3) return bruteForce(px);

        int mid = n / 2;
        double midX = px[mid].x;

        Point[] leftPx = Arrays.copyOfRange(px, 0, mid);
        Point[] rightPx = Arrays.copyOfRange(px, mid, n);

        Set<Point> leftSet = new HashSet<>(Arrays.asList(leftPx));
        Point[] leftPy = new Point[leftPx.length];
        Point[] rightPy = new Point[rightPx.length];
        int li = 0, ri = 0;
        for (Point p : py) {
            if (leftSet.contains(p)) {
                leftPy[li++] = p;
            } else {
                rightPy[ri++] = p;
            }
        }

        double dl = closestRec(leftPx, leftPy, depth + 1);
        double dr = closestRec(rightPx, rightPy, depth + 1);
        double d = Math.min(dl, dr);

        List<Point> strip = new ArrayList<>();
        for (Point p : py) {
            comparisons++;
            if (Math.abs(p.x - midX) < d) {
                strip.add(p);
            }
        }

        for (int i = 0; i < strip.size(); i++) {
            for (int j = i + 1; j < strip.size(); j++) {
                comparisons++;
                if ((strip.get(j).y - strip.get(i).y) >= d) break;
                double dist = strip.get(i).distTo(strip.get(j));
                if (dist < d) d = dist;
            }
        }
        return d;
    }

    public static double bruteForce(Point[] pts) {
        double best = Double.POSITIVE_INFINITY;
        for (int i = 0; i < pts.length; i++) {
            for (int j = i + 1; j < pts.length; j++) {
                double d = pts[i].distTo(pts[j]);
                if (d < best) best = d;
            }
        }
        return best;
    }
}