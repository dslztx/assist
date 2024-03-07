package me.dslztx.assist.nlp;

import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;
import me.dslztx.assist.util.*;

/**
 * 中文停用词判断
 *
 * 停用词数据文件来自https://github.com/goto456/stopwords
 * 
 */
@Slf4j
public class ChineseStopWordAssist {

    private static Set<String> set = new HashSet<String>();

    static {
        init();
    }

    private static void init() {
        BufferedReader in = null;

        try {
            in = IOAssist.bufferedReader(ClassPathResourceAssist.locateInputStream("cn_stopwords.txt"),
                StandardCharsets.UTF_8);

            String line;
            while ((line = in.readLine()) != null) {
                if (StringAssist.isNotBlank(line)) {
                    set.add(line);
                }
            }

            if (CollectionAssist.isEmpty(set)) {
                throw new RuntimeException("no stop words loaded");
            }
        } catch (Exception e) {
            log.error("", e);

            throw new RuntimeException("can not load stop words");
        } finally {
            CloseableAssist.closeQuietly(in);
        }
    }

    public static boolean isStopWord(String word) {
        return set.contains(word);
    }
}
