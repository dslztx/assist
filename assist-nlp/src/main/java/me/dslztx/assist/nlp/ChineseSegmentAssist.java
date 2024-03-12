package me.dslztx.assist.nlp;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.hankcs.hanlp.seg.Dijkstra.DijkstraSegment;
import com.hankcs.hanlp.seg.NShort.NShortSegment;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.SpeedTokenizer;

import lombok.extern.slf4j.Slf4j;
import me.dslztx.assist.util.CollectionAssist;
import me.dslztx.assist.util.StringAssist;

/**
 * 中文分词，底层基于HanLP自然语言处理工具包
 * 
 * https://github.com/hankcs/HanLP/tree/1.x?tab=readme-ov-file
 */
@Slf4j
public class ChineseSegmentAssist {

    private static DijkstraSegment shortestSegment;

    private static NShortSegment nShortSegment;

    static {
        init();
    }

    private static void init() {
        try {

            shortestSegment = new DijkstraSegment();
            shortestSegment.enableCustomDictionary(false).enablePlaceRecognize(true).enableOrganizationRecognize(true);

            nShortSegment = new NShortSegment();
            nShortSegment.enableCustomDictionary(false).enablePlaceRecognize(true).enableOrganizationRecognize(true);

        } catch (Exception e) {
            log.error("", e);
            throw new RuntimeException("init segment instance fail");
        }
    }

    /**
     * 极速词典分词：<br/>
     * 1、侧重速度，每秒数千万字符<br/>
     * 2、省内存<br/>
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

    /**
     * 最短路分词：<br/>
     * 1、速度与精度最佳平衡<br/>
     * 2、一百兆内存<br/>
     * 3、相较于"N最短路分词"，速度快几倍，效果稍差（对命名实体识别能力稍弱）但是一般场景精度已经足够<br/>
     */
    public static List<String> segmentDijkstra(String text, boolean noStopWord) {
        if (StringAssist.isBlank(text)) {
            return new ArrayList<>();
        }

        List<Term> termList = shortestSegment.seg(text);

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

    /**
     * N-最短路分词：<br/>
     * 1、速度与精度最佳平衡<br/>
     * 2、一百兆内存<br/>
     * 3、相较于"最短路分词"，速度慢几倍，效果稍好（对命名实体识别能力更强）<br/>
     */
    public static List<String> segmentNShort(String text, boolean noStopWord) {
        if (StringAssist.isBlank(text)) {
            return new ArrayList<>();
        }

        List<Term> termList = nShortSegment.seg(text);

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
