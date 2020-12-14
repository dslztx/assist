package me.dslztx.assist.algorithm;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MortgageCalculatorTest {
    private static final Logger logger = LoggerFactory.getLogger(MortgageCalculatorTest.class);

    @Test
    public void benjinTest() {
        try {
            double doubleMonthRatio = 0.0538 / 12;

            BigDecimal r = MortgageCalculator.benjin(2120000, doubleMonthRatio, 240, 240);
            BigDecimal r2 = MortgageCalculator.benjin(2120000, doubleMonthRatio, 300, 300);
            BigDecimal r3 = MortgageCalculator.benjin(2120000, doubleMonthRatio, 360, 360);

            Assert.assertTrue(r.compareTo(BigDecimal.valueOf(3265312.38)) == 0);
            Assert.assertTrue(r2.compareTo(BigDecimal.valueOf(3550452.27)) == 0);
            Assert.assertTrue(r3.compareTo(BigDecimal.valueOf(3835592.3)) == 0);
        } catch (Exception e) {
            logger.error("", e);
            Assert.fail();
        }
    }

    @Test
    public void benxiTest() {
        try {
            double doubleMonthRatio = 0.0538 / 12;

            BigDecimal r = MortgageCalculator.benxi(2120000, doubleMonthRatio, 240, 240);
            BigDecimal r2 = MortgageCalculator.benxi(2120000, doubleMonthRatio, 300, 300);
            BigDecimal r3 = MortgageCalculator.benxi(2120000, doubleMonthRatio, 360, 360);

            Assert.assertTrue(r.compareTo(BigDecimal.valueOf(3465576.14)) == 0);
            Assert.assertTrue(r2.compareTo(BigDecimal.valueOf(3860150.36)) == 0);
            Assert.assertTrue(r3.compareTo(BigDecimal.valueOf(4276079.96)) == 0);
        } catch (Exception e) {
            logger.error("", e);
            Assert.fail();
        }
    }
}