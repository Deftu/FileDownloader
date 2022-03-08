package xyz.deftu.fd;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.nio.file.Files;
import java.security.MessageDigest;

class HashingHelper {
    static String fetchChecksum(File file) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(Files.readAllBytes(file.toPath()));
            return DatatypeConverter.printHexBinary(digest.digest()).toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}