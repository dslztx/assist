package me.dslztx.assist.nlp;

import org.junit.Assert;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LanguageAssistTest {

    @Test
    public void isChineseCharByUnicodeRange() {
        try {
            Assert.assertTrue(LanguageAssist.isChineseCharByUnicodeRange('中'));
            Assert.assertFalse(LanguageAssist.isChineseCharByUnicodeRange('；'));
            Assert.assertFalse(LanguageAssist.isChineseCharByUnicodeRange('。'));
            Assert.assertFalse(LanguageAssist.isChineseCharByUnicodeRange('a'));
            Assert.assertFalse(LanguageAssist.isChineseCharByUnicodeRange('1'));
            Assert.assertFalse(LanguageAssist.isChineseCharByUnicodeRange('α'));
            Assert.assertFalse(LanguageAssist.isChineseCharByUnicodeRange('①'));
        } catch (Exception e) {
            log.error("", e);
            Assert.fail();
        }
    }

    @Test
    public void isChineseCharByUnicodeBlock() {
        try {
            Assert.assertTrue(LanguageAssist.isChineseCharByUnicodeBlock('中'));
            Assert.assertFalse(LanguageAssist.isChineseCharByUnicodeBlock('；'));
            Assert.assertFalse(LanguageAssist.isChineseCharByUnicodeBlock('。'));
            Assert.assertFalse(LanguageAssist.isChineseCharByUnicodeBlock('a'));
            Assert.assertFalse(LanguageAssist.isChineseCharByUnicodeBlock('1'));
            Assert.assertFalse(LanguageAssist.isChineseCharByUnicodeBlock('α'));
            Assert.assertFalse(LanguageAssist.isChineseCharByUnicodeBlock('①'));
        } catch (Exception e) {
            log.error("", e);
            Assert.fail();
        }
    }

    @Test
    public void isChineseCharByUnicodeScript() {
        try {
            Assert.assertTrue(LanguageAssist.isChineseCharByUnicodeScript('中'));
            Assert.assertFalse(LanguageAssist.isChineseCharByUnicodeScript('；'));
            Assert.assertFalse(LanguageAssist.isChineseCharByUnicodeScript('。'));
            Assert.assertFalse(LanguageAssist.isChineseCharByUnicodeScript('a'));
            Assert.assertFalse(LanguageAssist.isChineseCharByUnicodeScript('1'));
            Assert.assertFalse(LanguageAssist.isChineseCharByUnicodeScript('α'));
            Assert.assertFalse(LanguageAssist.isChineseCharByUnicodeScript('①'));
        } catch (Exception e) {
            log.error("", e);
            Assert.fail();
        }
    }

    @Test
    public void isChinesePunctuation() {
        try {
            Assert.assertTrue(LanguageAssist.isChinesePunctuation('；'));
            Assert.assertTrue(LanguageAssist.isChinesePunctuation('。'));
            Assert.assertFalse(LanguageAssist.isChinesePunctuation('中'));
            Assert.assertFalse(LanguageAssist.isChinesePunctuation('a'));
            Assert.assertFalse(LanguageAssist.isChinesePunctuation('1'));
            Assert.assertFalse(LanguageAssist.isChinesePunctuation('α'));
            Assert.assertFalse(LanguageAssist.isChinesePunctuation('①'));
        } catch (Exception e) {
            log.error("", e);
            Assert.fail();
        }
    }

    @Test
    public void isChineseCharWhole() {
        try {
            Assert.assertTrue(LanguageAssist.isChineseCharWhole("中华人民共和国"));
            Assert.assertFalse(LanguageAssist.isChineseCharWhole("中华人民1共和国"));
            Assert.assertFalse(LanguageAssist.isChineseCharWhole("中华人民2共和国"));
            Assert.assertFalse(LanguageAssist.isChineseCharWhole("中华人民；共和国"));
        } catch (Exception e) {
            log.error("", e);
            Assert.fail();
        }
    }

    @Test
    public void existChineseChar() {
        try {
            Assert.assertTrue(LanguageAssist.existChineseChar("中华人民共和国"));
            Assert.assertTrue(LanguageAssist.existChineseChar("中华人民1共和国"));
            Assert.assertTrue(LanguageAssist.existChineseChar("中华人民2共和国"));
            Assert.assertTrue(LanguageAssist.existChineseChar("中华人民；共和国"));
            Assert.assertFalse(LanguageAssist.existChineseChar("abcdefghij"));
        } catch (Exception e) {
            log.error("", e);
            Assert.fail();
        }
    }

    @Test
    public void isChineseCharOrPunctuationWhole() {
        try {
            Assert.assertTrue(LanguageAssist.isChineseCharOrPunctuationWhole("中华人民共和国"));
            Assert.assertTrue(LanguageAssist.isChineseCharOrPunctuationWhole("中华人民；共和国"));
            Assert.assertFalse(LanguageAssist.isChineseCharOrPunctuationWhole("中华人民1共和国"));
            Assert.assertFalse(LanguageAssist.isChineseCharOrPunctuationWhole("中华人民2共和国"));
            Assert.assertFalse(LanguageAssist.isChineseCharOrPunctuationWhole("abcdefghij"));
        } catch (Exception e) {
            log.error("", e);
            Assert.fail();
        }
    }

    @Test
    public void existChineseCharOrPunctuationWhole() {
        try {
            Assert.assertTrue(LanguageAssist.existChineseCharOrPunctuation("中华人民共和国"));
            Assert.assertTrue(LanguageAssist.existChineseCharOrPunctuation("中华人民1共和国"));
            Assert.assertTrue(LanguageAssist.existChineseCharOrPunctuation("中华人民2共和国"));
            Assert.assertTrue(LanguageAssist.existChineseCharOrPunctuation("中华人民；共和国"));
            Assert.assertFalse(LanguageAssist.existChineseCharOrPunctuation("abcdefghij"));
        } catch (Exception e) {
            log.error("", e);
            Assert.fail();
        }
    }

    @Test
    public void guessLanguageTest() {
        try {
            String text0 = "你好啊，这是一段测试文字，hello world";
            Assert.assertTrue(LanguageAssist.guessLanguage(text0) == LanguageAssist.Language.CHINESE);

            String text1 = "나는 가난한 탁발승이오. 내가 가진 거라고는 물레와 교도소에서 쓰던 밥그릇과 염소 젖 한 깡통, 허름한 요포 여섯장, 수건 그리고 대단치도 않은 평판 이것 "
                + "뿐이오. hello world 这是朝鲜语";
            Assert.assertTrue(LanguageAssist.guessLanguage(text1) == LanguageAssist.Language.KOREA);

            String text2 = "うみは ひろいな\n" + "おおきいな\n" + "つきは のぼるし\n" + "ひがしずむ\n" + "\n" + "うみは おおなみ\n" + "あおいなみ\n"
                + "ゆれて どこまで\n" + "つづくやら\n" + "\n" + "うみに おふねを\n" + "うかばせて\n" + "いって みたいな\n" + "よそのくに\n" + "\n"
                + "うみは ひろいな\n" + "おおきいな\n" + "つきは のぼるし\n" + "ひがしずむ  hello world 这是日语";
            Assert.assertTrue(LanguageAssist.guessLanguage(text2) == LanguageAssist.Language.JAPAN);

            String text3 =
                "Почти треть россиян (30%) придерживаются однозначного мнения, что отец может не хуже матери "
                    + "ухаживать hello world 这是俄语";
            Assert.assertTrue(LanguageAssist.guessLanguage(text3) == LanguageAssist.Language.CYRILLIC);

            String text4 = "I am a 12 years old smart and handsome boy..I am proud of myself.Because I can get "
                + "perfect scores 这是英语";
            Assert.assertTrue(LanguageAssist.guessLanguage(text4) == LanguageAssist.Language.LATIN);

            String text5 =
                "Debout les damnés de la terre Debout les forçats de la faim La raison tonne en son cratère " + "这是法语";
            Assert.assertTrue(LanguageAssist.guessLanguage(text5) == LanguageAssist.Language.LATIN);

            String text6 = "Wie lange wollen Sie hier bleiben, Wieviel kostet das, Darf ich kurz dich stoeren 这是德语";
            Assert.assertTrue(LanguageAssist.guessLanguage(text6) == LanguageAssist.Language.LATIN);

            String text7 = "Fidanzamento e matrimonio, Gravidanza,parto ,Separazione,divorzio, 这是意大利语";
            Assert.assertTrue(LanguageAssist.guessLanguage(text7) == LanguageAssist.Language.LATIN);

            String text8 = "مرحبا\n" + "كيف حالك؟\n" + "这是阿拉伯语.انا بخير, شكرا";
            Assert.assertTrue(LanguageAssist.guessLanguage(text8) == LanguageAssist.Language.ARABIC);

            String text9 = "قارشى ئالىمىز\n" + "ئەسسالامۇ ئەلەيكۇم!\n" + "ياخشىمۇسىز؟这是维吾尔语";
            Assert.assertTrue(LanguageAssist.guessLanguage(text9) == LanguageAssist.Language.ARABIC);

            String text10 = "Αυτό το απόσπασμα， η αγάπη μερικές φορές για πάντα， μερικές φορές αξέχαστη. Αυτό είναι "
                + "το όνομα και του χαρακτήρα του， Qin Shuyi 这是希腊语";
            Assert.assertTrue(LanguageAssist.guessLanguage(text10) == LanguageAssist.Language.GREEK);

            String text11 = "階段などの一きざみ。転じて、事件の一くぎり、地位・技能の一段階など";
            Assert.assertTrue(LanguageAssist.guessLanguage(text11) == LanguageAssist.Language.JAPAN);
        } catch (Exception e) {
            log.error("", e);
            Assert.fail();
        }
    }
}