package MapReduce;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class HBMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

	public void map(LongWritable ikey, Text ivalue, Context context) throws IOException, InterruptedException {
		Configuration conf = context.getConfiguration();
		int k = conf.getInt("k", 3);
		
		for(int i = 0; i+k-1 < ivalue.getLength(); i++) {
			Text kmer = new Text(ivalue.toString().substring(i, i+k));
			context.write(kmer, new IntWritable(1));
		}
	}
}
