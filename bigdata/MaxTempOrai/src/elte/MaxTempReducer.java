package elte;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;

import elte.TempWritable;

public class MaxTempReducer extends Reducer<Text, TempWritable, Text, TempWritable> {

// TempWritable �rt�keken iter�lunk v�gig
public void reduce(Text _key, Iterable<TempWritable> values, Context context) throws IOException, InterruptedException {
		
		// A reducerben egy maximum keres�st hajtunk v�gre TEmpWritable �rt�keken
		// A h�m�rs�kleteket szeretn�nk �sszehasonlitani
		// A t_max t�rolja a max h�m�rs�kletet Double-ben
		// A max t�rolja a maxim�lis h�m�rs�kletet �s �r�t TempWritable-ben
		double t_max = Double.MIN_VALUE;
		TempWritable max = new TempWritable();
		
		// V�gigiter�lunk az egy naphoz tartoz� �rt�keken
		for (TempWritable val : values) {
			
			// Ha tal�ltunk nagyobb h�m�rs�kletet be�llitjuk
			if(val.getTemp().get() > t_max) {
				
				// A max TempWritable-ben is
				max = new TempWritable(val.getTime().toString(), val.getTemp().get());
				
				// ...�s a t_max Double-ben is
				t_max = val.getTemp().get();
			}
		}
		
		// V�g�l a kulcs-�rt�k p�rba a nap �s a TempWritable ker�l
		// Mivel elk�szitett�k a toString() met�dust, ez�rt sz�pen ki tudja majd irni f�ljba a Hadoop
		context.write(_key, max);
	}

}
