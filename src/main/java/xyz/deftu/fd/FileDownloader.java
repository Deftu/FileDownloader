package xyz.deftu.fd;

import java.io.File;

public interface FileDownloader {
    /* Internals. */
    void initialize(File tempDir, File existingFile);

    /* Usage. */
    void download(String url);
    void validate();
    void complete(File newFile);

    /* Additional properties. */
    FileDownloader withCaches(boolean caches);
    FileDownloader withUserAgent(String userAgent);
    FileDownloader withTimeout(int timeout);
    FileDownloader withBufferSize(int bufferSize);

    /* Management. */
    FileDownloadState getState();

    static FileDownloader create(File tempDir, File existingFile) {
        FileDownloader instance = new FileDownloaderImpl();
        instance.initialize(tempDir, existingFile);
        return instance;
    }

    static FileDownloader create(File tempDir) {
        return create(tempDir, null);
    }
}