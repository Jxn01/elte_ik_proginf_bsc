package elte;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Mapper.Context;

import elte.TempWritable;

// Az általunk létrehozott TempWritable lesz az érétk tipus
public class MaxTempMapper extends Mapper<LongWritable, Text, Text, TempWritable> {

	private TempWritable temp = new TempWritable();
	private Text date = new Text("");

	public void map(LongWritable ikey, Text ivalue, Context context) throws IOException, InterruptedException {
		
		// Felbontjuk a sorokat vesszõnként (két oszlopot fogunk kapni)
		String[] cols = ivalue.toString().split(",");
		
		// A fájl elején header sorok vannak, azokat nem akarjuk feldolgozni
		// Ha egy sor "2020"-al kezdõdik, akkor jó
		if(cols[0].startsWith("2020")) {
			
			// A napot és az órát egy T betû választja el, bontsuk fel ezt is
			String[] timestamp = cols[0].split("T");
			
			// A dátum lesz a kulcs (Text)
			date.set(timestamp[0]);
			
			// A TempWritable lesz az érték
			// Az elsõ eleme az óra (Text), a második pedig a hõmérséklet, mint DoubleWritable
			temp.set(timestamp[1], Double.parseDouble(cols[1]));
			context.write(date, temp);
		}
	}

}
