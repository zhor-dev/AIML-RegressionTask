public class RegressionFunctions {
    public static double xSinX(double x) {
        return x * Math.sin(x) / 10;
    }

    public static double xSin15X(double x) {
        return x * Math.sin(15 * x) / 10;
    }

    public static double powX2(double x) {
        return 1.8 * x * x - 0.9;
    }
}
