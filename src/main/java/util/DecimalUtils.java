package util;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public final class DecimalUtils {

    // ===== GLOBAL CONSTANTS =====

    // High precision, deterministic
    public static final MathContext MC =
            new MathContext(16, RoundingMode.HALF_UP);

    // Comparison tolerance
    public static final BigDecimal EPSILON =
            new BigDecimal("0.00000001");

    public static final BigDecimal ZERO = BigDecimal.ZERO;
    public static final BigDecimal ONE = BigDecimal.ONE;
    public static final BigDecimal HUNDRED = new BigDecimal("100");

    private DecimalUtils() {
        // utility class
    }

    // ===== SAFE CONSTRUCTION =====

    /**
     * Safe conversion from double to BigDecimal.
     * NEVER use new BigDecimal(double).
     */
    public static BigDecimal bd(double value) {
        return BigDecimal.valueOf(value);
    }

    /**
     * Safe conversion from String to BigDecimal.
     * Preferred when reading config / constants.
     */
    public static BigDecimal bd(String value) {
        return new BigDecimal(value, MC);
    }

    // ===== COMPARISONS =====

    public static boolean equals(BigDecimal a, BigDecimal b) {
        return a.subtract(b).abs().compareTo(EPSILON) <= 0;
    }

    public static boolean greaterThan(BigDecimal a, BigDecimal b) {
        return a.subtract(b).compareTo(EPSILON) > 0;
    }

    public static boolean lessThan(BigDecimal a, BigDecimal b) {
        return b.subtract(a).compareTo(EPSILON) > 0;
    }

    // ===== ROUNDING =====

    /**
     * Use ONLY when returning values to API layer.
     */
    public static BigDecimal round(BigDecimal value, int scale) {
        return value.setScale(scale, RoundingMode.HALF_UP);
    }

    // ===== POWER FUNCTION =====

    /**
     * Deterministic BigDecimal power calculation.
     * Used for compound interest & inflation.
     *
     * @param base e.g. (1 + rate)
     * @param exponent number of years
     */
    public static BigDecimal pow(BigDecimal base, int exponent) {
        if (exponent < 0) {
            throw new IllegalArgumentException("Exponent must be non-negative");
        }

        BigDecimal result = ONE;

        for (int i = 0; i < exponent; i++) {
            result = result.multiply(base, MC);
        }

        return result;
    }
}

