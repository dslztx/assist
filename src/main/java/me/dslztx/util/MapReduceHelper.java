package me.dslztx.util;

import java.io.IOException;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.SequenceFile.CompressionType;
import org.apache.hadoop.mapreduce.InputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.OutputFormat;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;

/**
 * @date 2015年5月27日
 * @author dslztx
 */
@SuppressWarnings("rawtypes")
public class MapReduceHelper {

    /**
     * 创建一个完整的MapReduce Job的实例
     * 
     * @param conf Job的配置实例
     * @param jobName Job的名字　
     * @param workingClz Job的工作类名，据此找到Jar包
     * @param inputSettings 输入配置，具体包括输入文件路径，文件格式，处理该文件的Mapper类
     * @param mapOutput Mapper类的输出Key/Value对类型设置
     * @param outputSetting 输出配置，具体包括输出的Reducer类，输出文件格式，输出文件目录
     * @param reduceOutput Reducer类的输出Key/Value对类型设置，也是整个MapReduce最终输出Key/Value对的类型设置
     * @return
     * @throws IOException
     */
    public static Job createJob(Configuration conf, String jobName, Class<?> workingClz,
                                List<InputSetting> inputSettings, OutputKeyValuePair mapOutput,
                                OutputSetting outputSetting, OutputKeyValuePair reduceOutput) throws IOException {
        // 根据Job配置和Job名字建立一个Job实例
        Job job = Job.getInstance(conf, jobName);

        // 设置Job的工作类，据此找到Jar包
        job.setJarByClass(workingClz);

        // 输入配置，具体包括输入文件路径，文件格式，处理该文件的Mapper类
        for (InputSetting inputSetting : inputSettings) {
            MultipleInputs.addInputPath(job, inputSetting.inputUri, inputSetting.inputFormatClz,
                    inputSetting.inputMapperClz);
        }

        // Mapper类的输出Key/Value对类型设置
        job.setMapOutputKeyClass(mapOutput.outputKeyClz);
        job.setMapOutputValueClass(mapOutput.outputValueClz);

        // 输出配置，具体包括输出的Reducer类，输出文件格式，输出文件目录
        job.setReducerClass(outputSetting.outputReducerClz);
        job.setOutputFormatClass(outputSetting.outputFormatClz);
        FileOutputFormat.setOutputPath(job, outputSetting.outputDir);

        // Reducer类的输出Key/Value对类型设置，也是整个MapReduce最终输出Key/Value对的类型设置
        job.setOutputKeyClass(reduceOutput.outputKeyClz);
        job.setOutputValueClass(reduceOutput.outputValueClz);

        // 返回创建好的完整的MapReduce Job实例
        return job;
    }

    /**
     * 创建一个完整的MapReduce Job的实例，并且设置输出使用SnappyCodec进行压缩，压缩类型为RECORD
     * 
     * @param conf Job的配置实例
     * @param jobName Job的名字　
     * @param workingClz Job的工作类名，据此找到Jar包
     * @param inputSettings 输入配置，具体包括输入文件路径，文件格式，处理该文件的Mapper类
     * @param mapOutput Mapper类的输出Key/Value对类型设置
     * @param outputSetting 输出配置，具体包括输出的Reducer类，输出文件格式，输出文件目录
     * @param reduceOutput Reducer类的输出Key/Value对类型设置，也是整个MapReduce最终输出Key/Value对的类型设置
     * @return
     * @throws IOException
     */
    public static Job createJobWithSnappyCodecCompress(Configuration conf, String jobName, Class<?> workingClz,
                                                       List<InputSetting> inputSettings, OutputKeyValuePair mapOutput,
                                                       OutputSetting outputSetting, OutputKeyValuePair reduceOutput)
            throws IOException {
        // 输出文件格式只有是SequenceFileOutputFormat或者是其子类，才允许使用压缩功能
        if (!SequenceFileOutputFormat.class.isAssignableFrom(outputSetting.outputFormatClz))
            throw new RuntimeException("指定的输出文件格式不是SequenceFileOutputFormat，不能使用压缩功能");

        // 调用重载方法，获得不对输出进行压缩的Job实例
        Job job = createJob(conf, jobName, workingClz, inputSettings, mapOutput, outputSetting, reduceOutput);

        // 设置允许压缩
        SequenceFileOutputFormat.setCompressOutput(job, true);

        // 设置采用SnappyCodec进行压缩
        SequenceFileOutputFormat.setOutputCompressorClass(job, org.apache.hadoop.io.compress.SnappyCodec.class);

        // 设置采用RECORD压缩类型
        SequenceFileOutputFormat.setOutputCompressionType(job, CompressionType.RECORD);

        // 返回Job实例
        return job;
    }

    /**
     * 设置输入文件路径（MapReduce处理输入目录对用户非常不友好，这里直接规定是文件路径），该文件对应的格式，处理该文件的Mapper类
     * 
     * @date 2015年5月27日
     * @author dslztx
     */
    public static class InputSetting {
        /**
         * 输入文件路径
         */
        Path inputUri;

        /**
         * 相应的文件格式
         */
        Class<? extends InputFormat> inputFormatClz;

        /**
         * 处理相应文件的Mapper类
         */
        Class<? extends Mapper> inputMapperClz;

        public InputSetting(Path inputUri, Class<? extends InputFormat> inputFormatClz,
                Class<? extends Mapper> inputMapperClz) {
            this.inputUri = inputUri;
            this.inputFormatClz = inputFormatClz;
            this.inputMapperClz = inputMapperClz;
        }
    }

    /**
     * 由哪个Reducer类输出，输出文件的格式，输出目录的设置（只能是一个目录，而不能是一个文件）
     * 
     * @date 2015年5月27日
     * @author dslztx
     */
    public static class OutputSetting {
        /**
         * 由哪个Reducer类输出
         */
        Class<? extends Reducer> outputReducerClz;

        /**
         * 输出文件的格式
         */
        Class<? extends OutputFormat> outputFormatClz;

        /**
         * 输出目录
         */
        Path outputDir;

        public OutputSetting(Class<? extends Reducer> outputReducerClz, Class<? extends OutputFormat> outputFormatClz,
                Path outputDir) {
            this.outputReducerClz = outputReducerClz;
            this.outputFormatClz = outputFormatClz;
            this.outputDir = outputDir;
        }
    }

    /**
     * 设定Mapper或者Reducer类的输出Key和Value对的类型
     * 
     * @date 2015年5月27日
     * @author dslztx
     */
    public static class OutputKeyValuePair {
        /**
         * 输出Key的类型
         */
        Class<? extends Writable> outputKeyClz;

        /**
         * 输出Value的类型
         */
        Class<? extends Writable> outputValueClz;

        public OutputKeyValuePair(Class<? extends Writable> outputKeyClz, Class<? extends Writable> outputValueClz) {
            this.outputKeyClz = outputKeyClz;
            this.outputValueClz = outputValueClz;
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {

    }
}
