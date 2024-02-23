import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class Gyak2GrepDriver {

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
		job.setJarByClass(Gyak2GrepDriver.class);
		job.setMapperClass(Gyak2GrepMapper.class);
		//job.setReducerClass(Gyak2GrepReducer.class);
		job.setNumReduceTasks(0);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);

		FileInputFormat.setInputPaths(job, new Path("input"));
		FileOutputFormat.setOutputPath(job, outPath);

		if (!job.waitForCompletion(true))
			return;
	}

}
