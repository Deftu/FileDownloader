package xyz.deftu.fd;

import java.io.File;
import java.util.function.Consumer;

class FileDownloaderFactoryImpl implements FileDownloaderFactory {
    private boolean caches = Constants.caches;
    private Consumer<Long> transferCallback = Constants.transferCallback;
    private String userAgent;
    private int timeout = Constants.timeout;

    public FileDownloader create(File tempDir, File existingFile) {
        return FileDownloader.create(tempDir, existingFile)
                .withCaches(caches)
                .withTransferCallback(transferCallback)
                .withUserAgent(userAgent)
                .withTimeout(timeout);
    }

    public FileDownloader create(File tempDir) {
        return FileDownloader.create(tempDir)
                .withCaches(caches)
                .withUserAgent(userAgent)
                .withTimeout(timeout);
    }

    public FileDownloaderFactory withCaches(boolean caches) {
        this.caches = caches;
        return this;
    }

    public FileDownloaderFactory withTransferCallback(Consumer<Long> transferCallback) {
        this.transferCallback = transferCallback;
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
}