import java.io.IOException;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class Gyak2WordMeanMapper extends Mapper<LongWritable, Text, Text, FloatWritable> {

	public void map(LongWritable ikey, Text ivalue, Context context) throws IOException, InterruptedException {

	}

}
