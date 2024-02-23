package hadooptest;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class TestMapper extends Mapper<LongWritable, Text, Text, Text> {
	private Text empty = new Text("");
	
	public void map(LongWritable ikey, Text ivalue, Context context) throws IOException, InterruptedException {
		context.write(ivalue, empty);
	}
}
