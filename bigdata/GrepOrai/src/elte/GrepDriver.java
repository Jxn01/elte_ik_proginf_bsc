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
 * Megjegyz�sek:
 * 
 * A feladat megold�s�hoz nincs sz�ks�g Reducerre, az�rt azt l�tre sem hoztam
 * Ahhoz, hogy ne legyen reducer-�nk ezt be kell �ll�tani (l�sd a main f�ggv�nyben)
 */

public class GrepDriver {

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
		Job job = Job.getInstance(conf, "JobName");
		job.setJarByClass(elte.GrepDriver.class);
		job.setMapperClass(elte.GrepMapper.class);

		// Kikommentezz�k a Reducer be�ll�t�s�t
		//job.setReducerClass(Reducer.class);
		
		// Megadjuk, hogy h�ny reducert szeretn�nk: null�t
		job.setNumReduceTasks(0);

		// Be kell �ll�tanunk a kimeneti kulcs �s �rt�k t�pus�t
		// N�lunk ez most Text �s IntWritable (ne felejts�k el import�lni ha m�g nem tett�k meg, l�sd 6.-ik sor)
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);

		// A k�nyvt�r neve, ahol a bemeneti f�jlok tal�lhat�ak
		// Ez a projekt gy�kr�k�nyvt�r�ban tal�lhat� "input" k�nyvt�rra mutat
		FileInputFormat.setInputPaths(job, new Path("input"));
		
		// Kimeneti k�nyvt�r neve
		Path outPath = new Path("grep_out");
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
