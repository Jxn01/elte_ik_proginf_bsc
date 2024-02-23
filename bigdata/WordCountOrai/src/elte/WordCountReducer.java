package elte;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

// A Reducer oszt�ly els� �s m�sodik param�tere adja meg a bemeneti kulcs �s �rt�k t�pus�t (amit a Mapper oszt�lyunk k�sz�t el)
// A harmadik �s a negyedik param�ter adja meg a kimeneti kulcs �s �rt�k t�pus�t (ezt �rjuk majd ki f�jlba)
public class WordCountReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

	// A reduce() f�ggv�ny els� param�tere (_key) a kulcs, amit a reducer megkapott
	// A f�ggv�ny m�sodik param�tere (values) pedig egy kollekci�, ami a kulcshoz tartoz� �rt�keket tartalmazza
	// Ha IntWritable �rt�keket kaptunk a Mappert�l, akkor az Iterable IntWritable  t�pus� �rt�keket tartalmaz
	public void reduce(Text _key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
		
		// Egy �sszegz�st kell v�gezn�nk a reduce()-ban, a szavak el�fordul�s�t a "sum" v�ltoz�ban sz�moljuk meg
		int sum = 0;
		
		// A ciklus v�gigiter�l az �sszes �rt�ken:
		for (IntWritable val : values) {
			
			// A "sum"-hoz hozz�adjuk az aktu�lis �rt�ket
			sum += val.get();
		}
		
		// A ciklus ut�n egy sz� �sszes el�fordul�s�t megsz�moltuk
		// Elk�sz�tjuk a kimeneti kulcs-�rt�k p�runkat
		// A kulcs az aktu�lis sz�, az �rt�k a sz� el�fordul�sainak a sz�ma
		// Mivel Int-et nem haszn�lhatunk, ez�rt IntWritable t�pusba "csomagoljuk" a sz�mot
		context.write(_key, new IntWritable(sum));
	}

}
