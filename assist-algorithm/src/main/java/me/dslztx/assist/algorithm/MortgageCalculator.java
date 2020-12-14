package me.dslztx.assist.algorithm;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 房贷计算，等额本金和等额本息
 */
public class MortgageCalculator {

    /**
     * https://baike.baidu.com/item/%E7%AD%89%E9%A2%9D%E6%9C%AC%E9%87%91
     */
    public static BigDecimal benjin(int totalLoan, double monthRatio, int originMonth, int actualMonth) {
        BigDecimal loan = BigDecimal.valueOf(totalLoan);

        BigDecimal ratio = BigDecimal.valueOf(monthRatio);

        BigDecimal monthRepayment = loan.divide(BigDecimal.valueOf(originMonth), 3, RoundingMode.HALF_UP);

        BigDecimal sum = BigDecimal.valueOf(0);

        for (int i = 1; i <= actualMonth; i++) {
            sum = sum.add(monthRepayment).add(loan.multiply(ratio));

            loan = loan.subtract(monthRepayment);
        }

        return sum.add(loan).setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * 参考 https://www.cnblogs.com/hanganglin/p/6777838.html
     */
    public static BigDecimal benxi(int totalLoan, double monthRatio, int originMonth, int actualMonth) {

        BigDecimal loan = BigDecimal.valueOf(totalLoan);

        BigDecimal ratio = BigDecimal.valueOf(monthRatio);

        BigDecimal factor = BigDecimal.valueOf(Math.pow(1 + ratio.doubleValue(), originMonth));

        BigDecimal monthRepayment = loan.multiply(ratio).multiply(factor).divide(factor.subtract(BigDecimal.valueOf(1)),
            3, RoundingMode.HALF_UP);

        BigDecimal leftRepayment = loan.multiply(BigDecimal.valueOf(Math.pow(ratio.doubleValue() + 1, actualMonth)));

        BigDecimal sum0 = BigDecimal.valueOf(1);
        for (int i = 1; i <= actualMonth - 1; i++) {
            BigDecimal tmp = BigDecimal.valueOf(Math.pow(ratio.doubleValue() + 1, i));

            sum0 = sum0.add(tmp);
        }

        sum0 = sum0.multiply(monthRepayment);

        leftRepayment = leftRepayment.subtract(sum0);

        return leftRepayment.add(monthRepayment.multiply(BigDecimal.valueOf(actualMonth))).setScale(2,
            RoundingMode.HALF_UP);
    }

}
