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

            Assert.assertTrue(r.compareTo(BigDecimal.valueOf(3265312.38)) == 0);
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

            Assert.assertTrue(r.compareTo(BigDecimal.valueOf(3465576.14)) == 0);
        } catch (Exception e) {
            logger.error("", e);
            Assert.fail();
        }
    }
}