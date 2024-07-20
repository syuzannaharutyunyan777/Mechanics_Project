public class ConverterInt extends Converter {

    public double[] convertToSprings(int[] bits) {
        double[] springs = new double[bits.length];
        for (int i = 0; i < bits.length; i++) {
            springs[i] = bits[i];
        }
        return springs;
    }

    public double convertToDecimal(int[] bits, double[] amplitude) {
        double sum = 0;
        for (int i = 0; i < bits.length; i++) {
            sum += amplitude[i] * bits[i];
        }
        return sum;
    }
}
