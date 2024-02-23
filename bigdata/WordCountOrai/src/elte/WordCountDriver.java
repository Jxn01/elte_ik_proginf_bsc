package elte;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.server.jobtracker.JTConfig;

/*
 * Megjegyzések:
 * 
 * A programot Hadoop alkalmazásként kell futtatni (Run on Hadoop)
 * Ha rákérdez, hogy melyik fájlt futtassa, akkor a drivert kell megadni (WordCountDriver)
 * A log4j.properties fájlt a projekt "src" könyvtárába másoljuk be. Csak ezután fogunk látni értelmes log üzeneteket a konzolban.
 * Ha futtatás után nem láttjuk a kimeneti fájlt érdemes frissíteni a Project Explolerben a projektet (jobb gomb a projekt nevére, és Refresh)
 * Ha a kimeneti mappában láttjuk a _SUCCESS nevû fájlt sikerült a futtatás.
 * A part-r-00000 fájl tartalmazza a Reducer osztályunk kimenetét.
 */

public class WordCountDriver {

	public static void main(String[] args) throws Exception {
		
		// Az alábbái 3 sor megadja hová kerüljenek a temporális fájlok, hol van a hadoop, és beállít egy haddop felhasználónevet
		// Az egyetemi gépeken fontos használni, saját gépen ezek nélkül is fut a kód
		// Ez tetszõleges útvonalra beállítható:
		System.setProperty("hadoop.tmp.dir", "C:\\BigData\\tmp\\hadoop");
		// Itt a hadoop könytárát kell beálllítanunk:
		System.setProperty("hadoop.home.dir", "C:\\BigData\\hadoop-3.3.6");
		// Az alábbi sor akkor fontos, ha a Windowsos felhasználónevünkben van szóköz, egyébként elhagyható
		System.setProperty("HADOOP_USER_NAME", "hduser");
		
		Configuration conf = new Configuration();
		
		// Az alábbi két sor szintén a temporális fájlok helyét határozza meg
		// Fõleg az egyetemen lévõ gépek esetében kell ezeket megadni, egyébként nem fontos
		// Importálunk kell hozzá egy csomagot: org.apache.hadoop.mapreduce.server.jobtracker.JTConfig
		conf.set(JTConfig.JT_STAGING_AREA_ROOT, "C:\\BigData\\tmp\\staging");
		conf.set(JTConfig.JT_SYSTEM_DIR, "C:\\BigData\\tmp\\system");
		
		Job job = Job.getInstance(conf, "JobName");
		job.setJarByClass(elte.WordCountDriver.class);
		
		// Itt állítjuk be, hogy a Map/Reduce programunk melyik Mapper, Combiner és Reducer osztályt használja
		job.setMapperClass(elte.WordCountMapper.class);
		// Combinerre nem mindig van szükség. A Combiner lokális összegzést végez.
		// Ebben a feladatban ha jól írjuk meg a reducert (így tettünk) akkor használható combinerként is
		job.setCombinerClass(elte.WordCountReducer.class);
		job.setReducerClass(elte.WordCountReducer.class);

		// Be kell állítanunk a kimeneti kulcs és érték típusát
		// Nálunk ez most Text és IntWritable (ne felejtsük el importálni ha még nem tettük meg, lásd 6.-ik sor)
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);

		// A könyvtár neve, ahol a bemeneti fájlok találhatóak
		// Ez a projekt gyökrékönyvtárában található "input" könyvtárra mutat
		FileInputFormat.setInputPaths(job, new Path("input"));
		
		// Kimeneti könyvtár neve
		Path outPath = new Path("wordcount_out");
		// Itt állítjuk be, hogy a fent meghatározott útvonal legyen a kimeneti könyvtárunk
		FileOutputFormat.setOutputPath(job, outPath);

		// Az alábbi 5 sor leellenõrzi, hogy létezik-e már a kimeneti könyvtár
		// Ha igen, akkor elõbb letörli, hogy aztán létre tudja hozni
		// A FileSystem használatához importálnunk kell a org.apache.hadoop.fs.FileSystem csomagot (lásd 4.-ik sor)
		FileSystem fs;
		fs = FileSystem.get(conf);
		if(fs.exists(outPath)) {
			fs.delete(outPath, true);
		}

		if (!job.waitForCompletion(true))
			return;
	}

}
