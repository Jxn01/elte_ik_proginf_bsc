package elte;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

// A kimeneti kulcs az b�rmi lehet (nem sz�m�t), az �rt�k egy FloatWritable lesz
public class PiEstimationReducer extends Reducer<Text, IntWritable, Text, FloatWritable> {
	
	// Ebbe sz�moljuk �ssze h�ny k�r�n bel�li pont van
	private int inPoints = 0;
	// Ebbe sz�moljuk �ssze a gener�lt pontok sz�m�t (az �sszes map taskb�l)
	private int dbSum = 0;

	public void reduce(Text _key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
		
		// K�t f�le kulcs j�het:
		// Ha "in" a kulcs, akkor egy k�r�n ble�l l�v� pont
		if(_key.toString().compareTo("in") == 0) {
			for (IntWritable val : values) {
				inPoints += val.get();
			}
		} else {
			// Egy�bk�nt (ha "db" a kulcs, akkor �sszegezz�k a db v�ltoz�ba
			for (IntWritable val : values) {
				dbSum += val.get();
			}
		}
		
	}
	
	// A cleanup f�ggv�ny akkor fut le, ha a reduce task befejez�d�tt
	// Minden reduce task eset�ben egyszer fut le
	// Ha t�bb kulcsunk van (�s ez�rt t�bb reduce f�ggv�ny lesz), akkor is csak egyszer fut le
	// A megold�s csak akkor m�k�dik ha egy reduce task van, ezt a driverben be�llitjuk k�s�bb
	public void cleanup(Context context) throws IOException, InterruptedException {
		//Kisz�mitjuk a PI �rt�k�t
		float pi = 4 * ((float)inPoints / (float)dbSum);
		// �s tov�bb adjuk, mint kulcs-�rt�k p�r
		context.write(new Text("Pi value: "), new FloatWritable(pi));
	}

}
