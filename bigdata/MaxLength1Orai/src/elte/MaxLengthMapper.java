package elte;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

// A kimeneti kulcs egy bet� lesz, Text t�pussal
// A kimeneti �rt�k egy eg�sz sz�m lesz, IntWritable t�pussal
public class MaxLengthMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

	public void map(LongWritable ikey, Text ivalue, Context context) throws IOException, InterruptedException {
		// Felbontjuk a bemeneti sort sz�k�z�nk�nt
		String[] words = ivalue.toString().split(" ");
		
		// V�gigiter�lunk a szavakon
		for(String s: words) {
			
			// A sz� els� bejt�je lesz a kulcs (nagybet�ss� konvert�lva, hogy ne legyen elt�r�s)
			// A sz� hossza lesz a kulcs
			context.write(new Text(s.substring(0,1).toUpperCase()), new IntWritable(s.length()));
		}
	}

}
