public class ConverterFloat extends Converter {

    private int intBitsLength;

    public ConverterFloat(int intBitsLength) {
        this.intBitsLength = intBitsLength;
    }

    public double[] convertToSprings(int[] bits) {
        int totalBitsLength = bits.length;
        double[] springs = new double[totalBitsLength];

        for (int i = 0; i < intBitsLength; i++) {
            springs[i] = bits[i];
        }

        for (int i = intBitsLength; i < totalBitsLength; i++) {
            springs[i] = bits[i] * Math.pow(0.5, i - intBitsLength + 1);
        }

        return springs;
    }

    public double convertToDecimal(int[] bits, double[] amplitude) {
        int totalBitsLength = bits.length;
        double sum = 0;

        for (int i = 0; i < intBitsLength; i++) {
            sum += amplitude[i] * bits[i];
        }

        for (int i = intBitsLength; i < totalBitsLength; i++) {
            sum += amplitude[i] * bits[i];
        }

        return sum;
    }
}
