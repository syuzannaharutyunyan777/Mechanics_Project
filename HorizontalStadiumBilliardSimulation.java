import java.util.ArrayList;
import java.util.List;

public class HorizontalStadiumBilliardSimulation {
    private static final double RADIUS = 1.0;

    public static List<Point> simulateHorizontalStadiumBilliard(int nReflections, double L, double x0, double y0, double px0, double py0) {
        List<Point> reflectionPoints = new ArrayList<>();

        normalizeMomentum(px0, py0);

        double x = x0;
        double y = y0;
        double px = px0;
        double py = py0;

        reflectionPoints.add(new Point(x, y));

        for (int i = 0; i < nReflections; i++) {
            Side side = determineSide(x, L);

            switch (side) {
                case TOP:
                case BOTTOM:
                    double yIntersection = (side == Side.TOP) ? RADIUS : -RADIUS;
                    double t = (yIntersection - y) / py;
                    x += px * t;
                    y = yIntersection;
                    py = -py;
                    break;

                case LEFT_SEMICIRCLE:
                    double xIntersectionLeft = -Math.sqrt(RADIUS * RADIUS - y * y);
                    double tLeft = (xIntersectionLeft - x) / px;
                    double yIntersectionLeft = y + py * tLeft;
                    x = xIntersectionLeft;
                    y = yIntersectionLeft;
                    normalizePositionInSemicircle(y);
                    reflectOffCircle(x, y, px, py);
                    break;

                case RIGHT_SEMICIRCLE:
                    double xIntersectionRight = L + Math.sqrt(RADIUS * RADIUS - y * y);
                    double tRight = (xIntersectionRight - x) / px;
                    double yIntersectionRight = y + py * tRight;
                    x = xIntersectionRight;
                    y = yIntersectionRight;
                    normalizePositionInSemicircle(y);
                    reflectOffCircle(x, y, px, py);
                    break;
            }

            reflectionPoints.add(new Point(x, y));
        }

        return reflectionPoints;
    }

    private static Side determineSide(double x, double L) {
        if (x < 0) {
            return Side.LEFT_SEMICIRCLE;
        } else if (x > L) {
            return Side.RIGHT_SEMICIRCLE;
        } else if (Math.abs(x - L / 2) <= RADIUS) {
            return Side.TOP;
        } else {
            return Side.BOTTOM;
        }
    }

    private static void normalizePositionInSemicircle(double y) {
        if (y > RADIUS) {
            y = RADIUS;
        } else if (y < -RADIUS) {
            y = -RADIUS;
        }
    }

    private static void reflectOffCircle(double x, double y, double px, double py) {
        double xCenter = (x < 0) ? 0 : 2 * RADIUS;
        double pxNew = (y * y - (x - xCenter) * (x - xCenter)) * px - 2 * (x - xCenter) * y * py;
        double pyNew = -2 * (x - xCenter) * y * px + ((x - xCenter) * (x - xCenter) - y * y) * py;
        normalizeMomentum(pxNew, pyNew);
    }

    private static void normalizeMomentum(double px, double py) {
        double magnitude = Math.sqrt(px * px + py * py);
        px /= magnitude;
        py /= magnitude;
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

    enum Side {
        TOP,
        BOTTOM,
        LEFT_SEMICIRCLE,
        RIGHT_SEMICIRCLE
    }

    public static void main(String[] args) {
        int nReflections = 10;
        double L = 1.0;
        double x0 = 0.2;
        double y0 = 0.5;
        double px0 = 1.0;
        double py0 = 0.0;

        List<Point> reflectionPoints = simulateHorizontalStadiumBilliard(nReflections, L, x0, y0, px0, py0);

        for (Point point : reflectionPoints) {
            System.out.println(point);
        }
    }
}

