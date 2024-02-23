package nio;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Feladat3 {

	private static final Map<Path, List<ByteBuffer>> HISTORY = new HashMap<>();
	private static final Path DIR = Path.of("test");

	public static void main(String[] args) throws Exception {
		new Thread(Feladat3::userInteractions).start();
		WatchService service = FileSystems.getDefault().newWatchService();
		WatchKey registerKey = DIR.register(service, StandardWatchEventKinds.ENTRY_CREATE,
				StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE);
		while (true) {
			WatchKey eventKey = service.take();
			try {
				for (WatchEvent<?> we : eventKey.pollEvents()) {
					if (we.kind() == StandardWatchEventKinds.OVERFLOW) {
						System.err.println("Hiba");
						return;
					}
					Path file = (Path) we.context();
					if (we.kind() == StandardWatchEventKinds.ENTRY_DELETE) {
						System.out.println("file: " + file + " was deleted");
					} else {
						if (we.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
							HISTORY.put(file, new ArrayList<>());
						}
						Path filePath = DIR.resolve(file);
						try (FileChannel fc = FileChannel.open(filePath, StandardOpenOption.READ)) {
							ByteBuffer bb = ByteBuffer.allocate((int) fc.size());
							fc.read(bb);
							bb.flip();
							HISTORY.get(file).add(bb);
						}
						System.out.println("history is updated with: " + file);
					}
				}
			} finally {
				eventKey.reset();
			}
		}
	}

	private static void userInteractions() {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String line = null;
			while ((line = br.readLine()) != null) {
				System.out.println("read: ''" + line + "''");
				if (line.equals("list")) {
					HISTORY.entrySet().forEach(e -> {
						System.out.println(e.getKey() + "\t" + e.getValue().size());
					});
				} else {
					String[] parts = line.split(" ");
					Path path = Path.of(parts[0]);
					int version = Integer.parseInt(parts[1]);
					Path filePath = DIR.resolve(path);
					try (FileChannel fc = FileChannel.open(filePath, StandardOpenOption.WRITE,
							StandardOpenOption.CREATE)) {
						fc.write(HISTORY.get(path).get(version));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalStateException(e);
		}
	}
}
