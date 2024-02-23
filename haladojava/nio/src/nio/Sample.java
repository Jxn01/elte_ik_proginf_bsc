package nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

public class Sample {

	public static void main(String[] args) throws IOException {
		File f = new File("test.txt");
		File copy = new File("copy.txt");
		System.out.println(f.getAbsolutePath());
		//oldIO(f, copy);
		newIO(f, copy);
	}
	
	private static void newIO(File f, File copy) throws IOException, FileNotFoundException {
		Path from = f.toPath();
		Path to = copy.toPath();
		Path createdByStaticMethod = Path.of("copy.txt");
		System.out.println("created: "+createdByStaticMethod.toAbsolutePath());
		FileChannel fromChannel = FileChannel.open(from, StandardOpenOption.READ);
		FileChannel toChannel = FileChannel.open(to, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
		ByteBuffer buffer = ByteBuffer.allocate(10);
		try {
			long readedBytes = 0;
			int lastRead = Integer.MIN_VALUE;
			while (readedBytes < fromChannel.size()) {
				lastRead = fromChannel.read(buffer);
				if(lastRead != -1) {
					buffer.flip();
					readedBytes += lastRead;
					toChannel.write(buffer);
				}
				buffer.rewind();
			}
			
		} finally {
			fromChannel.close();
			toChannel.close();
		}
	}

	private static void oldIO(File f, File copy) throws IOException, FileNotFoundException {
		byte[] buffer = new byte[10];
		try (InputStream is = new FileInputStream(f); OutputStream os = new FileOutputStream(copy)) {
			int readed = Integer.MIN_VALUE;
			while ((readed = is.read(buffer)) != -1) {
				System.out.println("readed: " + readed);
				System.out.println(Arrays.toString(buffer));
				if (readed != -1) {
					os.write(buffer, 0, readed);
				}
				os.flush();
			}
		}
	}
	
}
