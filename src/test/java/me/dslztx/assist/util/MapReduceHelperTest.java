package me.dslztx.assist.util;

import java.io.IOException;
import java.util.Arrays;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.dslztx.assist.util.MapReduceHelper.InputSetting;
import me.dslztx.assist.util.MapReduceHelper.OutputKeyValuePair;
import me.dslztx.assist.util.MapReduceHelper.OutputSetting;

/**
 * @date 2015年5月29日
 * @author dslztx
 */
public class MapReduceHelperTest {
    private static Logger logger = LoggerFactory.getLogger(MapReduceHelperTest.class);

    public void createJobTest() {
        try {
            InputSetting inputSetting1 =
                    new InputSetting(new Path("/dir/stats/20150527"), SequenceFileInputFormat.class,
                            WordCountMapper.class);
            InputSetting inputSetting2 =
                    new InputSetting(new Path("/dir/stats/20150528"), SequenceFileInputFormat.class,
                            WordCountMapper.class);
            Job job =
                    MapReduceHelper.createJob(new Configuration(), "MapReduceHelperTest", MapReduceHelper.class, Arrays
                                    .asList(inputSetting1, inputSetting2),
                            new OutputKeyValuePair(Text.class, IntWritable.class), new OutputSetting(
                                    WordCountReducer.class, SequenceFileOutputFormat.class, new Path(
                                    "/dir/stats/output2/")), new OutputKeyValuePair(Text.class,
                                    IntWritable.class));
            job.waitForCompletion(true);
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    public void createJobTest2() {
        try {
            InputSetting inputSetting1 =
                    new InputSetting(new Path("/dir/stats/20150527"), SequenceFileInputFormat.class,
                            WordCountMapper.class);
            InputSetting inputSetting2 =
                    new InputSetting(new Path("/dir/stats/20150528"), SequenceFileInputFormat.class,
                            WordCountMapper.class);
            Job job =
                    MapReduceHelper.createJob(new Configuration(), "MapReduceHelperTest", MapReduceHelper.class, Arrays
                            .asList(inputSetting1, inputSetting2),
                            new OutputKeyValuePair(Text.class, IntWritable.class), new OutputSetting(
                                    WordCountReducer.class, TextOutputFormat.class, new Path("/dir/stats/output3/")),
                            new OutputKeyValuePair(Text.class, IntWritable.class));
            job.waitForCompletion(true);
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    public void createJobWithSnappyCodecCompressTest() {
        try {
            InputSetting inputSetting1 =
                    new InputSetting(new Path("/dir/stats/20150527"), SequenceFileInputFormat.class,
                            WordCountMapper.class);
            InputSetting inputSetting2 =
                    new InputSetting(new Path("/dir/stats/20150528"), SequenceFileInputFormat.class,
                            WordCountMapper.class);
            Job job =
                    MapReduceHelper.createJobWithSnappyCodecCompress(new Configuration(), "MapReduceHelperTest",
                            MapReduceHelper.class, Arrays.asList(inputSetting1, inputSetting2), new OutputKeyValuePair(
                                    Text.class, IntWritable.class), new OutputSetting(WordCountReducer.class,
                                    SequenceFileOutputFormat.class, new Path("/dir/stats/output1/")),
                            new OutputKeyValuePair(Text.class, IntWritable.class));
            job.waitForCompletion(true);
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    class WordCountMapper extends Mapper<Text, Text, Text, IntWritable> {
        @Override
        protected void map(Text key, Text value, Context context) throws IOException, InterruptedException {
            IntWritable newValue = new IntWritable();
            newValue.set(1);
            context.write(key, newValue);
        }
    }

    class WordCountReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
        @Override
        protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException,
                InterruptedException {
            int sum = 0;
            for (IntWritable value : values) {
                sum += value.get();
            }
            IntWritable newValue = new IntWritable();
            newValue.set(sum);
            context.write(key, newValue);
        }
    }
}
