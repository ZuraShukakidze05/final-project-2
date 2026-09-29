package ge.tbc.testautomation.utils;

import ge.tbc.testautomation.constants.Constants;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LoanCalculationUtils {

    public static BigDecimal calculateMonthlyPayment(double principal,
                                                     double annualNominalRatePercent,
                                                     int termMonths) {
        double monthlyRate = (annualNominalRatePercent / 100) / 12;

        double payment;
        if (monthlyRate == 0) {
            payment = principal / termMonths;
        } else {
            double factor = Math.pow(1 + monthlyRate, termMonths);
            payment = principal * monthlyRate * factor / (factor - 1);
        }

        return BigDecimal.valueOf(payment).setScale(Constants.CENT_SCALE, RoundingMode.HALF_UP);
    }

    public static Pattern toleranceRangePattern(double expected, double tolerance) {
        BigDecimal center = BigDecimal.valueOf(expected);
        BigDecimal tol = BigDecimal.valueOf(tolerance);
        BigDecimal max = center.add(tol);

        String alternatives = Stream
                .iterate(center.subtract(tol), v -> v.compareTo(max) <= 0, v -> v.add(Constants.CENT))
                .map(v -> toNumberRegex(v.setScale(Constants.CENT_SCALE, RoundingMode.HALF_UP).toPlainString()))
                .collect(Collectors.joining(Constants.ALTERNATION));

        return Pattern.compile(Constants.AMOUNT_PREFIX_REGEX + alternatives + Constants.AMOUNT_SUFFIX_REGEX);
    }

    private static String toNumberRegex(String value) {
        String[] parts = value.split(Constants.DECIMAL_POINT_REGEX);
        String intPart = parts[0];
        String cents = parts[1];

        if (intPart.length() > Constants.THOUSANDS_GROUP_SIZE) {
            int cut = intPart.length() - Constants.THOUSANDS_GROUP_SIZE;
            intPart = intPart.substring(0, cut)
                    + Constants.OPTIONAL_THOUSANDS_SEPARATOR_REGEX
                    + intPart.substring(cut);
        }

        if (cents.equals(Constants.ZERO_CENTS)) {
            return intPart + Constants.OPTIONAL_ZERO_CENTS_REGEX;
        }
        if (cents.endsWith(Constants.TRAILING_ZERO)) {
            return intPart + Constants.DECIMAL_POINT_REGEX
                    + cents.charAt(0) + Constants.OPTIONAL_TRAILING_ZERO_REGEX;
        }
        return intPart + Constants.DECIMAL_POINT_REGEX + cents;
    }
}