public abstract class Converter {

    public abstract double[] convertToSprings(int[] bits);

    public double[] calculateOscillations(double[] springs) {
        int n = springs.length;
        double[] time = new double[n];
        for (int i = 0; i < n; i++) {
            time[i] = i;
        }
        double dt = n > 1 ? time[1] - time[0] : 0;

        double[] amplitude = new double[n];
        for (int k = 0; k < n; k++) {
            double real = 0;
            double imaginary = 0;
            for (int j = 0; j < n; j++) {
                real += springs[j] * Math.cos(2 * Math.PI * k * j / n);
                imaginary -= springs[j] * Math.sin(2 * Math.PI * k * j / n);
            }
            amplitude[k] = Math.sqrt(Math.pow(real, 2) + Math.pow(imaginary, 2)) * 2 * dt / n;
        }
        return amplitude;
    }
}
