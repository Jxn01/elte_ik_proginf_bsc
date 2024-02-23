package elte;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Mapper.Context;

import elte.TempWritable;

// Az �ltalunk l�trehozott TempWritable lesz az �r�tk tipus
public class MaxTempMapper extends Mapper<LongWritable, Text, Text, TempWritable> {

	private TempWritable temp = new TempWritable();
	private Text date = new Text("");

	public void map(LongWritable ikey, Text ivalue, Context context) throws IOException, InterruptedException {
		
		// Felbontjuk a sorokat vessz�nk�nt (k�t oszlopot fogunk kapni)
		String[] cols = ivalue.toString().split(",");
		
		// A f�jl elej�n header sorok vannak, azokat nem akarjuk feldolgozni
		// Ha egy sor "2020"-al kezd�dik, akkor j�
		if(cols[0].startsWith("2020")) {
			
			// A napot �s az �r�t egy T bet� v�lasztja el, bontsuk fel ezt is
			String[] timestamp = cols[0].split("T");
			
			// A d�tum lesz a kulcs (Text)
			date.set(timestamp[0]);
			
			// A TempWritable lesz az �rt�k
			// Az els� eleme az �ra (Text), a m�sodik pedig a h�m�rs�klet, mint DoubleWritable
			temp.set(timestamp[1], Double.parseDouble(cols[1]));
			context.write(date, temp);
		}
	}

}
