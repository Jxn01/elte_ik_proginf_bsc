package elte;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class GrepMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
	
	// Ez lesz a keresend� kifejez�s�nk
	private String keres = "kis";

	public void map(LongWritable ikey, Text ivalue, Context context) throws IOException, InterruptedException {
		
		// Megn�zz�k, hogy a keresend� kifejez�s benne van-e a sorban
		// Ha igen, akkor a find() visszaadja a keresend� kifejez�st kezdeti poz�ci�j�t
		int place = ivalue.find(keres);
		
		// Ha nem tal�lja, akkor -1-et ad vissza
		// Ha -1-t�l el�tr� az �rt�k, akkor k�sz�ts�nk kulcs-�rt�k p�rt
		// A kulcs maga a sor lesz, az �rt�k pedig a kezdeti poz�ci�
		if(place != -1) {
			context.write(ivalue, new IntWritable(place));
		}
	}

}
