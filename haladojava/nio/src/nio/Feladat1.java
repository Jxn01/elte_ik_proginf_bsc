package nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class Feladat1 {
	public static void main(String[] args) throws IOException {
		if(args.length!=3) {
			System.err.println("I need 3 arguments");
			System.exit(1);
		}
		Path file = Path.of(args[0]);
		if( ! file.toFile().exists() ) {
			System.err.println("Not exists: "+file);
			System.exit(2);
		}
		long position = Long.parseLong(args [1]);
		byte theByte = Byte.parseByte(args[2]);
		try(FileChannel fc = FileChannel.open(file, StandardOpenOption.READ)) {
			fc.position(position);
			ByteBuffer buffer = ByteBuffer.allocate(1);
			fc.read(buffer);
			buffer.flip();
			if(buffer.get(0) == theByte) {
				System.out.println("They are the same");
			} else {
				System.err.println("Expected: "+theByte+", got: "+buffer.get(0));
			}
		}
	}
}
