package elte;

import java.io.IOException;
import java.util.Random;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

// Két kimeneti kulcs lesz: in - ha a körön belül van egy pont, vagy db - a generált pontok száma
public class PiEstimationMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

	public void map(LongWritable ikey, Text ivalue, Context context) throws IOException, InterruptedException {
		
		// Lekérdezzük a conf-ból a point_num-ot (a driverbe raktuk bele)
		Configuration conf = context.getConfiguration();
		
		// Ennyi pontot fogunk generálni egy map taskban
		int point_num = conf.getInt("num", 0);
		//int point_num = 100000;
		for(int i = 0; i < point_num; i++) {
			
			// Mindig generálunk egy x és y koordinátát [0,1] intervallumon
			Random rand = new Random();
			float x = rand.nextFloat();
			float y = rand.nextFloat();
			
			// Megnézzük a pont távolságát az origótól
			float dist = (float)Math.sqrt(x * x + y * y);
			
			// Ha a távoslág <= 1, akkor a körben van a pont
			if(dist <= 1) {
				context.write(new Text("in"), new IntWritable(1));
			}
		}
		
		// A "db" kulccsal továbbadjuk a reduvernek hogy hány pontot generáltunk az adott map taskban
		context.write(new Text("db"), new IntWritable(point_num));
	}

}
