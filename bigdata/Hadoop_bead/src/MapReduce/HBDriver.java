package MapReduce;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class HBDriver {

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		conf.setInt("k", 3); // k-mer
		
		Job job = Job.getInstance(conf, "Hadoop Beadandó");
		
		Path inputPath = new Path("input");
		Path outputPath = new Path("output");
		
		deleteOutputPath(conf, outputPath);
		
		FileInputFormat.setInputPaths(job, inputPath);
		FileOutputFormat.setOutputPath(job, outputPath);
		
		job.setJarByClass(MapReduce.HBDriver.class);
		job.setMapperClass(MapReduce.HBMapper.class);
		job.setReducerClass(MapReduce.HBReducer.class);

		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		if (!job.waitForCompletion(true))
			return;
	}
	
	private static boolean deleteOutputPath(Configuration c, Path p) throws IOException {
		FileSystem fs = FileSystem.get(c);
		return fs.delete(p, true);
	}

}
