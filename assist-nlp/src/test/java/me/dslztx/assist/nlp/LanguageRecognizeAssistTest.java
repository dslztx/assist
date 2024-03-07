package me.dslztx.assist.nlp;

import org.junit.Assert;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LanguageRecognizeAssistTest {

    @Test
    public void isChineseCharByUnicodeRange() {
        try {
            Assert.assertTrue(LanguageRecognizeAssist.isChineseCharByUnicodeRange('中'));
            Assert.assertFalse(LanguageRecognizeAssist.isChineseCharByUnicodeRange('；'));
            Assert.assertFalse(LanguageRecognizeAssist.isChineseCharByUnicodeRange('。'));
            Assert.assertFalse(LanguageRecognizeAssist.isChineseCharByUnicodeRange('a'));
            Assert.assertFalse(LanguageRecognizeAssist.isChineseCharByUnicodeRange('1'));
            Assert.assertFalse(LanguageRecognizeAssist.isChineseCharByUnicodeRange('α'));
            Assert.assertFalse(LanguageRecognizeAssist.isChineseCharByUnicodeRange('①'));
        } catch (Exception e) {
            log.error("", e);
            Assert.fail();
        }
    }

    @Test
    public void isChineseCharByUnicodeBlock() {
        try {
            Assert.assertTrue(LanguageRecognizeAssist.isChineseCharByUnicodeBlock('中'));
            Assert.assertFalse(LanguageRecognizeAssist.isChineseCharByUnicodeBlock('；'));
            Assert.assertFalse(LanguageRecognizeAssist.isChineseCharByUnicodeBlock('。'));
            Assert.assertFalse(LanguageRecognizeAssist.isChineseCharByUnicodeBlock('a'));
            Assert.assertFalse(LanguageRecognizeAssist.isChineseCharByUnicodeBlock('1'));
            Assert.assertFalse(LanguageRecognizeAssist.isChineseCharByUnicodeBlock('α'));
            Assert.assertFalse(LanguageRecognizeAssist.isChineseCharByUnicodeBlock('①'));
        } catch (Exception e) {
            log.error("", e);
            Assert.fail();
        }
    }

    @Test
    public void isChineseCharByUnicodeScript() {
        try {
            Assert.assertTrue(LanguageRecognizeAssist.isChineseCharByUnicodeScript('中'));
            Assert.assertFalse(LanguageRecognizeAssist.isChineseCharByUnicodeScript('；'));
            Assert.assertFalse(LanguageRecognizeAssist.isChineseCharByUnicodeScript('。'));
            Assert.assertFalse(LanguageRecognizeAssist.isChineseCharByUnicodeScript('a'));
            Assert.assertFalse(LanguageRecognizeAssist.isChineseCharByUnicodeScript('1'));
            Assert.assertFalse(LanguageRecognizeAssist.isChineseCharByUnicodeScript('α'));
            Assert.assertFalse(LanguageRecognizeAssist.isChineseCharByUnicodeScript('①'));
        } catch (Exception e) {
            log.error("", e);
            Assert.fail();
        }
    }

    @Test
    public void isChinesePunctuation() {
        try {
            Assert.assertTrue(LanguageRecognizeAssist.isChinesePunctuation('；'));
            Assert.assertTrue(LanguageRecognizeAssist.isChinesePunctuation('。'));
            Assert.assertFalse(LanguageRecognizeAssist.isChinesePunctuation('中'));
            Assert.assertFalse(LanguageRecognizeAssist.isChinesePunctuation('a'));
            Assert.assertFalse(LanguageRecognizeAssist.isChinesePunctuation('1'));
            Assert.assertFalse(LanguageRecognizeAssist.isChinesePunctuation('α'));
            Assert.assertFalse(LanguageRecognizeAssist.isChinesePunctuation('①'));
        } catch (Exception e) {
            log.error("", e);
            Assert.fail();
        }
    }

    @Test
    public void isChineseCharWhole() {
        try {
            Assert.assertTrue(LanguageRecognizeAssist.isChineseCharWhole("中华人民共和国"));
            Assert.assertFalse(LanguageRecognizeAssist.isChineseCharWhole("中华人民1共和国"));
            Assert.assertFalse(LanguageRecognizeAssist.isChineseCharWhole("中华人民2共和国"));
            Assert.assertFalse(LanguageRecognizeAssist.isChineseCharWhole("中华人民；共和国"));
        } catch (Exception e) {
            log.error("", e);
            Assert.fail();
        }
    }

    @Test
    public void existChineseChar() {
        try {
            Assert.assertTrue(LanguageRecognizeAssist.existChineseChar("中华人民共和国"));
            Assert.assertTrue(LanguageRecognizeAssist.existChineseChar("中华人民1共和国"));
            Assert.assertTrue(LanguageRecognizeAssist.existChineseChar("中华人民2共和国"));
            Assert.assertTrue(LanguageRecognizeAssist.existChineseChar("中华人民；共和国"));
            Assert.assertFalse(LanguageRecognizeAssist.existChineseChar("abcdefghij"));
        } catch (Exception e) {
            log.error("", e);
            Assert.fail();
        }
    }

    @Test
    public void isChineseCharOrPunctuationWhole() {
        try {
            Assert.assertTrue(LanguageRecognizeAssist.isChineseCharOrPunctuationWhole("中华人民共和国"));
            Assert.assertTrue(LanguageRecognizeAssist.isChineseCharOrPunctuationWhole("中华人民；共和国"));
            Assert.assertFalse(LanguageRecognizeAssist.isChineseCharOrPunctuationWhole("中华人民1共和国"));
            Assert.assertFalse(LanguageRecognizeAssist.isChineseCharOrPunctuationWhole("中华人民2共和国"));
            Assert.assertFalse(LanguageRecognizeAssist.isChineseCharOrPunctuationWhole("abcdefghij"));
        } catch (Exception e) {
            log.error("", e);
            Assert.fail();
        }
    }

    @Test
    public void existChineseCharOrPunctuationWhole() {
        try {
            Assert.assertTrue(LanguageRecognizeAssist.existChineseCharOrPunctuation("中华人民共和国"));
            Assert.assertTrue(LanguageRecognizeAssist.existChineseCharOrPunctuation("中华人民1共和国"));
            Assert.assertTrue(LanguageRecognizeAssist.existChineseCharOrPunctuation("中华人民2共和国"));
            Assert.assertTrue(LanguageRecognizeAssist.existChineseCharOrPunctuation("中华人民；共和国"));
            Assert.assertFalse(LanguageRecognizeAssist.existChineseCharOrPunctuation("abcdefghij"));
        } catch (Exception e) {
            log.error("", e);
            Assert.fail();
        }
    }
}