package xyz.deftu.fd;

import java.io.File;

public interface FileDownloaderFactory {
    /* Creation. */
    FileDownloader create(File tempDir, File existingFile);
    FileDownloader create(File tempDir);

    /* Properties. */
    FileDownloaderFactory withCaches(boolean caches);
    FileDownloaderFactory withUserAgent(String userAgent);
    FileDownloaderFactory withTimeout(int timeout);

    static FileDownloaderFactory create() {
        return new FileDownloaderFactoryImpl();
    }
}