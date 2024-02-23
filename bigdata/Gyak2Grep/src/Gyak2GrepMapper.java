import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class Gyak2GrepMapper extends Mapper<LongWritable, Text, Text, Text> {

	private Text word = new Text();
	
	public void map(LongWritable ikey, Text ivalue, Context context) throws IOException, InterruptedException {
		if(ivalue.toString().contains("sütsz")) {
			word.set(ivalue.toString());
			ivalue.set("");
			context.write(word, ivalue);
		}
		
	}

}
