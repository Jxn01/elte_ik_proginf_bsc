package elte;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.server.jobtracker.JTConfig;

public class PiEstimationDriver {

	public static void main(String[] args) throws Exception {
		String uname = "hduser";
		System.setProperty("hadoop.tmp.dir", "c:\\BigData\\tmp-"+uname+"\\hadoop");
		System.setProperty("hadoop.home.dir", "c:\\BigData\\hadoop-3.3.6");

		Configuration conf = new Configuration();
		
		// Beállitjuk, hogy egy map task hány pontot generálján
		// 10*100 000 generált ponttal már az elsõ két számjegy pontos lesz
		conf.setInt("num", 100000);
		
		// Ezt megadhatnánk parancsori argumentumként is
		// Akkor lenne ez hasznos számunkra, ha valódi klaszteren dolgoznánk
		//conf.setInt("num", Integer.parseInt(args[0]));
		
		conf.set(JTConfig.JT_STAGING_AREA_ROOT, "c:\\BigData\\tmp-"+uname+"\\staging");
		conf.set(JTConfig.JT_SYSTEM_DIR, "c:\\BigData\\tmp-"+uname+"\\system");

		System.setProperty("HADOOP_USER_NAME", "hduser");
		Job job = Job.getInstance(conf, "JobName");
		job.setJarByClass(elte.PiEstimationDriver.class);
		job.setMapperClass(elte.PiEstimationMapper.class);

		job.setReducerClass(elte.PiEstimationReducer.class);
		
		// FONTOS!!! Csak akkor mûködik a megoldásunk, ha pontosan EGY reduce taskunk van
		// Ezért beállitjuk 1-re a reduce taskok számát
		job.setNumReduceTasks(1);

		// TODO: specify output types
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(FloatWritable.class);

		// TODO: specify input and output DIRECTORIES (not files)
		FileInputFormat.setInputPaths(job, new Path("input.txt"));
		FileOutputFormat.setOutputPath(job, new Path("out"));

		if (!job.waitForCompletion(true))
			return;
	}

}
