package me.dslztx.assist.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.hankcs.algorithm.AhoCorasickDoubleArrayTrie;

import me.dslztx.assist.util.CollectionAssist;
import me.dslztx.assist.util.StringAssist;

/**
 * Alfred V.Aho和Margaret J.Corasick提出的AC算法，又称AC自动机
 */
public class ACAutomaton {

    List<String> keywordList = null;

    AhoCorasickDoubleArrayTrie<String> acAutomaton = null;

    public ACAutomaton(List<String> keywordList) {
        if (CollectionAssist.isEmpty(keywordList)) {
            throw new RuntimeException("keyword list is empty");
        }

        this.keywordList = keywordList;

        build();
    }

    private void build() {
        TreeMap<String, String> map = new TreeMap<String, String>();

        for (String keyword : keywordList) {
            map.put(keyword, keyword);
        }

        acAutomaton = new AhoCorasickDoubleArrayTrie<String>();

        acAutomaton.build(map);
    }

    public List<PatternHit> hit(String text) {
        List<PatternHit> result = new ArrayList<PatternHit>();

        if (StringAssist.isBlank(text)) {
            return result;
        }

        List<AhoCorasickDoubleArrayTrie.Hit<String>> hitList = acAutomaton.parseText(text);

        if (CollectionAssist.isNotEmpty(hitList)) {
            for (AhoCorasickDoubleArrayTrie.Hit<String> hit : hitList) {
                result.add(new PatternHit(hit.begin, hit.end, hit.value));
            }
        }

        return result;
    }
}
