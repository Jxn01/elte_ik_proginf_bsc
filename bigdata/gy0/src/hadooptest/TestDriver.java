package hadooptest;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class TestDriver {

	public static void main(String[] args) throws Exception {
		System.setProperty("hadoop.tmp.dir", "/home/jxn/.hadoop-xx");
		System.setProperty("hadoop.home.dir", "/home/jxn/.hadoop-3.3.6");

		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf, "JobName");		
		
		job.setJarByClass(TestDriver.class);
		job.setMapperClass(TestMapper.class);

		job.setReducerClass(TestReducer.class);

		// TODO: specify output types
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);

		// TODO: specify input and output DIRECTORIES (not files)
		FileInputFormat.setInputPaths(job, new Path("input.txt"));
		FileOutputFormat.setOutputPath(job, new Path("testeredmeny"));

		FileSystem fs = FileSystem.get(conf);
		if (fs.exists(new Path("testeredmeny"))) {
			fs.delete(new Path("testeredmeny"),true);
		}
		
		if (!job.waitForCompletion(true))
			return;
	}

}
