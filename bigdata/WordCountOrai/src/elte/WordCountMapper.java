package elte;

import java.io.IOException;

// IntWritable oszt�lyt import�lni kell, hogy haszn�lhassuk
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

// A Mapper oszt�ly harmadik �s negyedik param�tere adja meg a kimeneti kulcs �s �rt�k t�pus�t
// Mivel mi (sz�, 1) p�rokat k�sz�t�nk, ez�rt a kimenetti kulcs t�pus maradhat Text, 
// az �rt�k t�pust viszont �ll�tsuk be IntWritable-re

public class WordCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
	
	// A kulcs �s �rt�k t�pusok csak Hadoop-osak lehetnek, ez�rt String helyett Text t�pust haszn�lnunk a kulcshoz:
	private Text word = new Text("");
	
	// Az �rt�kn�l ugyanez a helyzet: Int helyett IntWritablet haszn�lunk:
	private IntWritable one = new IntWritable(1);

	// A map f�ggv�ny soronk�nt feldolgozza a bementi f�jlt
	// Az aktu�lis r�szt az "ivalue" v�ltoz� tartalmazza
	public void map(LongWritable ikey, Text ivalue, Context context) throws IOException, InterruptedException {
		
		// A Text t�pus� sort String-� konvert�ljuk �s sz�k�z�nk�nt felbontjuk szavakra:
		String[] words = ivalue.toString().split(" ");
		
		// V�gigiter�lunk az egyes szavakon �s (sz�, 1) kulcs-�rt�k p�rokat k�sz�t�nk:
		for(String s: words) {
			
			// A "word" v�ltoz�nak �rt�k�l adjuk az aktu�lis sz�t:
			word.set(s);
			
			// A context.write() haszn�lat�val tudjuk elk�sz�teni a kulcs-�rt�k p�runkat
			// Az els� param�ter a kulcs, a m�sodik az �rt�k:
			context.write(word, one);
		}
	}

}
