package elte;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/*
 * Megjegyzések:
 * 
 * A feladat megoldásához nincs szükség Reducerre, azért azt létre sem hoztam
 * Ahhoz, hogy ne legyen reducer-ünk ezt be kell állítani (lásd a main függvényben)
 */

public class GrepDriver {

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
		Job job = Job.getInstance(conf, "JobName");
		job.setJarByClass(elte.GrepDriver.class);
		job.setMapperClass(elte.GrepMapper.class);

		// Kikommentezzük a Reducer beállítását
		//job.setReducerClass(Reducer.class);
		
		// Megadjuk, hogy hány reducert szeretnénk: nullát
		job.setNumReduceTasks(0);

		// Be kell állítanunk a kimeneti kulcs és érték típusát
		// Nálunk ez most Text és IntWritable (ne felejtsük el importálni ha még nem tettük meg, lásd 6.-ik sor)
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);

		// A könyvtár neve, ahol a bemeneti fájlok találhatóak
		// Ez a projekt gyökrékönyvtárában található "input" könyvtárra mutat
		FileInputFormat.setInputPaths(job, new Path("input"));
		
		// Kimeneti könyvtár neve
		Path outPath = new Path("grep_out");
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
