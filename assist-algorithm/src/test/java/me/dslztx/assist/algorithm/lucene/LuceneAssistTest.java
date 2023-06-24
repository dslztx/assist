package me.dslztx.assist.algorithm.lucene;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.util.IOUtils;
import org.junit.Assert;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LuceneAssistTest {

    @Test
    public void test0() {
        Path indexPath = null;

        try {
            indexPath = Files.createTempDirectory("tempIndex");

            IndexWriter iwriter = LuceneAssist.createIndexWriterStandardAnalyzer(indexPath);

            Document doc = new Document();
            String text = "This is the text to be indexed.";
            doc.add(new Field("fieldname", text, TextField.TYPE_STORED));
            iwriter.addDocument(doc);
            iwriter.close();

            // Now search the index:
            IndexSearcher isearcher = LuceneAssist.obtainIndexSearcher(indexPath);

            // Parse a simple query that searches for "text":
            QueryParser parser = new QueryParser("fieldname", new StandardAnalyzer());
            Query query = parser.parse("text");

            ScoreDoc[] hits = isearcher.search(query, 10).scoreDocs;

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