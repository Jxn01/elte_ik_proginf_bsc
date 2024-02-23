package elte;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;

import elte.TempWritable;

public class MaxTempReducer extends Reducer<Text, TempWritable, Text, TempWritable> {

// TempWritable értékeken iterálunk végig
public void reduce(Text _key, Iterable<TempWritable> values, Context context) throws IOException, InterruptedException {
		
		// A reducerben egy maximum keresést hajtunk végre TEmpWritable értékeken
		// A hõmérsékleteket szeretnénk összehasonlitani
		// A t_max tárolja a max hõmérsékletet Double-ben
		// A max tárolja a maximális hõmérsékletet és órát TempWritable-ben
		double t_max = Double.MIN_VALUE;
		TempWritable max = new TempWritable();
		
		// Végigiterálunk az egy naphoz tartozó értékeken
		for (TempWritable val : values) {
			
			// Ha találtunk nagyobb hõmérsékletet beállitjuk
			if(val.getTemp().get() > t_max) {
				
				// A max TempWritable-ben is
				max = new TempWritable(val.getTime().toString(), val.getTemp().get());
				
				// ...és a t_max Double-ben is
				t_max = val.getTemp().get();
			}
		}
		
		// Végül a kulcs-érték párba a nap és a TempWritable kerül
		// Mivel elkészitettük a toString() metódust, ezért szépen ki tudja majd irni fáljba a Hadoop
		context.write(_key, max);
	}

}
