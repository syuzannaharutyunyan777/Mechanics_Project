public class Spring {
    private double k = 1.0;

    public Spring() {
    }

    public Spring(double k) {
        this.k = k;
    }

    public double getK() {
        return k;
    }

    private void setK(double k) {
        this.k = k;
    }

    public double[] move(double t, double dt, double x0, double v0) {
        if (t < 0 || dt <= 0) throw new IllegalArgumentException("invalid time argument(s)");
        int arrayLength = ((int) (t / dt)) + 1;
        t = 0;
        return getPositionArray(arrayLength, x0, v0, t, dt, 1);
    }

    public double[] move(double t, double dt, double x0) {
        return move(t, dt, x0, 0);
    }

    public double[] move(double t0, double t1, double dt, double x0, double v0, double m) {
        if (t0 < 0 || t1 < 0 || dt <= 0 || t0 > t1) throw new IllegalArgumentException("negative time");
        int arrayLength = ((int) ((t1 - t0) / dt)) + 1;
        double t = t0;
        return getPositionArray(arrayLength, x0, v0, t, dt, m);
    }

    public double[] move(double t0, double t1, double dt, double x0, double v0) {
        return move(t0, t1, dt, x0, v0, 1);
    }

    private double[] getPositionArray(int arrayLength, double x0, double v0, double t, double dt, double m) {
        double[] positions = new double[arrayLength];
        double k = this.getK();
        double omega = Math.sqrt(k / m);
        double amplitude;
        double phaseShift;
        if (v0 == 0) {
            amplitude = x0;
            phaseShift = 0;
        } else {
            double AcosPhi = x0;
            double AsinPhi = v0 / (omega * -1);
            amplitude = Math.sqrt(Math.pow(AcosPhi, 2) + Math.pow(AsinPhi, 2));
            phaseShift = Math.atan(AsinPhi / AcosPhi);
        }
        for (int i = 0; i < arrayLength; i++) {
            positions[i] = amplitude * Math.cos(omega * t + phaseShift);
            t += dt;
        }
        return positions;
    }

    public Spring inSeries(Spring that) {
        double newK = this.k + that.k;
        return new Spring(newK);
    }

    public Spring inParallel(Spring that) {
        double newK = 1 / ((1 / this.k) + (1 / that.k));
        return new Spring(newK);
    }
}
