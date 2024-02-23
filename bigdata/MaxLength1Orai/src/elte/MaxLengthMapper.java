package elte;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

// A kimeneti kulcs egy betû lesz, Text típussal
// A kimeneti érték egy egész szám lesz, IntWritable típussal
public class MaxLengthMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

	public void map(LongWritable ikey, Text ivalue, Context context) throws IOException, InterruptedException {
		// Felbontjuk a bemeneti sort szóközönként
		String[] words = ivalue.toString().split(" ");
		
		// Végigiterálunk a szavakon
		for(String s: words) {
			
			// A szó elsõ bejtûje lesz a kulcs (nagybetûssé konvertálva, hogy ne legyen eltérés)
			// A szó hossza lesz a kulcs
			context.write(new Text(s.substring(0,1).toUpperCase()), new IntWritable(s.length()));
		}
	}

}
