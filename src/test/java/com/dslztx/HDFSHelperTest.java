package com.dslztx;

import java.util.Random;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @date 2015年5月29日
 * @author dslztx
 */
public class HDFSHelperTest {
    private static Logger logger = LoggerFactory.getLogger(HDFSHelperTest.class);

    public void fileExistTest() {
        try {
            System.out.println(HDFSHelper.fileExists("/dir/stats/20150527"));
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    public void getSnappyCodecSeqFileWriterTest() {
        try {
            SequenceFile.Writer writer =
                    HDFSHelper.getSnappyCodecSeqFileWriter("/dir/stats/20150528", Text.class, IntWritable.class);
            String[] wordSet = {"Bye", "Goodbye", "Hadoop", "Hello", "World"};
            Random random = new Random();
            Text key = new Text();
            IntWritable value = new IntWritable();
            value.set(1);

            for (int i = 0; i < 100; i++) {
                int index = random.nextInt(5);
                key.set(wordSet[index]);
                writer.append(key, value);
            }
            writer.close();
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    public void getSeqFileReaderTest() {
        try {
            SequenceFile.Reader reader = HDFSHelper.getSeqFileReader("/dir/stats/20150527");
            Text key = new Text();
            Text value = new Text();
            while (reader.next(key, value)) {
                System.out.println("key:" + key);
                System.out.println("value:" + value);
            }
            reader.close();
        } catch (Exception e) {
            logger.error("", e);
        }
    }
}
