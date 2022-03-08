package xyz.deftu.fd;

import java.io.File;

class FileDownloaderFactoryImpl implements FileDownloaderFactory {
    private boolean caches = Constants.caches;
    private String userAgent;
    private int timeout = Constants.timeout;
    private int bufferSize = Constants.bufferSize;

    public FileDownloader create(File tempDir, File existingFile) {
        return FileDownloader.create(tempDir, existingFile)
                .withCaches(caches)
                .withUserAgent(userAgent)
                .withTimeout(timeout)
                .withBufferSize(bufferSize);
    }

    public FileDownloader create(File tempDir) {
        return FileDownloader.create(tempDir)
                .withCaches(caches)
                .withUserAgent(userAgent)
                .withTimeout(timeout)
                .withBufferSize(bufferSize);
    }

    public FileDownloaderFactory withCaches(boolean caches) {
        this.caches = caches;
        return this;
    }

    public FileDownloaderFactory withUserAgent(String userAgent) {
        this.userAgent = userAgent;
        return this;
    }

    public FileDownloaderFactory withTimeout(int timeout) {
        this.timeout = timeout;
        return this;
    }

    public FileDownloaderFactory withBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
        return this;
    }
}