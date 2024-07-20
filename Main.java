 public static void main(String[] args) {
        double[] time = {0, 1, 2, 3, 4, 5, 6, 7};
        double[] coordinate = {0, 1, 0, -1, 0, 1, 0, -1};
        FT ft = new FT(time, coordinate);
        ft.transform();
        double[] amplitude = ft.getAmplitude();
        System.out.println(Arrays.toString(amplitude));

        int[] bits = {1, 0, 1, 1, 0, 1, 0, 0};
        ConverterInt converterInt = new ConverterInt();
        double[] springs = converterInt.convertToSprings(bits);
        System.out.println(Arrays.toString(springs));
        double[] oscillations = converterInt.calculateOscillations(springs);
        System.out.println(Arrays.toString(oscillations));
        double decimal = converterInt.convertToDecimal(bits, oscillations);
        System.out.println(decimal);

        Converter8Bit converter8Bit = new Converter8Bit();
        springs = converter8Bit.convertToSprings(bits);
        System.out.println(Arrays.toString(springs));
        oscillations = converter8Bit.calculateOscillations(springs);
        System.out.println(Arrays.toString(oscillations));
        decimal = converter8Bit.convertToDecimal(bits, oscillations);
        System.out.println(decimal);

        ConverterFloat converterFloat = new ConverterFloat(32);
        springs = converterFloat.convertToSprings(bits);
        System.out.println(Arrays.toString(springs));
        oscillations = converterFloat.calculateOscillations(springs);
        System.out.println(Arrays.toString(oscillations));
        decimal = converterFloat.convertToDecimal(bits, oscillations);
        System.out.println(decimal);
    }
