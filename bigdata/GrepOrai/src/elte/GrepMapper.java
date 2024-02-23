package elte;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class GrepMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
	
	// Ez lesz a keresendõ kifejezésünk
	private String keres = "kis";

	public void map(LongWritable ikey, Text ivalue, Context context) throws IOException, InterruptedException {
		
		// Megnézzük, hogy a keresendõ kifejezés benne van-e a sorban
		// Ha igen, akkor a find() visszaadja a keresendõ kifejezést kezdeti pozícióját
		int place = ivalue.find(keres);
		
		// Ha nem találja, akkor -1-et ad vissza
		// Ha -1-tõl elétrõ az érték, akkor készítsünk kulcs-érték párt
		// A kulcs maga a sor lesz, az érték pedig a kezdeti pozíció
		if(place != -1) {
			context.write(ivalue, new IntWritable(place));
		}
	}

}
