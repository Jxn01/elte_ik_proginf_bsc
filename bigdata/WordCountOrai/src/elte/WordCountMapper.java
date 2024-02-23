package elte;

import java.io.IOException;

// IntWritable osztályt importálni kell, hogy használhassuk
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

// A Mapper osztály harmadik és negyedik paramétere adja meg a kimeneti kulcs és érték típusát
// Mivel mi (szó, 1) párokat készítünk, ezért a kimenetti kulcs típus maradhat Text, 
// az érték típust viszont állítsuk be IntWritable-re

public class WordCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
	
	// A kulcs és érték típusok csak Hadoop-osak lehetnek, ezért String helyett Text típust használnunk a kulcshoz:
	private Text word = new Text("");
	
	// Az értéknél ugyanez a helyzet: Int helyett IntWritablet használunk:
	private IntWritable one = new IntWritable(1);

	// A map függvény soronként feldolgozza a bementi fájlt
	// Az aktuális részt az "ivalue" változó tartalmazza
	public void map(LongWritable ikey, Text ivalue, Context context) throws IOException, InterruptedException {
		
		// A Text típusú sort String-é konvertáljuk és szóközönként felbontjuk szavakra:
		String[] words = ivalue.toString().split(" ");
		
		// Végigiterálunk az egyes szavakon és (szó, 1) kulcs-érték párokat készítünk:
		for(String s: words) {
			
			// A "word" változónak értékül adjuk az aktuális szót:
			word.set(s);
			
			// A context.write() használatával tudjuk elkészíteni a kulcs-érték párunkat
			// Az elsõ paraméter a kulcs, a második az érték:
			context.write(word, one);
		}
	}

}
