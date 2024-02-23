package elte;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

// A Reducer osztály elsõ és második paramétere adja meg a bemeneti kulcs és érték típusát (amit a Mapper osztályunk készít el)
// A harmadik és a negyedik paraméter adja meg a kimeneti kulcs és érték típusát (ezt írjuk majd ki fájlba)
public class WordCountReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

	// A reduce() függvény elsõ paramétere (_key) a kulcs, amit a reducer megkapott
	// A függvény második paramétere (values) pedig egy kollekció, ami a kulcshoz tartozó értékeket tartalmazza
	// Ha IntWritable értékeket kaptunk a Mappertõl, akkor az Iterable IntWritable  típusú értékeket tartalmaz
	public void reduce(Text _key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
		
		// Egy összegzést kell végeznünk a reduce()-ban, a szavak elõfordulását a "sum" változóban számoljuk meg
		int sum = 0;
		
		// A ciklus végigiterál az összes értéken:
		for (IntWritable val : values) {
			
			// A "sum"-hoz hozzáadjuk az aktuális értéket
			sum += val.get();
		}
		
		// A ciklus után egy szó összes elõfordulását megszámoltuk
		// Elkészítjuk a kimeneti kulcs-érték párunkat
		// A kulcs az aktuális szó, az érték a szó elõfordulásainak a száma
		// Mivel Int-et nem használhatunk, ezért IntWritable típusba "csomagoljuk" a számot
		context.write(_key, new IntWritable(sum));
	}

}
