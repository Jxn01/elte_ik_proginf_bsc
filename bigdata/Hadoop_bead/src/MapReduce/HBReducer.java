package MapReduce;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class HBReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

	public void reduce(Text _key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
		int count = 0;
		
		for (IntWritable val : values) {
			count += val.get();
		}
		
		boolean hasT = _key.toString().contains("T");
		boolean over100 = count > 100;
		
		if(hasT && over100) context.write(_key, new IntWritable(count));
	}
}