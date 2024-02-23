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
 * Megjegyz�sek:
 * 
 * A programot Hadoop alkalmaz�sk�nt kell futtatni (Run on Hadoop)
 * Ha r�k�rdez, hogy melyik f�jlt futtassa, akkor a drivert kell megadni (WordCountDriver)
 * A log4j.properties f�jlt a projekt "src" k�nyvt�r�ba m�soljuk be. Csak ezut�n fogunk l�tni �rtelmes log �zeneteket a konzolban.
 * Ha futtat�s ut�n nem l�ttjuk a kimeneti f�jlt �rdemes friss�teni a Project Explolerben a projektet (jobb gomb a projekt nev�re, �s Refresh)
 * Ha a kimeneti mapp�ban l�ttjuk a _SUCCESS nev� f�jlt siker�lt a futtat�s.
 * A part-r-00000 f�jl tartalmazza a Reducer oszt�lyunk kimenet�t.
 */

public class WordCountDriver {

	public static void main(String[] args) throws Exception {
		
		// Az al�bb�i 3 sor megadja hov� ker�ljenek a tempor�lis f�jlok, hol van a hadoop, �s be�ll�t egy haddop felhaszn�l�nevet
		// Az egyetemi g�peken fontos haszn�lni, saj�t g�pen ezek n�lk�l is fut a k�d
		// Ez tetsz�leges �tvonalra be�ll�that�:
		System.setProperty("hadoop.tmp.dir", "C:\\BigData\\tmp\\hadoop");
		// Itt a hadoop k�nyt�r�t kell be�lll�tanunk:
		System.setProperty("hadoop.home.dir", "C:\\BigData\\hadoop-3.3.6");
		// Az al�bbi sor akkor fontos, ha a Windowsos felhaszn�l�nev�nkben van sz�k�z, egy�bk�nt elhagyhat�
		System.setProperty("HADOOP_USER_NAME", "hduser");
		
		Configuration conf = new Configuration();
		
		// Az al�bbi k�t sor szint�n a tempor�lis f�jlok hely�t hat�rozza meg
		// F�leg az egyetemen l�v� g�pek eset�ben kell ezeket megadni, egy�bk�nt nem fontos
		// Import�lunk kell hozz� egy csomagot: org.apache.hadoop.mapreduce.server.jobtracker.JTConfig
		conf.set(JTConfig.JT_STAGING_AREA_ROOT, "C:\\BigData\\tmp\\staging");
		conf.set(JTConfig.JT_SYSTEM_DIR, "C:\\BigData\\tmp\\system");
		
		Job job = Job.getInstance(conf, "JobName");
		job.setJarByClass(elte.WordCountDriver.class);
		
		// Itt �ll�tjuk be, hogy a Map/Reduce programunk melyik Mapper, Combiner �s Reducer oszt�lyt haszn�lja
		job.setMapperClass(elte.WordCountMapper.class);
		// Combinerre nem mindig van sz�ks�g. A Combiner lok�lis �sszegz�st v�gez.
		// Ebben a feladatban ha j�l �rjuk meg a reducert (�gy tett�nk) akkor haszn�lhat� combinerk�nt is
		job.setCombinerClass(elte.WordCountReducer.class);
		job.setReducerClass(elte.WordCountReducer.class);

		// Be kell �ll�tanunk a kimeneti kulcs �s �rt�k t�pus�t
		// N�lunk ez most Text �s IntWritable (ne felejts�k el import�lni ha m�g nem tett�k meg, l�sd 6.-ik sor)
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);

		// A k�nyvt�r neve, ahol a bemeneti f�jlok tal�lhat�ak
		// Ez a projekt gy�kr�k�nyvt�r�ban tal�lhat� "input" k�nyvt�rra mutat
		FileInputFormat.setInputPaths(job, new Path("input"));
		
		// Kimeneti k�nyvt�r neve
		Path outPath = new Path("wordcount_out");
		// Itt �ll�tjuk be, hogy a fent meghat�rozott �tvonal legyen a kimeneti k�nyvt�runk
		FileOutputFormat.setOutputPath(job, outPath);

		// Az al�bbi 5 sor leellen�rzi, hogy l�tezik-e m�r a kimeneti k�nyvt�r
		// Ha igen, akkor el�bb let�rli, hogy azt�n l�tre tudja hozni
		// A FileSystem haszn�lat�hoz import�lnunk kell a org.apache.hadoop.fs.FileSystem csomagot (l�sd 4.-ik sor)
		FileSystem fs;
		fs = FileSystem.get(conf);
		if(fs.exists(outPath)) {
			fs.delete(outPath, true);
		}

		if (!job.waitForCompletion(true))
			return;
	}

}
