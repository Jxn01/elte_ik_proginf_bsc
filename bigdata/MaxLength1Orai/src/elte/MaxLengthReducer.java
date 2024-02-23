package elte;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

//A kimeneti kulcs egy betû lesz ugyanúgy, mint a Mapper esetében, Text típussal
//A kimeneti érték egy egész szám lesz, a leghosszabb szó hossza, IntWritable típussal
public class MaxLengthReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

	public void reduce(Text _key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
		// Maximum keresést kell implementálnunk
		// A 0 jó kezdeti értéknek, mivel ettõl rövidebb nem lehet egy szó
		int max_len = 0;
		
		// Végigiterálunk az azonos kulcshoz tartozó értékeken
		for (IntWritable val : values) {
			
			// A max_len változót frissítjük ha találunk nagyobb értéket
			max_len = Math.max(val.get(), max_len);
		}
		
		// A kulcs ugyanúgy a kezdõbetû marad, az érték pedig a leghosszabb szó hossza (max_len)
		context.write(_key, new IntWritable(max_len));
	}

}
