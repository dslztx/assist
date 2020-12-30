package me.dslztx.assist.util;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TextAssistTest {
    private static final Logger logger = LoggerFactory.getLogger(TextAssistTest.class);

    @Test
    public void guessLanguageTest() {
        try {
            String text0 = "你好啊，这是一段测试文字，hello world";
            Assert.assertTrue(TextAssist.guessLanguage(text0) == TextAssist.Language.CHINESE);

            String text1 = "나는 가난한 탁발승이오. 내가 가진 거라고는 물레와 교도소에서 쓰던 밥그릇과 염소 젖 한 깡통, 허름한 요포 여섯장, 수건 그리고 대단치도 않은 평판 이것 "
                + "뿐이오. hello world 这是朝鲜语";
            Assert.assertTrue(TextAssist.guessLanguage(text1) == TextAssist.Language.KOREA);

            String text2 = "うみは ひろいな\n" + "おおきいな\n" + "つきは のぼるし\n" + "ひがしずむ\n" + "\n" + "うみは おおなみ\n" + "あおいなみ\n"
                + "ゆれて どこまで\n" + "つづくやら\n" + "\n" + "うみに おふねを\n" + "うかばせて\n" + "いって みたいな\n" + "よそのくに\n" + "\n"
                + "うみは ひろいな\n" + "おおきいな\n" + "つきは のぼるし\n" + "ひがしずむ  hello world 这是日语";
            Assert.assertTrue(TextAssist.guessLanguage(text2) == TextAssist.Language.JAPAN);

            String text3 =
                "Почти треть россиян (30%) придерживаются однозначного мнения, что отец может не хуже матери "
                    + "ухаживать hello world 这是俄语";
            Assert.assertTrue(TextAssist.guessLanguage(text3) == TextAssist.Language.CYRILLIC);

            String text4 = "I am a 12 years old smart and handsome boy..I am proud of myself.Because I can get "
                + "perfect scores 这是英语";
            Assert.assertTrue(TextAssist.guessLanguage(text4) == TextAssist.Language.LATIN);

            String text5 =
                "Debout les damnés de la terre Debout les forçats de la faim La raison tonne en son cratère " + "这是法语";
            Assert.assertTrue(TextAssist.guessLanguage(text5) == TextAssist.Language.LATIN);

            String text6 = "Wie lange wollen Sie hier bleiben, Wieviel kostet das, Darf ich kurz dich stoeren 这是德语";
            Assert.assertTrue(TextAssist.guessLanguage(text6) == TextAssist.Language.LATIN);

            String text7 = "Fidanzamento e matrimonio, Gravidanza,parto ,Separazione,divorzio, 这是意大利语";
            Assert.assertTrue(TextAssist.guessLanguage(text7) == TextAssist.Language.LATIN);

            String text8 = "مرحبا\n" + "كيف حالك؟\n" + "这是阿拉伯语.انا بخير, شكرا";
            Assert.assertTrue(TextAssist.guessLanguage(text8) == TextAssist.Language.ARABIC);

            String text9 = "قارشى ئالىمىز\n" + "ئەسسالامۇ ئەلەيكۇم!\n" + "ياخشىمۇسىز؟这是维吾尔语";
            Assert.assertTrue(TextAssist.guessLanguage(text9) == TextAssist.Language.ARABIC);

            String text10 = "Αυτό το απόσπασμα， η αγάπη μερικές φορές για πάντα， μερικές φορές αξέχαστη. Αυτό είναι "
                + "το όνομα και του χαρακτήρα του， Qin Shuyi 这是希腊语";
            Assert.assertTrue(TextAssist.guessLanguage(text10) == TextAssist.Language.GREEK);

        } catch (Exception e) {
            logger.error("", e);
            Assert.fail();
        }
    }
}