package me.dslztx.assist.algorithm.lucene;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.util.IOUtils;
import org.junit.Assert;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

/**
 * Document中Field的含义：https://www.cnblogs.com/shoufeng/p/9384832.html
 */
@Slf4j
public class LuceneAssistTest {

    @Test
    public void test0() {
        Path indexPath = null;

        try {
            indexPath = Files.createTempDirectory("tempIndex");

            IndexWriter iwriter = LuceneAssist.createIndexWriterStandardAnalyzer(indexPath);

            Document book0 = new Document();
            book0.add(new StringField("BookID", "book0", Field.Store.YES));
            book0.add(new TextField("BookName", "《百年孤独》", Field.Store.YES));
            book0.add(new FloatPoint("BookPrice", 100.5F));
            book0.add(new StoredField("BookPng", new byte[] {1, 2, 3, 4, 5}));
            book0.add(new TextField("BookDesc",
                "《百年孤独》，是哥伦比亚作家加西亚·马尔克斯创作的长篇小说，是其代表作，也是拉丁美洲魔幻现实主义文学的代表作，被誉为“再现拉丁美洲历史社会图景的鸿篇巨著”。\n"
                    + "作品描写了布恩迪亚家族七代人的传奇故事，以及加勒比海沿岸小镇马孔多的百年兴衰，反映了拉丁美洲一个世纪以来风云变幻的历史。作品融入神话传说、民间故事、宗教典故等神秘因素，巧妙地糅合了现实与虚幻，展现出一个瑰丽的想象世界，成为20世纪重要的经典文学巨著之一。",
                Field.Store.YES));

            iwriter.addDocument(book0);

            Document book1 = new Document();
            book1.add(new StringField("BookID", "book1", Field.Store.YES));
            book1.add(new TextField("BookName", "《活着》", Field.Store.YES));
            book1.add(new FloatPoint("BookPrice", 200.5F));
            book1.add(new StoredField("BookPng", new byte[] {6, 7, 8, 9, 10}));
            book1.add(new TextField("BookDesc", "《活着》是中国当代作家余华创作的长篇小说，首次发表于《收获》1992年第6期。\n"
                + "《活着》讲述了在大时代背景下，随着内战、三反五反、大跃进、“文化大革命”等社会变革，徐福贵的人生和家庭不断经受着苦难，到了最后所有亲人都先后离他而去，仅剩下年老的他和一头老牛相依为命。小说以普通、平实的故事情节讲述了在急剧变革的时代中福贵的不幸遭遇和坎坷命运，在冷静的笔触中展现了生命的意义和存在的价值，揭示了命运的无奈，与生活的不可捉摸。 [1]\n"
                + "1994年，改编自该小说的同名剧情片《活着》上映，由张艺谋执导，葛优、巩俐等主演 [19] 1998年7月，《活着》获得意大利“格林扎纳·卡佛”文学奖。 [1]", Field.Store.YES));

            iwriter.addDocument(book1);

            iwriter.close();

            // Now search the index:
            IndexSearcher isearcher = LuceneAssist.obtainIndexSearcher(indexPath);

            // Parse a simple query that searches for "text":
            QueryParser parser = new QueryParser("BookDesc", new StandardAnalyzer());
            Query query = parser.parse("加勒比海");

            ScoreDoc[] hits = isearcher.search(query, 10).scoreDocs;
            Document document = isearcher.doc(hits[0].doc);
            System.out.println(document);

            Assert.assertTrue(hits.length == 1);
        } catch (Exception e) {
            log.error("", e);
            Assert.fail();
        } finally {
            try {
                IOUtils.rm(indexPath);
            } catch (IOException e) {
                log.error("", e);
            }
        }
    }
}