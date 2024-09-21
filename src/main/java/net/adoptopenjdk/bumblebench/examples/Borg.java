package net.adoptopenjdk.bumblebench.examples;

import java.util.Random;

/**
 * Like {@link com.github.tommyettinger.random.Ziggurat}, but preserves patterns in the input {@code long}.
 * Uses a large table of the results of {@link #probitHighPrecision(double)}, and interpolates between them using a
 * Catmull-Rom spline.
 * <br>
 * As for the name... Catmull-Rom is a type of Cubic Hermite Spline. This is like Ziggurat, and a ziggurat was a major
 * base of leadership in various societies. And the main society to use a Cube as their base of leadership would be The
 * Borg, naturally.
 */
public final class Borg {
    private Borg() {}

    /**
     * A way of taking a double in the (0.0, 1.0) range and mapping it to a Gaussian or normal distribution, so high
     * inputs correspond to high outputs, and similarly for the low range. This is centered on 0.0 and its standard
     * deviation seems to be 1.0 (the same as {@link Random#nextGaussian()}). If this is given an input of 0.0
     * or less, it returns -38.5, which is slightly less than the result when given {@link Double#MIN_VALUE}. If it is
     * given an input of 1.0 or more, it returns 38.5, which is significantly larger than the result when given the
     * largest double less than 1.0 (this value is further from 1.0 than {@link Double#MIN_VALUE} is from 0.0). If
     * given {@link Double#NaN}, it returns whatever {@link Math#copySign(double, double)} returns for the arguments
     * {@code 38.5, Double.NaN}, which is implementation-dependent. It uses an algorithm by Peter John Acklam, as
     * implemented by Sherali Karimov.
     * <a href="https://web.archive.org/web/20150910002142/http://home.online.no/~pjacklam/notes/invnorm/impl/karimov/StatUtil.java">Original source</a>.
     * <a href="https://web.archive.org/web/20151030215612/http://home.online.no/~pjacklam/notes/invnorm/">Information on the algorithm</a>.
     * <a href="https://en.wikipedia.org/wiki/Probit_function">Wikipedia's page on the probit function</a> may help, but
     * is more likely to just be confusing.
     * <br>
     * Acklam's algorithm and Karimov's implementation are both quite fast. This appears faster when generating
     * Gaussian-distributed numbers than using either the Box-Muller Transform or Marsaglia's Polar Method, though it
     * isn't as precise and can't produce as extreme min and max results in the extreme cases they should appear. If
     * given a typical uniform random {@code double} that's exclusive on 1.0, it won't produce a result higher than
     * {@code 8.209536145151493}, and will only produce results of at least {@code -8.209536145151493} if 0.0 is
     * excluded from the inputs (if 0.0 is an input, the result is {@code -38.5}). This requires a fair amount of
     * floating-point multiplication and one division for all {@code d} where it is between 0 and 1 exclusive, but
     * roughly 1/20 of the time it need a {@link Math#sqrt(double)} and {@link Math#log(double)} as well.
     * <br>
     * This can be used both as an optimization for generating Gaussian random values, and as a way of generating
     * Gaussian values that match a pattern present in the inputs (which you could have by using a sub-random sequence
     * as the input, such as those produced by a van der Corput, Halton, Sobol or R2 sequence). Most methods of generating
     * Gaussian values (e.g. Box-Muller and Marsaglia polar) do not have any way to preserve a particular pattern. Note
     * that if you don't need to preserve patterns in input, then either the Ziggurat method (which is available and the
     * default in the juniper library for pseudo-random generation) or the Marsaglia polar method (which is the default
     * in the JDK Random class) will perform better in each one's optimal circumstances. The Marsaglia polar method does
     * well when generating multiple numbers at a time, while Ziggurat is often the best when you need one Gaussian
     * value per input.
     *
     * @param d should be between 0 and 1, exclusive, but other values are tolerated
     * @return a normal-distributed double centered on 0.0; all results will be between -38.5 and 38.5, both inclusive
     */
    public static double probit (final double d) {
        if (d <= 0 || d >= 1) {
            return Math.copySign(38.5, d - 0.5);
        } else if (d < 0.02425) {
            final double q = Math.sqrt(-2.0 * Math.log(d));
            return (((((-7.784894002430293e-03 * q - 3.223964580411365e-01) * q - 2.400758277161838e+00) * q - 2.549732539343734e+00) * q + 4.374664141464968e+00) * q + 2.938163982698783e+00) / (
                    (((7.784695709041462e-03 * q + 3.224671290700398e-01) * q + 2.445134137142996e+00) * q + 3.754408661907416e+00) * q + 1.0);
        } else if (0.97575 < d) {
            final double q = Math.sqrt(-2.0 * Math.log(1 - d));
            return -(((((-7.784894002430293e-03 * q - 3.223964580411365e-01) * q - 2.400758277161838e+00) * q - 2.549732539343734e+00) * q + 4.374664141464968e+00) * q + 2.938163982698783e+00) / (
                    (((7.784695709041462e-03 * q + 3.224671290700398e-01) * q + 2.445134137142996e+00) * q + 3.754408661907416e+00) * q + 1.0);
        }
        final double q = d - 0.5;
        final double r = q * q;
        return (((((-3.969683028665376e+01 * r + 2.209460984245205e+02) * r - 2.759285104469687e+02) * r + 1.383577518672690e+02) * r - 3.066479806614716e+01) * r + 2.506628277459239e+00) * q / (
                ((((-5.447609879822406e+01 * r + 1.615858368580409e+02) * r - 1.556989798598866e+02) * r + 6.680131188771972e+01) * r - 1.328068155288572e+01) * r + 1.0);
    }

    private static double erfcBase(double x) {
        return ((0.56418958354775629) / (x + 2.06955023132914151)) *
                ((x * (x + 2.71078540045147805) + 5.80755613130301624) / (x * (x + 3.47954057099518960) + 12.06166887286239555)) *
                ((x * (x + 3.47469513777439592) + 12.07402036406381411) / (x * (x + 3.72068443960225092) + 8.44319781003968454)) *
                ((x * (x + 4.00561509202259545) + 9.30596659485887898) / (x * (x + 3.90225704029924078) + 6.36161630953880464)) *
                ((x * (x + 5.16722705817812584) + 9.12661617673673262) / (x * (x + 4.03296893109262491) + 5.13578530585681539)) *
                ((x * (x + 5.95908795446633271) + 9.19435612886969243) / (x * (x + 4.11240942957450885) + 4.48640329523408675)) *
                Math.exp(-x * x);
    }

    /**
     * The complementary error function, equivalent to {@code 1.0 - erf(x)}.
     * This uses a different approximation that should have extremely low error (below {@code 0x1p-53}, or
     * {@code 1.1102230246251565E-16}).
     * <a href="https://en.wikipedia.org/wiki/Error_function#Complementary_error_function">See Wikipedia for more</a>.
     * @param x any finite float
     * @return a float between 0 and 2, inclusive
     */
    private static double erfc(double x) {
        return x >= 0 ? erfcBase(x) : 2.0 - erfcBase(-x);
    }

    /**
     * This is the same as {@link #probit(double)},
     * except that it performs an additional step of post-processing to
     * bring the result even closer to the normal distribution.
     * @param d should be between 0 and 1, exclusive, but other values are tolerated
     * @return a normal-distributed double centered on 0.0
     */
    public static double probitHighPrecision(double d)
    {
        double x = probit(d);
        if( d > 0.0 && d < 1.0) {
            double e = 0.5 * erfc(-x / Math.sqrt(2.0)) - d;
            double u = e * Math.sqrt(2.0 * Math.PI) * Math.exp((x * x) / 2.0);
            x = x - u / (1.0 + x * u / 2.0);
        }
        return x;
    }

    private static final double[] TABLE = new double[1027];

    static {
        for (int i = 0; i < 1024; i++) {
            TABLE[i+1] = probitHighPrecision(0.5 + i * 0x1p-11);
        }
        TABLE[0] = -TABLE[2];
        TABLE[1025] = probitHighPrecision(1.0 - 0x1p-12);
        TABLE[1026] = probitHighPrecision(1.0 - 0x1p-13);
    }

    /**
     * Given any {@code long} as input, this maps the full range of non-negative long values to much of the non-negative
     * half of the range of the normal distribution, and similarly maps all negative long values to their
     * equivalent-magnitude non-negative counterparts. Notably, an input of 0 will map to {@code 0.0}, an input of -1
     * will map to {@code -0.0}, and inputs of {@link Long#MIN_VALUE} and  {@link Long#MAX_VALUE} will map to larger
     * values that are equally far from 0.0 . If you only pass this small sequential inputs, there may be no detectable
     * difference between some outputs. This is meant to be given inputs with large differences if very different
     * outputs are desired.
     *
     * @param n any long; input patterns will be preserved
     * @return a normal-distributed double, matching patterns in {@code n}
     */
    public static double normal(long n) {
        final long sign = n >> 63;
        n ^= sign;
        final int top10 = (int) (n >>> 53);

        final double a = TABLE[top10], b = TABLE[top10+1], c = TABLE[top10+2], d = TABLE[top10+3],
                t = (n & 0x1FFFFFFFFFFFFFL) * 0x1p-53;
        // Catmull-Rom Spline between b and c, using the same sign as the original input.
        // Thanks to Robert Dunlop, https://www.mvps.org/directx/articles/catmull/ , for making this math clear.
        return Math.copySign((b + 0.5 *
                t * (c - a +
                t * ((2 * a - 5 * b + 4 * c - d) +
                        t * (-a + 3 * (b - c) + d)))), sign);

        // linear interpolation
//        final double b = TABLE[top10+1], c = TABLE[top10+2], t = (n & 0x1FFFFFFFFFFFFFL) * 0x1p-53;
//        return Math.copySign(t * (c - b) + b, sign);
    }

}
