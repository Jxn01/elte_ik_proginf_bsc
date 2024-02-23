package nio;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class Feladat4 {

	private static final char THE_CHAR = '\u5927';
	
	public static void main(String[] args) throws Exception {
		FileChannel fc = FileChannel.open(Path.of("megfejtendo_uzenet.txt"), StandardOpenOption.READ);
		ByteBuffer bb = ByteBuffer.allocate((int)fc.size());
		int r = fc.read(bb);
		bb.flip();
		for(var e : Charset.availableCharsets().entrySet()) {
			System.out.println("checking: "+e.getKey());
			CharBuffer decoded = e.getValue().decode(bb);
			for(int i=0; i<decoded.length(); ++i) {
				if(decoded.get(i) == THE_CHAR) {
					System.out.println("found");
					System.out.println(decoded);
					System.exit(0);
				}
			}
			bb.flip();
		}
	}
}
