package xyz.deftu.fd;

import java.io.File;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

class FileHelper {
    private static Random random;
    static File createTemporaryFile(File directory) {
        File file = new File(directory, "temp-" + generateRandomInteger());
        while (file.exists()) file = createTemporaryFile(directory);
        return file;
    }

    static int generateRandomInteger() {
        try {
            return Math.abs(ThreadLocalRandom.current().nextInt(4096));
        } catch (Exception e) {
            if (random == null) random = new Random();
            return Math.abs(random.nextInt(4096));
        }
    }
}