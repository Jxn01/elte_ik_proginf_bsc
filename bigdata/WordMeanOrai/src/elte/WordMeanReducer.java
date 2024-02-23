package elte;

import java.io.IOException;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class WordMeanReducer extends Reducer<NullWritable, IntWritable, NullWritable, FloatWritable> {

	public void reduce(NullWritable _key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
		// process values
				int length_sum = 0;
				int db = 0;
				for (IntWritable val : values) {
					db = db + 1;
					length_sum = length_sum + val.get();
				}
				//int count = (int)context.getCounter(TaskCounter.REDUCE_INPUT_RECORDS).getValue();
				float avg = (float)length_sum/(float)db;
				context.write(NullWritable.get(), new FloatWritable(avg));
	}

}
