package me.dslztx.assist.nlp;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.SpeedTokenizer;

import me.dslztx.assist.util.CollectionAssist;
import me.dslztx.assist.util.StringAssist;

/**
 * 中文分词，底层基于HanLP自然语言处理工具包
 * 
 * https://github.com/hankcs/HanLP/tree/1.x?tab=readme-ov-file
 */
public class ChineseSegmentAssist {

    /**
     * 侧重速度，每秒数千万字符；省内存
     */
    public static List<String> segmentBasedOnDict(String text, boolean noStopWord) {
        if (StringAssist.isBlank(text)) {
            return new ArrayList<>();
        }

        List<Term> termList = SpeedTokenizer.segment(text);

        if (CollectionAssist.isEmpty(termList)) {
            return new ArrayList<>();
        }

        List<String> result = new ArrayList<>();
        if (noStopWord) {
            for (Term term : termList) {
                if (Objects.nonNull(term) && StringAssist.isNotBlank(term.word)
                    && !ChineseStopWordAssist.isStopWord(term.word)) {
                    result.add(term.word);
                }
            }
        } else {
            for (Term term : termList) {
                if (Objects.nonNull(term) && StringAssist.isNotBlank(term.word)) {
                    result.add(term.word);
                }
            }
        }

        return result;
    }
}
