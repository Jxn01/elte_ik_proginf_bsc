package gyak2;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class gyak2driver {

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		Path outPath = new Path("output");
		if(true) { //to delete the output directory before every run
			FileSystem fs;
			fs = FileSystem.get(conf);
			if(fs.exists(outPath)) {
				fs.delete(outPath, true);
			}
		}
		
		Job job = Job.getInstance(conf, "JobName");
		job.setJarByClass(gyak2.gyak2driver.class);
		// specify a mapper
		job.setMapperClass(gyak2mapper.class);
		// specify a combiner class
		job.setCombinerClass(gyak2reducer.class);
		// specify a reducer
		job.setReducerClass(gyak2reducer.class);

		// specify output types
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);

		// specify input and output DIRECTORIES (not files)
		FileInputFormat.setInputPaths(job, new Path("input"));
		FileOutputFormat.setOutputPath(job, outPath);

		if (!job.waitForCompletion(true))
			return;
	}

}
