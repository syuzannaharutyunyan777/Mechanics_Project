import java.util.ArrayList;
import java.util.List;

public class VerticalCircularBilliardSimulation {
    private static final double RADIUS = 1.0;
    private static final double GRAVITY = 10.0;
    private static final double DELTA = 0.001;

    public static List<Point> simulateVerticalCircularBilliard(int nReflections, double x0, double y0, double px0, double py0) {
        List<Point> reflectionPoints = new ArrayList<>();

        normalizeMomentum(px0, py0);

        double x = x0;
        double y = y0;
        double px = px0;
        double py = py0;

        reflectionPoints.add(new Point(x, y));

        for (int i = 0; i < nReflections; i++) {
            double t = (py + Math.sqrt(py * py + 2 * GRAVITY * y)) / GRAVITY;
            double xIntersection = x + px * t;
            double yIntersection = y + py * t - 0.5 * GRAVITY * t * t;

            double distanceToCenter = Math.sqrt(xIntersection * xIntersection + yIntersection * yIntersection);
            double factor = RADIUS / distanceToCenter;
            x = factor * xIntersection;
            y = factor * yIntersection;

            reflectionPoints.add(new Point(x, y));

            double pxNew = (y * y - x * x) * px - 2 * x * y * py;
            double pyNew = -2 * x * y * px + (x * x - y * y) * py;
            normalizeMomentum(pxNew, pyNew);
            px = pxNew;
            py = pyNew;
        }

        return reflectionPoints;
    }

    private static void normalizeMomentum(double px, double py) {
        double magnitude = Math.sqrt(px * px + py * py);
        px /= magnitude;
        py /= magnitude;
    }

    public static boolean isPathReversible(List<Point> reflectionPoints) {
        List<Point> reversedPoints = new ArrayList<>(reflectionPoints);
        for (int i = reversedPoints.size() - 2; i >= 0; i--) {
            reversedPoints.add(reflectionPoints.get(i));
        }

        int size = reflectionPoints.size();
        for (int i = 0; i < size; i++) {
            double dx = Math.abs(reflectionPoints.get(i).x - reversedPoints.get(i).x);
            double dy = Math.abs(reflectionPoints.get(i).y - reversedPoints.get(i).y);
            if (dx > DELTA || dy > DELTA) {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        int nReflections = 10;
        double x0 = 0.8;
        double y0 = 0.3;
        double px0 = 0.5;
        double py0 = -0.4;

        List<Point> reflectionPoints = simulateVerticalCircularBilliard(nReflections, x0, y0, px0, py0);

        System.out.println("Reflection Points for n = " + nReflections);
        for (Point point : reflectionPoints) {
            System.out.println(point);
        }
        System.out.println();

        boolean isReversible = isPathReversible(reflectionPoints);
        System.out.println("Is the path reversible? " + isReversible);
    }

    static class Point {
        private final double x;
        private final double y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "(" + x + ", " + y + ")";
        }
    }
}
