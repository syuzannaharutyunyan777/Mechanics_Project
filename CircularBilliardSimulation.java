import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CircularBilliardSimulation {
    private static final double RADIUS = 1.0;
    private static final double DELTA = 0.001;

    public static List<List<Point>> simulateMultipleReflections(int[] nReflectionsList) {
        List<List<Point>> allReflectionPoints = new ArrayList<>();

        for (int nReflections : nReflectionsList) {
            List<Point> reflectionPoints = new ArrayList<>();

            Random random = new Random();
            double x = random.nextDouble() * 2 * RADIUS - RADIUS;
            double y = random.nextDouble() * 2 * RADIUS - RADIUS;
            double px = random.nextDouble() * 2 - 1;
            double py = random.nextDouble() * 2 - 1;
            normalizeMomentum(px, py);

            reflectionPoints.add(new Point(x, y));

            for (int i = 0; i < nReflections; i++) {
                double slope = py / px;
                double xIntersection = (px > 0) ? Math.sqrt(RADIUS * RADIUS / (1 + slope * slope))
                        : -Math.sqrt(RADIUS * RADIUS / (1 + slope * slope));
                double yIntersection = slope * xIntersection;
                x += xIntersection;
                y += yIntersection;

                reflectionPoints.add(new Point(x, y));

                double pxNew = (y * y - x * x) * px - 2 * x * y * py;
                double pyNew = -2 * x * y * px + (x * x - y * y) * py;
                normalizeMomentum(pxNew, pyNew);
                px = pxNew;
                py = pyNew;
            }

            allReflectionPoints.add(reflectionPoints);
        }

        return allReflectionPoints;
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
        int[] nReflectionsList = {10};
        List<List<Point>> allReflectionPoints = simulateMultipleReflections(nReflectionsList);

        for (int i = 0; i < nReflectionsList.length; i++) {
            int nReflections = nReflectionsList[i];
            System.out.println("Reflection Points for n = " + nReflections);
            List<Point> reflectionPoints = allReflectionPoints.get(i);
            for (Point point : reflectionPoints) {
                System.out.println(point);
            }
            System.out.println();

            boolean isReversible = isPathReversible(reflectionPoints);
            System.out.println("Is the path reversible for n = " + nReflections + "? " + isReversible);
            System.out.println();
        }
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

