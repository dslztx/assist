package me.dslztx.assist.util;

import java.io.IOException;
import java.util.Random;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.util.ReflectionUtils;

/**
 * @date 2015年5月21日
 * @author dslztx
 * @description <br>
 *              不使用同一个conf配置实例的原因有两个：<br>
 *              1)重新建立一个新的conf配置实例，能够正常工作；<br>
 *              2)任务在使用conf配置实例的时候，会对其进行一些隐式的设置参数操作，<br>
 *              比如getSnappyCodecSeqFileWriter在使用conf的时候，会设置其压缩参数，这会造成代码间的耦合
 */
public class    HDFSHelper {
    /**
     * 判断在HDFS中，一个指定的路径是否存在
     * 
     * @param uri
     * @return
     * @throws IOException
     */
    public static boolean fileExists(String uri) throws IOException {
        // 初始化配置对象和文件系统对象
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(conf);

        // 文件路径
        Path path = new Path(uri);

        return fs.exists(path);
    }

    /**
     * 得到对HDFS中一个指定文件的写对象：写出文件类型是SequenceFile,使用SnapppyCodec进行压缩
     * 
     * @param uri
     * @return
     * @description 注意关闭写对象:writer.close()
     */
    public static SequenceFile.Writer getSnappyCodecSeqFileWriter(String uri, Class<? extends Writable> outputKeyClz,
                                                                  Class<? extends Writable> outputValueClz) {
        try {
            // 生成一个配置文件实例
            Configuration conf = new Configuration();

            // 操作文件路径
            Path path = new Path(uri);

            // 显式加载类，用于反射生成一个压缩类实例
            Class<?> codecClass = Class.forName("org.apache.hadoop.io.compress.SnappyCodec");
            CompressionCodec codec = (CompressionCodec) ReflectionUtils.newInstance(codecClass, conf);

            // 生成一个Writer实例，需要"配置实例，文件路径，Key类型，Value类型，{压缩类型，压缩实例}"
            return SequenceFile.createWriter(conf, SequenceFile.Writer.file(path),
                    SequenceFile.Writer.keyClass(outputKeyClz), SequenceFile.Writer.valueClass(outputValueClz),
                    SequenceFile.Writer.compression(SequenceFile.CompressionType.BLOCK, codec));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 生成一个读取文件的读对象：读取的文件类型是SequenceFile
     * 
     * @param uri
     * @return
     * @description 注意关闭读对象:reader.close()
     * @description <br>
     *              想要读取压缩过的SequenceFile文件，在建立Reader实例的时候，不需要特殊指定压缩参数，<br>
     *              因为Reader实例会根据文件的Metadata识别出压缩格式，并进行解压缩读取
     */
    public static SequenceFile.Reader getSeqFileReader(String uri) {
        try {
            // 配置实例
            Configuration conf = new Configuration();

            // 文件路径
            Path path = new Path(uri);

            // 生成一个读对象
            return new SequenceFile.Reader(conf, SequenceFile.Reader.file(path));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
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
            throw new RuntimeException(e);
        }
    }
}
