package org.example;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.*;

public class Main {
    public static void main2(String[] args) throws IOException, InterruptedException {
        WatchService watchService = FileSystems.getDefault().newWatchService();
        Path dirToWatch = Paths.get(args[0]);
        dirToWatch.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);
        dirToWatch.register(watchService, StandardWatchEventKinds.ENTRY_DELETE);
        dirToWatch.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);
        while(true){
            WatchKey key = watchService.take();
            for (WatchEvent<?> event : key.pollEvents()) {
                System.out.println(event.kind() + " " + event.context());
            }
            key.reset();
        }
    }

    public static void main(String[] args){
        try(FileOutputStream fos = new FileOutputStream(args[0]); FileInputStream fis = new FileInputStream(args[1])){
            byte[] bytes = new byte[1024];
            while(fis.read(bytes) != -1){
                fos.write(bytes);
            }
            //fis.transferTo(fos); // Java 9
            //fos.write(fis.readAllBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}