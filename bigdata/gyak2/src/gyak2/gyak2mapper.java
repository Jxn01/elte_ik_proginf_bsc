package gyak2;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class gyak2mapper extends Mapper<LongWritable, Text, Text, IntWritable> {

	private Text word = new Text();
	private IntWritable val = new IntWritable(1);
		
	public void map(LongWritable ikey, Text ivalue, Context context) throws IOException, InterruptedException {
		String[] words = ivalue.toString().split(" ");
		for(String s : words) {
			word.set(s.replaceAll("[^A-Za-z0-9éáűúőóüöí]", "").toLowerCase());
			if(word.toString().length() > 2) {
				context.write(word, val);
			}
		}
	}

}
