package elte;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.server.jobtracker.JTConfig;

public class MaxTempDriver {

	public static void main(String[] args) throws Exception {
		String uname = "hduser";
		System.setProperty("hadoop.tmp.dir", "c:\\BigData\\tmp-"+uname+"\\hadoop");
		System.setProperty("hadoop.home.dir", "c:\\BigData\\hadoop-3.3.6");
		
		Configuration conf = new Configuration();
		conf.set(JTConfig.JT_STAGING_AREA_ROOT, "c:\\BigData\\tmp-"+uname+"\\staging");
		conf.set(JTConfig.JT_SYSTEM_DIR, "c:\\BigData\\tmp-"+uname+"\\system");

		System.setProperty("HADOOP_USER_NAME", "hduser");
		
		Job job = Job.getInstance(conf, "JobName");
		job.setJarByClass(elte.MaxTempDriver.class);
		job.setMapperClass(elte.MaxTempMapper.class);

		job.setReducerClass(elte.MaxTempReducer.class);

		// TODO: specify output types
		job.setOutputKeyClass(Text.class);
		// Beállitjuk a saját tipusunkat
		job.setOutputValueClass(TempWritable.class);

		// TODO: specify input and output DIRECTORIES (not files)
		// Ez lesz most az input fájl
		FileInputFormat.setInputPaths(job, new Path("tempBudapestMeteoBlue.csv"));
		FileOutputFormat.setOutputPath(job, new Path("out"));

		if (!job.waitForCompletion(true))
			return;
	}

}
