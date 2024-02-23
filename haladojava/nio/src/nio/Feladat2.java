package nio;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.zip.ZipInputStream;

public class Feladat2 {
	public static void main(String[] args) {
		Path cz = Path.of("copy.zip");
		try(FileChannel fc = FileChannel.open(cz, StandardOpenOption.READ)) {
			ByteBuffer compressionBuffer = ByteBuffer.allocate(2);
			fc.position(8);
			fc.read(compressionBuffer);
			compressionBuffer.flip();
			compressionBuffer.order(ByteOrder.LITTLE_ENDIAN);
			short cMethod = compressionBuffer.getShort(0);
			System.out.println("Compression: "+cMethod);
			fc.position(8+2+16);
			compressionBuffer.rewind();
			fc.read(compressionBuffer);
			System.out.println("file name length: "+compressionBuffer.getShort(0));
			ByteBuffer fileNameBuffer = ByteBuffer.allocate(compressionBuffer.getShort(0));
			fc.position(8+2+16+2+2);
			fc.read(fileNameBuffer);
			fileNameBuffer.flip();
			fileNameBuffer.order(ByteOrder.LITTLE_ENDIAN);
			String fName = new String(fileNameBuffer.array());
			System.out.println("File name: "+fName);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
