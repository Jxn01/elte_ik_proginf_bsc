package elte;

import java.io.IOException;
import java.util.Random;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

// K�t kimeneti kulcs lesz: in - ha a k�r�n bel�l van egy pont, vagy db - a gener�lt pontok sz�ma
public class PiEstimationMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

	public void map(LongWritable ikey, Text ivalue, Context context) throws IOException, InterruptedException {
		
		// Lek�rdezz�k a conf-b�l a point_num-ot (a driverbe raktuk bele)
		Configuration conf = context.getConfiguration();
		
		// Ennyi pontot fogunk gener�lni egy map taskban
		int point_num = conf.getInt("num", 0);
		//int point_num = 100000;
		for(int i = 0; i < point_num; i++) {
			
			// Mindig gener�lunk egy x �s y koordin�t�t [0,1] intervallumon
			Random rand = new Random();
			float x = rand.nextFloat();
			float y = rand.nextFloat();
			
			// Megn�zz�k a pont t�vols�g�t az orig�t�l
			float dist = (float)Math.sqrt(x * x + y * y);
			
			// Ha a t�vosl�g <= 1, akkor a k�rben van a pont
			if(dist <= 1) {
				context.write(new Text("in"), new IntWritable(1));
			}
		}
		
		// A "db" kulccsal tov�bbadjuk a reduvernek hogy h�ny pontot gener�ltunk az adott map taskban
		context.write(new Text("db"), new IntWritable(point_num));
	}

}
