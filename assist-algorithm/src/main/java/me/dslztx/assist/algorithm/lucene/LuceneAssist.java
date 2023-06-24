package me.dslztx.assist.algorithm.lucene;

import java.io.File;
import java.nio.file.Path;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LuceneAssist {

    public static IndexWriter createIndexWriterStandardAnalyzer(Path indexPath) {
        return createIndexWriter(indexPath, new StandardAnalyzer());
    }

    public static IndexWriter createIndexWriterStandardAnalyzer(File indexPath) {
        return createIndexWriter(indexPath.toPath(), new StandardAnalyzer());
    }

    public static IndexWriter createIndexWriter(Path indexPath, Analyzer analyzer) {
        try {
            Directory directory = FSDirectory.open(indexPath);

            IndexWriterConfig config = new IndexWriterConfig(analyzer);

            return new IndexWriter(directory, config);
        } catch (Exception e) {
            log.error("", e);
            return null;
        }
    }

    public static IndexWriter createIndexWriter(File indexPath, Analyzer analyzer) {
        return createIndexWriter(indexPath.toPath(), analyzer);
    }

    public static IndexSearcher obtainIndexSearcher(Path indexPath) {
        try {
            Directory directory = FSDirectory.open(indexPath);

            DirectoryReader ireader = DirectoryReader.open(directory);

            return new IndexSearcher(ireader);
        } catch (Exception e) {
            log.error("", e);
            return null;
        }
    }

    public static IndexSearcher obtainIndexSearcher(File indexPath) {
        return obtainIndexSearcher(indexPath.toPath());
    }
}
