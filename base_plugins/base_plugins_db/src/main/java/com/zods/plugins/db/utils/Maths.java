package com.zods.plugins.db.utils;
import org.apache.commons.lang3.StringUtils;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
/**
 * @author jianglong
 * @version 1.0
 * @Description
 * @createDate 2022-06-20
 */
public class Maths {
    public Maths() {
    }

    public static boolean gt(String value, String value2) {
        BigDecimal bigDecimal = new BigDecimal(value);
        BigDecimal bigDecimal1 = new BigDecimal(value2);
        return bigDecimal.compareTo(bigDecimal1) > 0;
    }

    public static boolean ge(String value, String value2) {
        return gt(value, value2) || eq(value, value2);
    }

    public static boolean eq(String value, String value2) {
        BigDecimal bigDecimal = new BigDecimal(value);
        BigDecimal bigDecimal1 = new BigDecimal(value2);
        return bigDecimal.compareTo(bigDecimal1) == 0;
    }

    public static <T> String plus(List<T> values, Function<T, String> function) {
        Optional<BigDecimal> reduce = values.stream().map(function).filter(StringUtils::isNotBlank).map(BigDecimal::new).reduce(BigDecimal::add);
        return reduce.isPresent() ? Double.toString(((BigDecimal)reduce.get()).doubleValue()) : Double.toString(BigDecimal.ZERO.doubleValue());
    }

    public static String plus(List<String> values) {
        return plus(values, (value) -> {
            return value;
        });
    }

    public static String plus(String... values) {
        List<String> listValues = Arrays.asList(values);
        return plus(listValues);
    }

    public static String minus(List<String> values) {
        Optional<BigDecimal> reduce = values.stream().map(BigDecimal::new).reduce(BigDecimal::subtract);
        return reduce.isPresent() ? Double.toString(((BigDecimal)reduce.get()).doubleValue()) : Double.toString(BigDecimal.ZERO.doubleValue());
    }

    public static String minus(String... values) {
        return minus(Arrays.asList(values));
    }

    public static <T> String multiply(List<T> values, Function<T, String> function) {
        Optional<BigDecimal> reduce = values.stream().map(function).map(BigDecimal::new).reduce(BigDecimal::multiply);
        return reduce.isPresent() ? Double.toString(((BigDecimal)reduce.get()).doubleValue()) : Double.toString(BigDecimal.ZERO.doubleValue());
    }

    public static String multiply(String... values) {
        return multiply(6, values);
    }

    public static String multiply(int scale, String... values) {
        Optional<BigDecimal> reduce = Arrays.asList(values).stream().map(BigDecimal::new).reduce(BigDecimal::multiply);
        if (reduce.isPresent()) {
            BigDecimal bigDecimal = (BigDecimal)reduce.get();
            bigDecimal = bigDecimal.setScale(scale, 4);
            return bigDecimal.toPlainString();
        } else {
            return Double.toString(BigDecimal.ZERO.doubleValue());
        }
    }

    public static String divide(String value, String value2) {
        BigDecimal bigDecimal = new BigDecimal(value);
        BigDecimal bigDecimal2 = new BigDecimal(value2);
        BigDecimal divide = bigDecimal.divide(bigDecimal2, 6, 4);
        return Double.toString(divide.doubleValue());
    }

    public static String strFormat(String value) {
        DecimalFormat format = new DecimalFormat("0.00");
        return format.format(new BigDecimal(value));
    }

    public static void main(String[] args) {
        String plus = multiply("12", "10");
        System.out.println(plus);
    }
}
