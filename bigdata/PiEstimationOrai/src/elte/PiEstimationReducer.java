package elte;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

// A kimeneti kulcs az bármi lehet (nem számít), az érték egy FloatWritable lesz
public class PiEstimationReducer extends Reducer<Text, IntWritable, Text, FloatWritable> {
	
	// Ebbe számoljuk össze hány körön belüli pont van
	private int inPoints = 0;
	// Ebbe számoljuk össze a generált pontok számát (az összes map taskból)
	private int dbSum = 0;

	public void reduce(Text _key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
		
		// Két féle kulcs jöhet:
		// Ha "in" a kulcs, akkor egy körön bleül lévõ pont
		if(_key.toString().compareTo("in") == 0) {
			for (IntWritable val : values) {
				inPoints += val.get();
			}
		} else {
			// Egyébként (ha "db" a kulcs, akkor összegezzük a db változóba
			for (IntWritable val : values) {
				dbSum += val.get();
			}
		}
		
	}
	
	// A cleanup függvény akkor fut le, ha a reduce task befejezõdött
	// Minden reduce task esetében egyszer fut le
	// Ha több kulcsunk van (és ezért több reduce függvény lesz), akkor is csak egyszer fut le
	// A megoldás csak akkor mûködik ha egy reduce task van, ezt a driverben beállitjuk késõbb
	public void cleanup(Context context) throws IOException, InterruptedException {
		//Kiszámitjuk a PI értékét
		float pi = 4 * ((float)inPoints / (float)dbSum);
		// És tovább adjuk, mint kulcs-érték pár
		context.write(new Text("Pi value: "), new FloatWritable(pi));
	}

}
