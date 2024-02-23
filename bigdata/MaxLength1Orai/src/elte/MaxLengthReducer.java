package elte;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

//A kimeneti kulcs egy bet� lesz ugyan�gy, mint a Mapper eset�ben, Text t�pussal
//A kimeneti �rt�k egy eg�sz sz�m lesz, a leghosszabb sz� hossza, IntWritable t�pussal
public class MaxLengthReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

	public void reduce(Text _key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
		// Maximum keres�st kell implement�lnunk
		// A 0 j� kezdeti �rt�knek, mivel ett�l r�videbb nem lehet egy sz�
		int max_len = 0;
		
		// V�gigiter�lunk az azonos kulcshoz tartoz� �rt�keken
		for (IntWritable val : values) {
			
			// A max_len v�ltoz�t friss�tj�k ha tal�lunk nagyobb �rt�ket
			max_len = Math.max(val.get(), max_len);
		}
		
		// A kulcs ugyan�gy a kezd�bet� marad, az �rt�k pedig a leghosszabb sz� hossza (max_len)
		context.write(_key, new IntWritable(max_len));
	}

}
