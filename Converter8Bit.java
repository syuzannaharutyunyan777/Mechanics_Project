public class Converter8Bit extends Converter {

    public double[] convertToSprings(int[] bits) {
        double[] springs = new double[8];
        for (int i = 0; i < 8; i++) {
            springs[i] = bits[i];
        }
        return springs;
    }

    public double convertToDecimal(int[] bits, double[] amplitude) {
        double sum = 0;
        for (int i = 0; i < 8; i++) {
            sum += amplitude[i] * bits[i];
        }
        return sum;
    }
}
